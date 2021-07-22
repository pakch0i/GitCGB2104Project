package com.cy.jt.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
/**基于Jwt规范创建token和解析token的工具类*/
public class JwtUtils {
    private static String secret = "AAABBB";
    /**基于负载和算法创建token信息*/
    public static String generatorToken(Map<String,Object> map){
        return Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+30*60*1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }
    /**解析token获取数据*/
    public static Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
    /**判定是否失效*/
    public static boolean isTokenExpired(String token){
        Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
}
