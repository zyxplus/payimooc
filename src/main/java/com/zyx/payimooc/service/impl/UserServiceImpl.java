package com.zyx.payimooc.service.impl;

import com.zyx.payimooc.dao.UserMapper;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.enums.RoleEnum;
import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.service.IUserService;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {

        //username, email 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }

        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        user.setRole(RoleEnum.CUSTOMER.getCode());

        //MD5
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));

        //写入数据库
        int res = userMapper.insertSelective(user);
        if (res == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();


    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        //用户名合法性
        if (user == null) {
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        //密码合法性
        String currentPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        if (!user.getPassword().equalsIgnoreCase(currentPassword)){
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        //避免密码泄漏
        user.setPassword("");
        return ResponseVo.success(user);
    }
}
