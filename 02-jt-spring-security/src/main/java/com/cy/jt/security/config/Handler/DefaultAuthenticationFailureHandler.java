package com.cy.jt.security.config.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {

        //1.设置响应数据的编码
        httpServletResponse.setCharacterEncoding("utf-8");
        //2.告诉客户端响应数据的类型,以及客户端以怎样的编码进行显示
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //3.获取一个输出流对象
        PrintWriter out=httpServletResponse.getWriter();
        //4.向客户端输出一个json格式字符串
        //4.1构建一个map对象
        Map<String,Object> map=new HashMap<>();
        map.put("state","200");
        map.put("msg","username or password error");
        //4.2基于jackson中的ObjectMapper对象将一个对象转换为json格式字符串
        String jsonStr= new ObjectMapper().writeValueAsString(map);
        //out.println("{\"state\":200,\"msg\":\"ok\"}");
        out.println(jsonStr);
        out.flush();
    }
}
