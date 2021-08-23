package cn.baizhi.controller;

import cn.baizhi.commont.CommontResult2;
import cn.baizhi.service.VideoService;
import cn.baizhi.util.AliYun;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/yingx/app")
@CrossOrigin
@RestController
public class AppController {

    @Autowired
    private VideoService vs;

    //yingx/app/queryByReleaseTime
    @RequestMapping("/queryByReleaseTime")
    public Map<String, Object> queryByReleaseTime() {
        List<VideoVo> videoVos = new ArrayList<>(); //数据空
        try {
            videoVos = vs.queryByCreateDate(); // 出错  没有出错
            //业务执行成功
            return CommontResult2.fail("查询成功", videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            //查询业务失败
            return CommontResult2.fail("查询失败", videoVos);
        }
    }


    @RequestMapping("/getPhoneCode")
    public Map<String, Object> getPhoneCode(String phone) {

        Map<String, Object> map = new HashMap<>();

        Map<String, Object> smsMap = AliYun.sendSms(phone);
        Object yzm = smsMap.get("yzm");
        System.out.println("验证码: " + yzm);
        if ((Boolean) smsMap.get("code")) {
            //代表成功
            //发送验证码成功
//                map.put("data", phone);
//                map.put("message", "发送验证码成功");
//                map.put("status", "100");
//            CommontResult cr = CommontResult.success("发送验证码成功", phone);
//            map.put("cr", cr);
            return CommontResult2.success("发送验证码成功", phone);

        } else {
            //代表失败
            //发送验证码失败
//                map.put("data", null);
//                map.put("message", "发送验证码失败");
//                map.put("status", "104");

//            CommontResult cr = CommontResult.fail("发送验证码失败", null);
//            map.put("cr", cr);

            return CommontResult2.fail("发送验证码失败", null);
        }

    }
}
