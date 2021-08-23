package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service//创建对象
@Transactional//事务
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminDao ad;

    @Transactional(propagation = Propagation.SUPPORTS )
    @Override
    public Map<String, Object> login(String username, String password) {
        //这里存储的是登录的信息
        Map<String,Object> map=new HashMap<>();
        //根据名字查到用户信息
        Admin admin = ad.queryByUsername(username);
        if(admin !=null){
            //有这个用户
            if(admin.getPassword().equals(password)){
                //登录成功
                map.put("flag", true);
                map.put("msg","登录成功");
            }else {
                //密码错误
                map.put("flag",false);
                map.put("msg","密码错误");
            }
        }else {
            //没有这个用户
            map.put("flag",false);
            map.put("msg","没有这个用户");
        }

        return map;
    }
}
