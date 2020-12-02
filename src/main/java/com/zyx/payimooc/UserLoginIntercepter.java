package com.zyx.payimooc;

import com.zyx.payimooc.consts.MallConst;
import com.zyx.payimooc.exception.UserLoginException;
import com.zyx.payimooc.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginIntercepter implements HandlerInterceptor {

    /**
     * @param request
     * @param response
     * @param handler
     * @return true: 继续流程；false: 中断
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            throw new UserLoginException();
        }
        return true;
    }
}
