package com.lambert.rpc.core.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册器
 */
public class LocalRegistry {

    /**
     * 线程安全注册中心
     */
    private static final Map<String, Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 服务注册
     */
    public static void register(String serviceName, Class<?> clazz) {
        map.put(serviceName, clazz);
    }

    /**
     * 服务获取
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName) {
        return map.get(serviceName);
    }

    public static void remove(String serviceName) {
        map.remove(serviceName);
    }
}
