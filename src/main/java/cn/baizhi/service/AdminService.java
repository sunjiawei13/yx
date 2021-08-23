package cn.baizhi.service;

import java.util.Map;

public interface AdminService {

    //登录
    Map<String,Object> login(String username,String password);
}
