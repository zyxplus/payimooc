package com.zyx.payimooc.service;

import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.vo.ResponseVo;

public interface IUserService {

    //注册
    ResponseVo register(User user);

}
