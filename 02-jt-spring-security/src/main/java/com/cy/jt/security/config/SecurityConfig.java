package com.cy.jt.security.config;

import com.cy.jt.security.config.Handler.DefaultAccessDeniedExceptionHandler;
import com.cy.jt.security.config.Handler.DefaultAuthenticationFailureHandler;
import com.cy.jt.security.config.Handler.JsonAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 */
//由SpringSecurity提供,用于描述权限配置类,告诉系统底层在启动时,进行访问权限的初始化配置
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.配置登录url
        http.formLogin()
                .loginPage("/login.html")//登录页面
                .loginProcessingUrl("/login")//与form表单中的action值相同
                //.usernameParameter("username")//与form表单中input元素的name属性相同
                // .passwordParameter("password")
                .defaultSuccessUrl("/index.html");//登录成功后的url地址
                //.failureUrl("/login.html?error");//登录失败(默认)
                //.successForwardUrl("/index.html");
                //.successHandler(new RedirectAuthenticationSuccessHandler ("http://www.tedu.cn"));
                //.successHandler(new JsonAuthenticationSuccessHandler())
                //.failureHandler(new DefaultAuthenticationFailureHandler());

        //配置登出url
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login.html");
        //设置需要认证与拒绝访问的异常处理
        http.exceptionHandling().accessDeniedHandler(new DefaultAccessDeniedExceptionHandler());


        //3.放行登录url(不需要认证就可以访问)
        http.authorizeRequests()
                .antMatchers("/login.html","/index.html")//这里写要放行的资源
                .permitAll()//允许访问
                .anyRequest().authenticated();//除了以上的资源必须认证才可以访问
    }

    /**
     * @Bean 注解通常会在@Configuration注解描述的类中描述方法
     * 用于告诉Spring框架这个方法的返回值会交给spring管理,并给
     * 对象起个默认的名字,这个名字与方法名相同
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
