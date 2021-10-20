package cn.baizhi.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("放行了");
        String token = request.getParameter("token");


        System.out.println(token);
//        if (token==null){
//            throw new RuntimeException();
//        }
        System.out.println(redisTemplate);
        System.out.println("----------");
        Boolean aBoolean = redisTemplate.hasKey(token);
        if (aBoolean){
            redisTemplate.expire(token, 30, TimeUnit.MINUTES);
            //重置    用户 在redis中key的存活时间
            return true;
        }else{
            throw new RuntimeException();
        }
    }
}
