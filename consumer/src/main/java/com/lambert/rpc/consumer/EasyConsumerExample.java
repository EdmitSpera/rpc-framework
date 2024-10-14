package com.lambert.rpc.consumer;

import com.lambert.rpc.common.model.User;
import com.lambert.rpc.consumer.proxy.UserServiceProxy;

public class EasyConsumerExample {
    public static void main(String[] args) {
        UserServiceProxy userService = new UserServiceProxy();
        User user = new User();

        user.setName("Lambert");

        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        }else {
            System.out.println("user is null");
        }
    }
}
