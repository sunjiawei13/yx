package cn.baizhi.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;

@SpringBootTest
public class RedisTest {
    //操作对象
    @Autowired
    private RedisTemplate redisTemplate;
    //操作字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){

        Set<String> keys = stringRedisTemplate.keys("*");

        for (String key : keys) {
            System.out.println(key);
        }

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("tom", "123");
        System.out.println(valueOperations.get("tom"));

    }
}
