package com.lambert.rpc.core.proxy;

import java.lang.reflect.Proxy;

/**
 * 代理工厂
 */
public class ServiceProxyFactory {
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxyHandler());
    }
}
