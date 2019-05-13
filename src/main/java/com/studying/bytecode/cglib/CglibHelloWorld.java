package com.studying.bytecode.cglib;

import com.studying.util.LoggerUtil;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibHelloWorld {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DemoBean.class);
        enhancer.setCallback(new MethodInterceptorImpl());
        DemoBean my = (DemoBean) enhancer.create();
        my.print();
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {

        public Object intercept(Object obj, Method method, Object[] args,
                                MethodProxy proxy) throws Throwable {
            LoggerUtil.logger.info(" intercepted...", method);
            proxy.invokeSuper(obj, args);
            return null;
        }

    }
}  