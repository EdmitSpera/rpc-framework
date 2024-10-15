package com.lambert.rpc.core.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.lambert.rpc.core.model.RpcReqDTO;
import com.lambert.rpc.core.model.RpcRespDTO;
import com.lambert.rpc.core.serializer.JdkSerializer;
import com.lambert.rpc.core.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务代理处理器 (增强业务逻辑)
 */
public class ServiceProxyHandler implements InvocationHandler {
    private static final Serializer serializer = new JdkSerializer();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 通过静态的方式声明"桩接口"相关信息
        RpcReqDTO rpcRequest = RpcReqDTO.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发送请求
            // TODO 地址硬编码 后续开发注册中心和服务发现机制解决
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();

                // 响应类反序列化
                RpcRespDTO rpcResponse = serializer.deserialize(result, RpcRespDTO.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
