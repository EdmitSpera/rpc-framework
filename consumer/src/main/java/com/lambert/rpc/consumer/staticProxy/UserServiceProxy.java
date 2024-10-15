package com.lambert.rpc.consumer.staticProxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.lambert.rpc.common.entity.User;
import com.lambert.rpc.common.service.UserService;
import com.lambert.rpc.core.model.RpcReqDTO;
import com.lambert.rpc.core.model.RpcRespDTO;
import com.lambert.rpc.core.serializer.JdkSerializer;
import com.lambert.rpc.core.serializer.Serializer;

import java.io.IOException;

/**
 * 静态代理
 */
public class UserServiceProxy implements UserService {
    private static final Serializer serializer = new JdkSerializer();

    /**
     * 调用远程方法
     * @param user
     * @return
     */
    @Override
    public User getUser(User user) {
        // 通过静态的方式声明"桩接口"相关信息
        RpcReqDTO rpcRequest = RpcReqDTO.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class<?>[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                            .body(bodyBytes)
                            .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcRespDTO rpcResponse = serializer.deserialize(result, RpcRespDTO.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
