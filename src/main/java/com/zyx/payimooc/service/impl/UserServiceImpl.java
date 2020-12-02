package com.zyx.payimooc.service.impl;

import com.zyx.payimooc.dao.UserMapper;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.enums.RoleEnum;
import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.service.IUserService;
import com.zyx.payimooc.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo register(User user) {

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
        DigestUtils.md5Digest(user.getPassword().getBytes(StandardCharsets.UTF_8));

        //写入数据库
        int res = userMapper.insertSelective(user);
        if (res == 0) {
            throw new RuntimeException("注册失败");
        }
        return ResponseVo.success();


    }
}
