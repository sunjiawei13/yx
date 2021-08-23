package cn.baizhi.test;


import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class GoEasyTest {

    //向goeasy频道中发消息
    @Test
    public void test1(){//                                                              BC-48c84eb5810d4f20b0ab85b8b9a10df0
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-48c84eb5810d4f20b0ab85b8b9a10df0");
        goEasy.publish("my_channel", "Hello,GoEasy你好");

    }
    @Test
    public void test2(){//

        List<String> data = Arrays.asList("1月", "2月", "3月","4月", "5月", "6月","7月", "8月", "9月","10月", "11月", "12月");
        //                              1 : 5  2: 0   3:20
        List<Integer> manCount = Arrays.asList(5, 0, 20, 36, 10, 10, 30,22,33,13,33,21);
        List<Integer> womanCount = Arrays.asList(5, 20, 36, 10, 10, 30,33,11,12,22,33,11);

        //查询男生每月注册人数
        //查询女生每月注册人数

        //  存储了   月份    男生人数  女生人数
        Map<String,Object> map = new HashMap<>();
        map.put("data", data);
        map.put("manCount", manCount);
        map.put("womanCount", womanCount);

        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-48c84eb5810d4f20b0ab85b8b9a10df0");
        goEasy.publish("my_channel", JSONObject.toJSONString(map));

    }
}
