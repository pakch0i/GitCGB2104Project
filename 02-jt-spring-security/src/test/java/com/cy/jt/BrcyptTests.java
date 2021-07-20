package com.cy.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class BrcyptTests {
    @Test
    void testEncode(){
        //1.定义一个密码
        String password = "12346";
        //2.定义加密对象
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //3.对密码进行加密(对密码基于随机盐进行hash不可逆加密)
        String newPwd = encoder.encode(password);
        System.out.println(newPwd);
        System.out.println(newPwd.length());
        //4.对密码进行匹配测试(匹配时底层会对密码再次进行加密)
        boolean flag = encoder.matches(password,newPwd);
        System.out.println(flag);
    }
}
