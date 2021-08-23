package cn.baizhi.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;

//@Aspect//用在类上代表切面
//@Component
public class CacheAspect {
    //操作对象
    @Autowired
    private RedisTemplate redisTemplate;
    /*
    下面这个是切面累的环绕通知(额外功能)
    配置切面
    切入表达式
    execution()  方法级别
    within()   类级别
    @annotation()注解方式

    ("execution(* cn.baizhi.service.*Impl.query*(..))
    返回值类型：         任意 包名cn.baizhi.service 类：Impl结尾的类  方法：query开头的方法  参数任意
     */
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
            System.out.println(args);
            sb.append(arg);
        }

//        System.out.println(obj);
        //只要执行service中的查询  一定会进行这个环绕通知
        //obj  就是当前目标方法执行后的数结果
        /*
        redis：key   value
        key:  类名全路径+方法名+实参
        value:obj
         */

        /*
        判断如果redis中已经有这个缓存了   就从redis拿 就别放行了
        sb.tostring():当前的key
         */

        ValueOperations valueOperations = redisTemplate.opsForValue();

        Object obj=null;
        //如果redis有数据  和数据库查出来的  是不是一样？  没有增删改查之前一定是一样的
        if (redisTemplate.hasKey(sb.toString())){
            //如果有这个key
            obj = valueOperations.get(sb.toString());
        }else{
            //没有这个key
            try {
                obj = joinPoint.proceed();//放行请求
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            valueOperations.set(sb.toString(), obj);
        }
        System.out.println(sb);
//        cn.baizhi.service.UserServiceImpl
//                queryByPage
//                [Ljava.lang.Object;@2eac6c86
//                [Ljava.lang.Object;@2eac6c86
//        cn.baizhi.service.UserServiceImplqueryByPage13

        return obj;//这个数据会到达controller
    }
    /*
    只要执行增删改查 就应该清除缓存，  应该清除那些缓存，
    比如我们在用户模块：添加了一个用户，以为着我们用户的信息已经变化，哪用户模块的所有查询是否都会收到影响
    所以用户模块数据发生变化，name关于用户模块所有的查询缓存都应该删除

    开发一个额外功能：删除redis缓存
    切点：     增删改查方法
    通知： 前置 后置  异常 环绕
     */
    /*清除缓存*/
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
        System.out.println("后置通知");
        //累的全限命名
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println(name);
        Set keys = redisTemplate.keys("*");//拿到所有的键
        for (Object key : keys) {
            String newKey = (String)key;//字符串
            if (newKey.startsWith(name)){
                redisTemplate.delete(key);
            }
        }
        /*
            redis中 hash类型
            key  value
            大键
                    key value
                    key value
             一个模块的房一个大键
             包名+类名
                    cn.baizhi.service.UserServiceImplqueryByPage13  object
                    cn.baizhi.service.UserServiceImplqueryByPage23  object
         */

    }

}
