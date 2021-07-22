package com.cy.jt.security.interceptor;

import com.cy.jt.security.util.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 令牌(ticker-通票)拦截器
 * 其中: HandlerInterceptor 为Spring MVC中的拦截器
 * 可以在Controller方法执行之前之后执行一些动作
 * 1)Handler 处理器(Spring MVC中将@RestController描述的类看成是处理器)
 * 2)Interceptor 拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {
    /**
     * preHandle在目标Ciontroller方法执行之前执行
     * handler 目标Controller对象
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("--preHandle--");
        String token =request.getHeader("Authentication");
//        String token = request.getParameter("Authentication");
        System.out.println(token);
        //判断请求中是否有令牌1111
        if(token==null||"".equals(token))
            throw new RuntimeException("plese login");
        boolean flag = JwtUtils.isTokenExpired(token);
        if(flag)
            throw new RuntimeException("login timeout,plese login");
        return true; //false表示拦截到请求之后,不再继续传递
    }
}
