package com.lambert.rpc.core.handler;

import com.lambert.rpc.core.model.RpcReqDTO;
import com.lambert.rpc.core.model.RpcRespDTO;
import com.lambert.rpc.core.register.LocalRegistry;
import com.lambert.rpc.core.serializer.JdkSerializer;
import com.lambert.rpc.core.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * HTTP 请求处理
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    /**
     * 构造响应 并序列化
     * @param request
     * @param response
     * @param serializer
     */
    void doResponse(HttpServerRequest request, RpcRespDTO response, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("content-type", "application/json");
        try {
            // 序列化
            byte[] serialized = serializer.serialize(response);
            httpServerResponse.end(Buffer.buffer(serialized));
        } catch (IOException e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }

    @Override
    public void handle(HttpServerRequest request) {
        // 反序列化请求 获取参数信息
        JdkSerializer serializer = new JdkSerializer();
        System.out.println("Received request: " + request);

        // 异步处理请求
        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcReqDTO rpcRequest = null;

            try {
                rpcRequest = serializer.deserialize(bytes, RpcReqDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 从本地注册器获取服务实现类
            RpcRespDTO rpcRespDTO = new RpcRespDTO();
            if (rpcRequest == null) {
                rpcRespDTO.setMessage("rpcRequest is null");
                doResponse(request, rpcRespDTO, serializer);
                return;
            }

            try {
                // 反射机制得到服务结果
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());

                // 运行方法
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());

                // 返回结果序列化后返回响应
                rpcRespDTO.setData(result);
                rpcRespDTO.setDataType(method.getReturnType());
                rpcRespDTO.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcRespDTO.setMessage(e.getMessage());
                rpcRespDTO.setException(e);
            }
            doResponse(request, rpcRespDTO, serializer);
        });
    }
}
