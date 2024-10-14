package com.lambert.rpc.core.server;

import com.lambert.rpc.core.handler.HttpServerHandler;
import io.vertx.core.Vertx;

/**
 * Vertx web服务器
 */
public class VertxHttpServer implements HttpServer{
    /**
     * 启动服务器
     * @param port
     */
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        // 创建服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 请求处理
        server.requestHandler(new HttpServerHandler());

        //调试
//        server.requestHandler(request ->{
//            // 接收http请求
//            System.out.println("Received request: " + request.method());
//
//            // 发送http响应
//            request.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("Hello from Vert.x HTTP server!");
//        });

        // 启动服务器 监听端口
        server.listen(port,result -> {
            if(result.succeeded()){
                System.out.println("Server now is listening on port " + port);
            } else {
                System.out.println("Failed to listen on port " + port);
            }
        });

    }
}
