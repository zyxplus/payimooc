package com.zyx.payimooc.service.impl;

import com.zyx.payimooc.dao.UserMapper;
import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        //username, email 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());

        DigestUtils.md5Digest(user.getPassword().getBytes(StandardCharsets.UTF_8));

        int res = userMapper.insertSelective(user);
        if (res == 0) {
            throw new RuntimeException("注册失败");
        }


    }
}
