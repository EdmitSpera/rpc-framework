package com.lambert.rpc.provider.serviceImpl;

import com.lambert.rpc.common.model.User;
import com.lambert.rpc.common.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
