package com.studying.rpc.swift.mock;

import com.facebook.swift.codec.ThriftCodec;
import com.facebook.swift.codec.ThriftCodecManager;
import com.facebook.swift.codec.internal.ForCompiler;
import com.facebook.swift.codec.internal.ThriftCodecFactory;
import com.facebook.swift.codec.internal.compiler.CompilerThriftCodecFactory;
import com.facebook.swift.codec.internal.compiler.DynamicClassLoader;
import com.facebook.swift.codec.metadata.ThriftStructMetadata;
import com.google.inject.Inject;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by junweizhang on 17/8/14.
 * 自定义Factory.
 */
public class MockCompilerThriftCodecFactory implements ThriftCodecFactory {

    private final boolean debug;
    private final DynamicClassLoader classLoader;

    @Inject
    public MockCompilerThriftCodecFactory(@ForCompiler ClassLoader parent) {
        this(false, parent);
    }

    public MockCompilerThriftCodecFactory(boolean debug) {
        this(debug, getPriviledgedClassLoader(CompilerThriftCodecFactory.class.getClassLoader()));
    }

    public MockCompilerThriftCodecFactory(boolean debug, ClassLoader parent) {
        this.debug = debug;
        this.classLoader = getPriviledgedClassLoader(parent);
    }

    @Override
    public ThriftCodec<?> generateThriftTypeCodec(ThriftCodecManager codecManager, ThriftStructMetadata metadata) {
        MockThriftCodecByteCodeGenerator<?> generator = new MockThriftCodecByteCodeGenerator<>(
                codecManager,
                metadata,
                classLoader,
                debug
        );
        return generator.getThriftCodec();
    }

    private static DynamicClassLoader getPriviledgedClassLoader(final ClassLoader parent) {
        return AccessController.doPrivileged(new PrivilegedAction<DynamicClassLoader>() {
            public DynamicClassLoader run() {
                return new DynamicClassLoader(parent);
            }
        });
    }
}
