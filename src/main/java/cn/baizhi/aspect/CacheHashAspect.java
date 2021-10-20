package cn.baizhi.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect//用在类上代表切面
@Component
public class CacheHashAspect {
    //操作对象
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){
        System.out.println("进入环绕通知");
        //键拼接
        StringBuilder sb = new StringBuilder();

        //获取类的全路径
        String className = joinPoint.getTarget().getClass().getName();//目标对象
        System.out.println(className);
        //获取方法名
        String methodName = joinPoint.getSignature().getName();//方法名
        System.out.println(methodName);
        sb.append(className).append(methodName);
        //实参值
        Object[] args = joinPoint.getArgs();//方法参数
        for (Object arg : args) {
            System.out.println(arg);
            sb.append(arg);
        }

        redisTemplate.setKeySerializer(new StringRedisSerializer());//key设置不序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();

        Object obj=null;
        //如果redis有数据  和数据库查出来的  是不是一样？  没有增删改查之前一定是一样的
        if (hashOperations.hasKey(className,sb.toString())){

            //如果有这个key
            obj = hashOperations.get(className,sb.toString());
        }else{
            //没有这个key
            try {
                obj = joinPoint.proceed();//放行请求
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hashOperations.put(className,sb.toString(),obj);
        }

        return obj;//这个数据会到达controller
    }

    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint) {
        System.out.println("后置通知");
        //删除大键
        //获取类的全路径
        String name= joinPoint.getTarget().getClass().getName();//目标对象
        System.out.println(name);
        redisTemplate.delete(name);

    }

}
