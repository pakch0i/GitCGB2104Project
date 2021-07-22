package com.cy.jt.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @RequestMapping("/retrieve")
    public String doRetrieve(){
        //执行业务查询操作,但是执行此操作之前要检查用户有没有登录
        return "do retrieve resource success";
    }

    @RequestMapping("/update")
    public String doUpdate(){
        //执行业务查询操作,但是执行此操作之前要检查用户有没有登录
        return "do update resource success";
    }
}
