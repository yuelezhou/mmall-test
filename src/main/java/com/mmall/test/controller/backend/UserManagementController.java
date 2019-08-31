package com.mmall.test.controller.backend;

import com.mmall.test.common.Const;
import com.mmall.test.common.ServerResponse;
import com.mmall.test.pojo.User;
import com.mmall.test.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/user/")
public class UserManagementController {

   @Autowired
    private IUserService iUserService;

    @RequestMapping("login.do")
    @ResponseBody
    public ServerResponse login(String username, String password, HttpSession httpSession){
        ServerResponse serverResponse = iUserService.login(username,password);
        if(serverResponse.isSuccess()){
            User user =(User)serverResponse.getData();
            if(user.getRole() == Const.Role.RoleAdmin.getAdmin()){
                httpSession.setAttribute(Const.CURRENT_USER,user);
                return serverResponse;
            }else {
                return ServerResponse.createByErrorMessage("不是管理员");
            }
        }
        return serverResponse;
    }


}
