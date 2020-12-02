package com.zyx.payimooc.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegisterForm {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    private String email;

}
