package com.zyx.payimooc.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    ERROR(-1, "服务端异常"),
    SUCCESS(0, "成功"),
    PASSWORD_ERROR(1, "密码错误"),
    USERNAME_EXIST(2, "用户名已注册"),
    PARAM_ERROR(3, "参数错误"),
    EMAIL_EXIST(4, "邮箱已注册"),
    NEED_LOGIN(10, "用户未登录，请先登录");

    Integer code;
    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }



}
