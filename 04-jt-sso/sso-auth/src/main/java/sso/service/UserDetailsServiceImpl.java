package sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sso.dao.UserMapper;

import java.util.List;
import java.util.Map;

/**
 * 定义UserDetailsService接口的具体实现,在这个实现定义用户登录逻辑
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * @Autowrie注解描述属性时,注入规则是怎样的?
     * spring框架会依据@Autowrie注解描述的属性类型,从spring容器查找对应的
     * Bean,假如只找到一个则直接注入,假如找到多个还会比对属性名是否与容器中的
     * Bean的名字有相同的,有则直接注入,没有则抛出异常.
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;
    /**
     * 当我们执行登录操作时,底层会通过过滤器等对象,调用这个方法
     * @param username 这个参数为页面输入的用户名
     * @return  一般是数据库基于用户名查询到的用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.基于用户名从数据库中查询用户信息
        Map<String, Object> userMap = userMapper.selectUserByUsername(username);
        if(userMap==null){//假设这是从数据库中查询的数据
            throw new UsernameNotFoundException("user not exists");
        }
        //假如分配权限的方式是角色,编写字符串时是"ROLE_"做前缀
        List<String> userPermission = userMapper.selectUserPermissions((Long) userMap.get("id"));

        UserDetails user = new User(username,(String) userMap.get("password"),
                            AuthorityUtils.createAuthorityList(userPermission.toArray(new String[]{})));
        return user;
    }
}
