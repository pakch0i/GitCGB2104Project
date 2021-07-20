package com.cy.jt.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 */
@Configuration
public class SecurityConfig {
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
