package com.zyx.payimooc.service.impl;

import com.zyx.payimooc.enums.RoleEnum;
import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.PayimoocApplicationTests;
import com.zyx.payimooc.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImplTest extends PayimoocApplicationTests {

    @Autowired
    private IUserService iUserService;

    @Test
    public void register() {
        User user = new User("jack", "112345",
                "123@mil.com", RoleEnum.CUSTOMER.getCode());
        iUserService.register(user);
    }
}
