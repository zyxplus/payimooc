package com.zyx.payimooc.service;

import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.vo.ResponseVo;

public interface IUserService {

    //注册
    ResponseVo<User> register(User user);


    /**
     * @param username
     * @param password
     * @return
     * 登录
     */
    ResponseVo<User> login(String username, String password);




}
