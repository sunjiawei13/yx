package cn.baizhi.controller;


import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

//@Controller //创建对象
//@ResponseBody //后端没页面，响应json

@RestController  //合二为一
@CrossOrigin//解决跨域问题
@RequestMapping("/user")//路径
public class UserController {
    private Logger log= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService us;
    //分页查业务
    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page){// page=2
        int size =3;
        return us.queryByPage(page,size);//第二页的3条数据
    }
    //修改状态的业务
    @RequestMapping("/updateStatus")
    public void updateStatus(String id,int status){
        log.debug(id+"----"+status);
        us.updateStatus(id, status);
    }
    //添加用户
    @RequestMapping("/add")
    public void add(MultipartFile photo,String username,String phone,String brief) {
        //byte[] bytes = photo.getBytes();
        /**添加用户
         *  有很多字段  当前方法提供了 username，phone brief
         *  status：0    createdate: new Date()  id:uuid
         *  headimg : //头像地址     动态获取路径  ？ http://2101class.soo-cn-beijing.aliyuncs.com/photo.getOriginalFilename()
         *
         * 2.jpg地址
         *
         * 1上传到本地：    将来项目迁移怎么办？
         *  2上传到云服务器：  阿里云oss
         *  点击oss控制台管理，点击头像，创建Accesskey，点击Bucket列表创建Bucket，名字2021，地域北京，标准储存，不开通不开通，版本控制不开通，读写公共，不加密。不开通日志备份
         *
         */

        System.out.println(photo.getOriginalFilename());
        System.out.println(username);
        System.out.println(phone);
        System.out.println(brief);

        User user=new User(null,username , phone, null, brief, null, null, null,null);

        us.save(photo,user);
    }

    //删除状态的业务
    @RequestMapping("/delete")
    public void delete(String id,String headimgurl){
        log.debug(id);
        log.debug(headimgurl);
        us.deleteById(id,headimgurl);

    }

    //导出
    @RequestMapping("/download")
    public void download(){
        System.out.println("11111");
        us.downloadUser();
    }
    //用户分析
    @RequestMapping("/registCount")
    public Map<String, Object> registCount(){
        //System.out.println("执行了");
        return us.queryUserSexCount();

    }



}
