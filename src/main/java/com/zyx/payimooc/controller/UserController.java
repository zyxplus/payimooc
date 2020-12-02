package com.zyx.payimooc.controller;

import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.form.UserLoginForm;
import com.zyx.payimooc.form.UserRegisterForm;
import com.zyx.payimooc.pojo.User;
import com.zyx.payimooc.service.IUserService;
import com.zyx.payimooc.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

import static com.zyx.payimooc.consts.MallConst.CURRENT_USER;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 注册
     * @param userRegisterForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                           BindingResult bindingResult) {

        //获取注解上的信息
        if (bindingResult.hasErrors()) {
            log.error("注册提交的参数有误, {} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userRegisterForm, user);
        return userService.register(user);
    }

    /**
     * 登录
     * @param userLoginForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult,
                                  HttpSession session) {
        //获取注解上的信息
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }

        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        session.setAttribute(CURRENT_USER, userResponseVo.getData());

        return userResponseVo;
    }

    /**
     * 获取当前会话用户信息
     * @param session
     * @return
     */
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        if (user == null) {
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        return ResponseVo.success(user);
    }


    @PostMapping("user/logout")
    public ResponseVo<User> logout(HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);

        session.removeAttribute(CURRENT_USER);
        return ResponseVo.success();
    }



}
