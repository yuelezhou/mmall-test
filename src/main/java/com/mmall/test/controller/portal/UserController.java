package com.mmall.test.controller.portal;


import com.mmall.test.common.Const;
import com.mmall.test.common.RespondCode;
import com.mmall.test.common.ServerResponse;
import com.mmall.test.pojo.User;
import com.mmall.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;


    @RequestMapping("login.do")
    public ServerResponse login(String username, String password, HttpSession httpSession){
        if(username == null || password ==null ) {
             return ServerResponse.createByErrorCodeMessage(RespondCode.ILLEGAL_ARGUMENT.getCode(),RespondCode.ILLEGAL_ARGUMENT.getMsg());
        }
        ServerResponse<User> response =  iUserService.login(username,password);
        if(response.isSuccess()){
            httpSession.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }


}
