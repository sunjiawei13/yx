package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


//@Controller //创建对象
//@ResponseBody //后端没页面，响应json

@RestController  //合二为一
@CrossOrigin//解决跨域问题
@RequestMapping("/admin")
public class AdminController {
    //日志
    private Logger log=LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService as;

    //登录
    @RequestMapping("/login")           //@requstboby 参数是一个对象
    public Map<String, Object> login(@RequestBody Admin admin,HttpServletRequest request){
        log.debug("执行了");
        log.debug(admin.toString());
        return as.login(admin.getUsername(),admin.getPassword(),request);


    }
}
