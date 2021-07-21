package com.cy.jt.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
}
