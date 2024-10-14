package com.lambert.rpc.provider;

import com.lambert.rpc.common.service.UserService;
import com.lambert.rpc.core.register.LocalRegistry;
import com.lambert.rpc.core.server.VertxHttpServer;

public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 调用core 启动并监听端口
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
