package com.cy.jt.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可以将这里的Controller看成是系统内部的一个资源对象,
 * 我们要求访问此对象中的方法需要进行权限检查
 */
@RestController
public class ResourceController {
    /**添加操作
     * @PreAuthorize 注解有SpringSecurity框架提供,用于描述方法,此注解描述
     * 方法后,再访问方法首先要进行权限检测
     * */
    //@PreAuthorize("hasRole('admin')")//假如登录用户具备admin这个角色可以访问
    @PreAuthorize("hasAuthority('sys:res:create')")
    @RequestMapping("/doCreate")
    public String doCreate(){
        return "create resource(insert data) ok";
    }
    /**查询操作*/
    /**修改操作*/
    /**删除操作*/
    @PreAuthorize("hasAuthority('sys:res:delete')")
    @RequestMapping("/doDelete")
    public String doDelete(){
        return "delete resource(delete data) ok";
    }

    /**
     * 获取用户登录信息
     */
    @GetMapping("/doGetUser")
    public String doGetUsre(){
        //从Session中获取用户认证信息
        //1)Authentication 认证对象(封装了登录用户信息的对象)
        //2)SecurityContextHolder  持有登录状态的信息的对象(底层可通过session获取用户信息)
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        //基于认证对象获取用户身份信息
        User principal =(User) authentication.getPrincipal();
        System.out.println("principal="+principal);
        return principal.getUsername()+":"+principal.getAuthorities();
    }
}
