package com.lambert.rpc.common.service;

import com.lambert.rpc.common.model.User;

/**
 * 桩文件
 */
public interface UserService {

    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);
}
