package com.lambert.rpc.consumer;

import com.lambert.rpc.common.entity.User;
import com.lambert.rpc.common.service.UserService;
import com.lambert.rpc.core.proxy.ServiceProxyFactory;

public class EasyConsumerExample {
    public static void main(String[] args) {
        // 静态代理
//        UserServiceProxy userService = new UserServiceProxy();
//        User user = new User();
//
//        user.setName("Lambert");
//
//        // 调用
//        User newUser = userService.getUser(user);
//        if (newUser != null) {
//            System.out.println(newUser.getName());
//        }else {
//            System.out.println("user is null");
//        }

        // 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Lambert");
        User newUser = userService.getUser(user);

        if (newUser != null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("user is null");
        }

    }
}
