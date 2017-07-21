package com.studying.rpc.swift.mock;

import com.facebook.nifty.core.NiftyRequestContext;
import com.facebook.nifty.core.RequestContext;
import com.facebook.nifty.core.TNiftyTransport;
import com.facebook.nifty.processor.NiftyProcessor;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.service.ContextChain;
import com.facebook.swift.service.ThriftEventHandler;
import com.facebook.swift.service.ThriftMethodProcessor;
import com.facebook.swift.service.metadata.ThriftMethodMetadata;
import com.facebook.swift.service.metadata.ThriftServiceMetadata;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.studying.util.LoggerUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.*;
import org.springframework.util.ReflectionUtils;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static org.apache.thrift.TApplicationException.INVALID_MESSAGE_TYPE;
import static org.apache.thrift.TApplicationException.UNKNOWN_METHOD;

/**
 * Created by junweizhang on 17/7/20.
 * Mock NiftyProcessor that wraps a Thrift service.
 */
@ThreadSafe
public class MockThriftServiceProcessor implements NiftyProcessor {

    private final Map<String, ThriftMethodProcessor> methods;
    private final List<ThriftEventHandler> eventHandlers;

    /**
     * @param eventHandlers event handlers to attach to services
     * @param services      the services to expose; services must be thread safe
     */
    public MockThriftServiceProcessor(ThriftCodecManager codecManager, List<? extends ThriftEventHandler> eventHandlers, Object...
            services) {
        this(codecManager, eventHandlers, ImmutableList.copyOf(services));
    }

    public MockThriftServiceProcessor(ThriftCodecManager codecManager, List<? extends ThriftEventHandler> eventHandlers, List<?> services) {
        Preconditions.checkNotNull(codecManager, "codecManager is null");
        Preconditions.checkNotNull(services, "service is null");
        Preconditions.checkArgument(!services.isEmpty(), "services is empty");

        Map<String, ThriftMethodProcessor> processorMap = newHashMap();
        for (Object service : services) {
            ThriftServiceMetadata serviceMetadata = new ThriftServiceMetadata(service.getClass(), codecManager.getCatalog());
            for (ThriftMethodMetadata methodMetadata : serviceMetadata.getMethods().values()) {
                String methodName = methodMetadata.getName();
                MockThriftMethodProcessor methodProcessor = new MockThriftMethodProcessor(service, serviceMetadata.getName(), methodMetadata,
                        codecManager);
                if (processorMap.containsKey(methodName)) {
                    throw new IllegalArgumentException("Multiple @ThriftMethod-annotated methods named '" + methodName + "' found in the " +
                            "given services");
                }
                processorMap.put(methodName, methodProcessor);
            }
        }
        methods = ImmutableMap.copyOf(processorMap);
        this.eventHandlers = ImmutableList.copyOf(eventHandlers);
    }

    public Map<String, ThriftMethodProcessor> getMethods() {
        return methods;
    }

    @Override
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public ListenableFuture<Boolean> process(final TProtocol in, TProtocol out, RequestContext requestContext)
            throws TException {
        String methodName = null;
        int sequenceId = 0;

        try {
            final SettableFuture<Boolean> resultFuture = SettableFuture.create();
            TMessage message = in.readMessageBegin();
            methodName = message.name;
            sequenceId = message.seqid;

            // lookup method TODO 此处用我们的Mock Method代替,并且将每次调用的参数传递下去
            // ThriftMethodProcessor method = getMethods().get(methodName);
            ThriftMethodProcessor method = getMethods().get("mockService");
            FieldUtils.writeDeclaredField(method, "name", methodName, true);
            FieldUtils.writeDeclaredField(method, "resultStructName", methodName + "_result", true);

            if (method == null) {
                TProtocolUtil.skip(in, TType.STRUCT);
                createAndWriteApplicationException(out, requestContext, methodName, sequenceId, UNKNOWN_METHOD, "Invalid method name: '"
                        + methodName + "'", null);
                return Futures.immediateFuture(true);
            }

            switch (message.type) {
                case TMessageType.CALL:
                case TMessageType.ONEWAY:
                    // Ideally we'd check the message type here to make the presence/absence of
                    // the "oneway" keyword annotating the method matches the message type.
                    // Unfortunately most clients send both one-way and two-way messages as CALL
                    // message type instead of using ONEWAY message type, and servers ignore the
                    // difference.
                    break;

                default:
                    TProtocolUtil.skip(in, TType.STRUCT);
                    createAndWriteApplicationException(out, requestContext, methodName, sequenceId, INVALID_MESSAGE_TYPE, "Received " +
                            "invalid message type " + message.type + " from client", null);
                    return Futures.immediateFuture(true);
            }


            // final ContextChain context = new ContextChain(eventHandlers, method.getQualifiedName(), requestContext);
            // 构造ContextChain实例.
            Constructor constructor = ContextChain.class.getDeclaredConstructor(List.class, String.class, RequestContext.class);
            LoggerUtil.logger.info("constructor : {} isAccessible : {}", constructor, constructor.isAccessible());
            ReflectionUtils.makeAccessible(constructor);
            LoggerUtil.logger.info("constructor : {} isAccessible : {}", constructor, constructor.isAccessible());
            final ContextChain context = (ContextChain) constructor.newInstance(eventHandlers, method.getQualifiedName(), requestContext);

            // invoke method
            ListenableFuture<Boolean> processResult = method.process(in, out, sequenceId, context);

            Futures.addCallback(
                    processResult,
                    new FutureCallback<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            context.done();
                            resultFuture.set(result);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            LoggerUtil.logger.error("Failed to process method [" + method.getName() + "] of service [" + method
                                    .getServiceName() + "]", t);
                            context.done();
                            resultFuture.setException(t);
                        }
                    });

            return resultFuture;
        } catch (TApplicationException e) {
            // If TApplicationException was thrown send it to the client.
            // This could happen if for example, some of event handlers method threw an exception.
            writeApplicationException(out, requestContext, methodName, sequenceId, e);
            return Futures.immediateFuture(true);
        } catch (Exception e) {
            return Futures.immediateFailedFuture(e);
        }
    }

    public static TApplicationException createAndWriteApplicationException(
            TProtocol outputProtocol,
            RequestContext requestContext,
            String methodName,
            int sequenceId,
            int errorCode,
            String errorMessage,
            Throwable cause)
            throws TException {
        // unexpected exception
        TApplicationException applicationException = new TApplicationException(errorCode, errorMessage);
        if (cause != null) {
            applicationException.initCause(cause);
        }

        LoggerUtil.logger.error(errorMessage, applicationException);

        return writeApplicationException(outputProtocol, requestContext, methodName, sequenceId, applicationException);
    }

    public static TApplicationException writeApplicationException(
            TProtocol outputProtocol,
            RequestContext requestContext,
            String methodName,
            int sequenceId,
            TApplicationException applicationException)
            throws TException {
        LoggerUtil.logger.error(applicationException.getMessage(), applicationException);
        TNiftyTransport requestTransport = requestContext instanceof NiftyRequestContext ? ((NiftyRequestContext) requestContext)
                .getNiftyTransport() : null;

        // Application exceptions are sent to client, and the connection can be reused
        outputProtocol.writeMessageBegin(new TMessage(methodName, TMessageType.EXCEPTION, sequenceId));
        applicationException.write(outputProtocol);
        outputProtocol.writeMessageEnd();
        if (requestTransport != null) {
            requestTransport.setTApplicationException(applicationException);
        }
        outputProtocol.getTransport().flush();

        return applicationException;
    }
}
