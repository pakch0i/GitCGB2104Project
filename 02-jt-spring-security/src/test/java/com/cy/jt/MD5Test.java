package com.cy.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
public class MD5Test {
    @Test
    void Md5Encond(){
        //1.设置一个密码
        String password = "12345";
        //加盐操作
        String salt = "www.baiud";
        //对密码加密操作
        String pwd = DigestUtils.md5DigestAsHex((password+salt).getBytes());
        System.out.println(pwd);
        System.out.println(pwd.length());
    }
}
