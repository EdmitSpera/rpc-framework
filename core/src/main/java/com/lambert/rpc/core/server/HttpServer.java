package com.lambert.rpc.core.server;

/**
 * Http服务器接口
 * 方便扩展 实现不同web服务器
 */
public interface HttpServer {
    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}
