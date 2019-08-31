package com.mmall.test.service;

import com.mmall.test.common.ServerResponse;
import com.mmall.test.pojo.User;

import javax.servlet.http.HttpSession;

public interface IUserService {
    ServerResponse login(String username, String password);
    ServerResponse register(User user);
    ServerResponse<String> checkeValid(String str ,String type);
    ServerResponse forgetQuestion(String username);
    ServerResponse questionAndAnswer(String username,String question,String password);
    ServerResponse forgetRestPassword(String username,String password,String token);
    ServerResponse updateInfo(User user);
    ServerResponse getInfo(int userId);
}
