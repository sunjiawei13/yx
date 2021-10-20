package cn.baizhi.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AdminService {

    //登录
    Map<String,Object> login(String username, String password, HttpServletRequest request);
}
