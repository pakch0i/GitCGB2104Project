package com.cy.jt.srcurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtTests {
    private String secret = "AAABBB";
    /**测试创建和解析token的过程*/
    @Test
    void  testCreateAndParseToken(){
        //1.创建一个token(包含三部分信息: 头信息,负载信息.签名信息)
        //1.1获取登录认证信息(例如:用户名,权限)
        Map<String,Object> map = new HashMap<>();
        map.put("username","lack");
        map.put("permissions","sys:res:create,sys:res:retrieve");
        //1.2基于JWT规范创建token对象
        //java中的日历对象
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 30);
        Date expirationTime=calendar.getTime();

        String token = Jwts.builder()
                .setClaims(map) //负载信息(存储登录用户信息)
                .setExpiration(new Date(System.currentTimeMillis()+30*1000)) //失效时间
                .setIssuedAt(new Date()) //签发时间
                .signWith(SignatureAlgorithm.HS256,secret) //签名加密算法已经密钥盐
                .compact();  //生成token
        System.out.println("token="+token);

        //2.解析token内容
        Claims body =
                Jwts.parser()
                        .setSigningKey(secret)  //设置解析使用前的秘钥
                        .parseClaimsJws(token)  //获取token中的负载
                        .getBody();  //获取具体负载内容
        System.out.println(body);
    }
}
