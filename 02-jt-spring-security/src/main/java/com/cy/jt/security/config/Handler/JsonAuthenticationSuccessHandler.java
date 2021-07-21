package com.cy.jt.security.config.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功后返回给用户一个json数据
 */
public class JsonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {
        //可以在这样的方法中获取登录信息用户(后续可以将这样的数据写到Redis缓存或者直接构建一个token)
        //获取用户身份(principal-身份的意思)
        User principal =(User) authentication.getPrincipal();
        //获取user用户信息
        System.out.println(principal.getUsername()+"/"+principal.getPassword()+"/"+principal.getAuthorities());
        //获取用户凭证(默认为密码)
        Object credentials = authentication.getCredentials();
        System.out.println("principal="+principal);
        System.out.println("credentials="+credentials);

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
        map.put("msg","Login ok");
        //4.2基于jackson中的ObjectMapper对象将一个对象转换为json格式字符串
        String jsonStr= new ObjectMapper().writeValueAsString(map);
        //out.println("{\"state\":200,\"msg\":\"ok\"}");
        out.println(jsonStr);
        out.flush();
    }
}
