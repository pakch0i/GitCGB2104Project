package com.cy.jt.security.controller;

import com.cy.jt.security.util.JwtUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @RequestMapping("/login")
    public Map<String,Object> doLogin(String username,String password){
        Map<String,Object> map = new HashMap<>();

        if ("jack".equals(username)&&"123456".equals(password)){
            map.put("state","200");
            map.put("message","Login in");
            Map<String,Object> claims = new HashMap<>();//负载信息
            claims.put("username",username);
            map.put("Authentication", JwtUtils.generatorToken(claims));
            return map;
        }else {
            map.put("state","500");
            map.put("message","Login failure");
            return map;
        }
    }
}
