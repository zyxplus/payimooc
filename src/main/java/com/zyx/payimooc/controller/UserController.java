package com.zyx.payimooc.controller;

import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.form.UserForm;
import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.service.IUserService;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("register")
    public Object register(@Valid @RequestBody UserForm userForm,
                           BindingResult bindingResult) {

        //获取注解上的信息
        if (bindingResult.hasErrors()) {
            log.error("注册提交的参数有误, {} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        return userService.register(user);

    }


}
