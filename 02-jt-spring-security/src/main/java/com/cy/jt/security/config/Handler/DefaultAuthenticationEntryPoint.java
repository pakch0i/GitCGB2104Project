package com.cy.jt.security.config.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 当系统出现AuthenticationException异常时,会自动调用commence方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param e
     */
    @Override
    public void commence(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        Map<String,Object> map = new HashMap<>();
        map.put("state",HttpServletResponse.SC_UNAUTHORIZED);//SC_UNAUTHORIZED没有被认证的值为401
        map.put("message","请先登录再访问");
        //将数据转换为json格式字符串
        String jsonStr = new ObjectMapper().writeValueAsString(map);
        //输出数据
        out.println(jsonStr);
        out.flush();

    }
}
