package sso.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    void  testSelectUserByUsername(){
        Map<String,Object> map =
        userMapper.selectUserByUsername("admin");
        System.out.println(map);
    }
    @Test
    void testSelect(){
        List<String> list = userMapper.selectUserPermissions(1L);
        System.out.println(list);
    }
}
