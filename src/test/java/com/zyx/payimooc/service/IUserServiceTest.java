package com.zyx.payimooc.service;

import com.zyx.payimooc.PayimoocApplicationTests;
import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.enums.RoleEnum;
import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class IUserServiceTest extends PayimoocApplicationTests {

    public static final String USERNAME = "jack";
    public static final String PASSWORD = "123456";

    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        User user = new User(USERNAME, PASSWORD, "jack@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }

    @Test
    public void login() {
        register();
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}
