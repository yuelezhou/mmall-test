package com.mmall.test.service;

import com.mmall.test.common.ServerResponse;

public interface IUserService {
    ServerResponse login(String username, String password);
}
