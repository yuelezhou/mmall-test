package com.mmall.test.controller.portal;


import com.mmall.test.common.Const;
import com.mmall.test.common.RespondCode;
import com.mmall.test.common.ServerResponse;
import com.mmall.test.pojo.User;
import com.mmall.test.service.IUserService;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
    @Autowired
    private IUserService iUserService;


    @RequestMapping("login.do")
    @ResponseBody
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

    @RequestMapping("register.do")
    @ResponseBody
    public ServerResponse register(User user){
        return iUserService.register(user);
    }

    @RequestMapping("logout.do")
    @ResponseBody
    public ServerResponse logout(HttpSession httpSession){
        httpSession.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("登出成功");
    }

    @RequestMapping("check_valid.do")
    @ResponseBody
    public ServerResponse checkValid(String str ,String type){
        return iUserService.checkeValid(str,type);
    }

    @RequestMapping("get_user_info.do")
    @ResponseBody
    public ServerResponse getUserInfo(HttpSession httpSession){
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return ServerResponse.createBySuccessMessage(user);
    }


    @RequestMapping("forget_get_question.do")
    @ResponseBody
    public ServerResponse forgetGetQuestion(String username){
        return iUserService.forgetQuestion(username);
    }

    @RequestMapping("forget_check_answer.do")
    @ResponseBody
    public ServerResponse forgetCheckAnswer(String username,String question, String password){
        return iUserService.questionAndAnswer(username,question,password);
    }

    @RequestMapping("forget_reset_password.do")
    @ResponseBody
    public ServerResponse forgetResetPassword(String username,String password,String token){
        return iUserService.forgetRestPassword(username,password,token);
    }

    @RequestMapping("update_information.do")
    @ResponseBody
    public ServerResponse updateInformation(User user,HttpSession httpSession){
        User currentUser = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登陆");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> serverResponse = iUserService.updateInfo(user);
        if(serverResponse.isSuccess()){
            serverResponse.getData().setUsername(currentUser.getUsername());
            httpSession.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

    @RequestMapping("get_information,do")
    @ResponseBody
    public ServerResponse getInformation(HttpSession httpSession){
        User user = (User)httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(RespondCode.NEED_LOGIN.getCode(),"用户未登陆");
        }
        return iUserService.getInfo(user.getId());


    }







}
