package com.mmall.test.service.impl;

import com.github.pagehelper.StringUtil;
import com.mmall.test.common.Const;
import com.mmall.test.common.RespondCode;
import com.mmall.test.common.ServerResponse;
import com.mmall.test.common.TokenCache;
import com.mmall.test.dao.UserMapper;
import com.mmall.test.pojo.User;
import com.mmall.test.service.IUserService;
import com.mmall.test.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    public ServerResponse login(String username,String password){
        int resultMap = userMapper.checkUsername(username);
        if(resultMap == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        String MD5Password = MD5Util.MD5EncodingUTF8(password);
        User user = userMapper.getUserByUsernameAndPassword(username,MD5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessMessage(user);
    }

    public ServerResponse register(User user){
        ServerResponse validResponse = this.checkeValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkeValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.RoleCustomer.getAdmin());
        user.setPassword(MD5Util.MD5EncodingUTF8(user.getPassword()));
        int result = userMapper.insert(user);
        if(result > 0){
            return ServerResponse.createBySuccessMessage("注册成功");
        }
        return ServerResponse.createByErrorMessage("注册失败");
    }

    public ServerResponse<String> checkeValid(String str ,String type){
        if(StringUtils.isNotBlank(type)){
            if(type.equals(Const.USERNAME)){
                int result = userMapper.checkUsername(str);
                if(result > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(type.equals(Const.EMAIL)){
                int result = userMapper.checkEmail(str);
                if(result > 0 ){
                    return  ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage(RespondCode.ILLEGAL_ARGUMENT.getMsg());
        }
        return ServerResponse.createByErrorMessage("校验成功");
    }

    public ServerResponse forgetQuestion(String username){
        ServerResponse validResponse = this.checkeValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            String question  = userMapper.selectQuestionByUsername(username);
            return ServerResponse.createBySuccessMessage(question);
        }
        return validResponse;
    }

    public ServerResponse questionAndAnswer(String username,String question,String password){
        ServerResponse validResponse = this.checkeValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            String token = UUID.randomUUID().toString();
            TokenCache.setValue(TokenCache.TOKEN_PREFIX+username,token);
            return ServerResponse.createBySuccessMessage(token);
        }
        return ServerResponse.createByErrorMessage("问题的答案不正确");
    }


    public ServerResponse forgetRestPassword(String username,String password,String token){
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token为空");
        }
        ServerResponse validResponse =  this.checkeValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            String tokenInCache = TokenCache.getValue(TokenCache.TOKEN_PREFIX+username);
            if(token == tokenInCache){
                String newPassword = MD5Util.MD5EncodingUTF8(password);
                int result = userMapper.updatePasswordByUsername(username,newPassword);
                if(result >0){
                    return ServerResponse.createBySuccessMessage("密码更新成功");
                }
                return ServerResponse.createByErrorMessage("更新密码失败");
            }
            return ServerResponse.createByErrorMessage("token错误");
        }
        return ServerResponse.createByErrorMessage("用户名不存在");
    }


    public ServerResponse updateInfo(User user){
        ServerResponse validResponse = this.checkeValid(user.getEmail(),Const.EMAIL);
        if(validResponse.isSuccess()){
            int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
            if(resultCount > 0){
                return ServerResponse.createByErrorMessage("email已存在，请更换email");
            }
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setEmail(user.getEmail());
            updateUser.setPhone(user.getPhone());
            updateUser.setQuestion(user.getQuestion());
            updateUser.setAnswer(user.getAnswer());

            int result = userMapper.updateByPrimaryKeySelective(updateUser);
            if(result > 0 ){
                return ServerResponse.createBySuccess(updateUser,"更新成功");
            }
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createByErrorMessage("email已存在");
    }

    public ServerResponse getInfo(int userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessMessage(user);
    }

}
