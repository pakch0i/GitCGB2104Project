package sso.config;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sso.util.JwtUtils;
import sso.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**构建配置安全对象*/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.配置form认证
        http.formLogin()
                .successHandler(this.authenticationSuccessHandler())//登录成功
                .failureHandler(this.authenticationFailureHandler());//登录失败
        http.exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint());//提示要认证

        //3.所有资源都要认证
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    /**密码加密对象*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**登录成功的认证处理器*/
    private AuthenticationSuccessHandler authenticationSuccessHandler(){
        return ( request, response,  authentication)->{
            Map<String,Object> map = new HashMap<>();
            map.put("state",200);
            map.put("message","login ok");

            Map<String,Object> userInfo = new HashMap<>();
            //获取用户身份,包含了认证和授权信息
            User user = (User) authentication.getPrincipal();
            //获取用户名,并封装到userInfo中
            userInfo.put("username",user.getUsername());
            //获取用户权限封装到userInfo中
            List<String> anthorities = new ArrayList<>();
            user.getAuthorities().forEach((authority)-> {
               anthorities.add(authority.getAuthority());
            });
            userInfo.put("anthorities",anthorities);
            //生成token令牌
            String token = JwtUtils.generatorToken(userInfo);
            map.put("token",token);
            WebUtils.writeJsonToClient(response,map);
        };
    }
    /**登录失败的处理器*/
    private AuthenticationFailureHandler authenticationFailureHandler(){
        return ( httpServletRequest,  httpServletResponse,  e) -> {
            Map<String,Object> map = new HashMap<>();
            map.put("state",500);
            map.put("message","login failure");
            WebUtils.writeJsonToClient(httpServletResponse,map);
        };
    }
    /**假如没有登录访问资源时给出提示*/
    private AuthenticationEntryPoint authenticationEntryPoint(){
        return ( httpServletRequest,  httpServletResponse, e) -> {
            Map<String,Object> map = new HashMap<>();
            map.put("state", HttpServletResponse.SC_UNAUTHORIZED);//401
            map.put("message","please login");
            WebUtils.writeJsonToClient(httpServletResponse,map);
        };
    }
}
