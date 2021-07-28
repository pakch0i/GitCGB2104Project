package sso.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //基于用户名查询用户信息
    @Select("select * from sys_user where username=#{username}")
    Map<String,Object> selectUserByUsername(@Param("username") String username);

    /**
     * 基于用户id查询用户权限信息
     * @param userId
     * @return
     */
    @Select("select m.permission\n" +
            "from sys_user u join  sys_user_role ur\n" +
            "on u.id=ur.user_id join sys_role_menu srm\n" +
            "on ur.role_id = srm.role_id join sys_menu m\n" +
            "on srm.menu_id=m.id\n" +
            "where u.id=#{userId}")
    List<String> selectUserPermissions(@Param("userId") Long userId);
}
