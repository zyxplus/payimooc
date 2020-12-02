package com.zyx.payimooc.exception;

import com.zyx.payimooc.enums.ResponseEnum;
import com.zyx.payimooc.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    //控制返回错误码为403
//    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo handle(RuntimeException e) {
        return ResponseVo.error(ResponseEnum.ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
}
