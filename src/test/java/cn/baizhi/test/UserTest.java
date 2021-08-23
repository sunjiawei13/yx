package cn.baizhi.test;


import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserDao ud;

    //1页 012  ，2页345
    @Test
    public void testqueryRange(){
        List<User> list = ud.queryRange(3, 3);
        for (User user : list) {
            System.out.println(user);
        }
    }
}
