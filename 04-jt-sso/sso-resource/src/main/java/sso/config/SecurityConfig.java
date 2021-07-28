package sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import sso.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.设置拒绝处理器(不允许访问时.给出什么反馈)
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        //3.资源放行设置(所有资源在本项目中不进行认证)
        http.authorizeRequests().anyRequest().permitAll();
    }

    public AccessDeniedHandler accessDeniedHandler(){
        return ( httpServletRequest, httpServletResponse,  e)-> {
            Map<String,Object> map = new HashMap<>();
            map.put("state",403);
            map.put("message","权限不足");
            //将对象写到客户端
            WebUtils.writeJsonToClient(httpServletResponse,map);
        };
    }
}
