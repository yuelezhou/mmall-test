package com.mmall.test.service.impl;

import com.github.pagehelper.StringUtil;
import com.mmall.test.common.ServerResponse;
import com.mmall.test.dao.UserMapper;
import com.mmall.test.pojo.User;
import com.mmall.test.service.IUserService;
import com.mmall.test.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    public ServerResponse login(String username,String password){


        int resultMap = userMapper.checkUsername(username);
        if(resultMap == 0){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String MD5Password = MD5Util.MD5EncodingUTF8(password);
        User user = userMapper.getUserByUsernameAndPassword(username,MD5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccessMessage(user);
    }
}
