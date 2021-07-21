package com.cy.jt.security.config.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认的用于处理访问被拒绝的异常处理器
 */
public class DefaultAccessDeniedExceptionHandler implements AccessDeniedHandler {
    /**
     * AccessDeniedHandler:统一处理 AccessDeniedException 异常.
     * @param httpServletRequest  请求对象
     * @param httpServletResponse 响应对象
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AccessDeniedException e) throws IOException, ServletException {
        //httpServletResponse.sendRedirect("http://www.tedu.cn");
        //假如访问被拒绝了向客户响应一个Json格式的字符串
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        Map<String,Object> map = new HashMap<>();
        map.put("state",HttpServletResponse.SC_FORBIDDEN);
        map.put("message","没有访问权限,请联系管理员");
        //将数据转换为json格式字符串
        String jsonStr = new ObjectMapper().writeValueAsString(map);
        //输出数据
        out.println(jsonStr);
        out.flush();
    }
}
