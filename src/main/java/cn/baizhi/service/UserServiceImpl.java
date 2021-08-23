package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.AliYun;
import cn.baizhi.vo.MonthAndCount;
import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao ud;
    //分页查业务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(int page, int size) {//第2页 的3条数据
       Map<String,Object>map=new HashMap<>();
        //得知道总页数 map,put("","");
        //   ，当前是第几页

        //如果是第一页 page-1*size 开始条数
        //        1-1*size=0*0=0， 2-1*size=1*3=3
        List<User> list = ud.queryRange((page - 1) * size, size);
        //分页查数据
        map.put("data",list );
        //储存当前页数
        map.put("page",page);//2
        //储存总页数           总条数/每页显示的条数=页数  10/3=3+1页
        int countNum = ud.queryPageNum();//总条数
        //总页数
        int pageNum=countNum%size==0?countNum/size:countNum/size+1;
        map.put("pageNum", pageNum);
        return map;
    }
    //修改状态的业务
    @DeleteCache
    @Override
    public void updateStatus(String id, int status) {
        ud.updateStatus(id, status);
    }

    //添加
    @DeleteCache
    @Override
    public void save(MultipartFile file, User user) {

        user.setSex("男");
        //头像上传
        //上传时的文件名
        String fileName = file.getOriginalFilename();
        Date date = new Date();
        long time = date.getTime();
        String finalName=time+fileName;
        AliYun.uploadByBytes(file, finalName);


        //user对象中的数据入库
        //user对象是controller 传递过来的
        user.setId(UUID.randomUUID().toString());
        user.setCreatedate(new Date());
        //http://2021815class.soo-cn-beijing.aliyuncs.com/1.jpg
        user.setHeadimg("https://20210816class.oss-cn-beijing.aliyuncs.com/"+finalName);
        user.setStatus(1);

        ud.add(user);
        Map<String, Object> map = queryUserSexCount();
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-48c84eb5810d4f20b0ab85b8b9a10df0");
        goEasy.publish("my_channel", JSONObject.toJSONString(map));


    }
    //添加用户业务    用户的个数就变   只要用户个数变   就像频道发消息   所有订阅频道的都可以收到消息


    //删除业务
    @DeleteCache
    @Override
    public void deleteById(String id,String headimgurl) {
        /*
        * 只有用户id；  通过用户id删除表中书库
        * 有图片的url： 删除图片
        * 问题 ：oss数据储存了改头像 ，头像怎么山、通过头像url去删除。怎么打的到url
        * */
        //删除图片
        //http://2021815class.soo-cn-beijing.aliyuncs.com/1.jpg
       int i= headimgurl.lastIndexOf('/');
        String fileName = headimgurl.substring(i + 1);
        AliYun.deleteFile(fileName);
        //删除用户信息
        ud.deleteById(id);
    }
    //用户导出
    @Override
    public void downloadUser() {
        List<User> list = ud.queryAll();
        for (User user : list) {
            ///System.out.println(user);
            //将用户头像下载到本地
            String headimg = user.getHeadimg();
            //http://2101class.oss-cn-beijing.aliyuncs.com/1.jpg
            int i = headimg.lastIndexOf('/');//截取
            String fileName = headimg.substring(i + 1);
            AliYun.download(fileName);
            user.setHeadimg("E:\\bb\\"+fileName);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息","用户信息表"),
                User.class, list);

        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream("E:\\usereasypoi.xls");
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*提供用户数据分析业务*/
    @Transactional(propagation = Propagation.SUPPORTS)
    /*如果没有注解 是该方法开启事务 ，
     如果没有注解表示该方法独立运行时不参加事务 如果其他业务方法调用  他会融入当前事务
     这个业务方法  其他业务中肯定会调用    一个是用户注册   有个是用户注销
     因为我们要是实现echarts实时变化    以为这只要是用户人数变化(注册注销) name就应该向频道重新发送实时人数  所以注册业务 注销业务会调用改方法
     将这个方法返回的map集合发送到频道中 页面就能收到新数据变化
     注册和注销业务  一定开启事务
     如果没有这个注解 表示改方法也会 开启事务
    */
    @Override
    public Map<String, Object> queryUserSexCount() {
        List<String> data = new ArrayList<>();
        //                              1 : 5  2: 0   3:20
        //查询男生每月注册人数
        List<Integer> manCount = new ArrayList<>();
        //查询女生每月注册人数
        List<Integer> womanCount = new ArrayList<>();

        List<MonthAndCount> man = ud.queryMonthCount("男");
        /*
              MonthAndCount      6   1
              MonthAndCount      7   1
         */
        List<MonthAndCount> nv = ud.queryMonthCount("女");

        //向data集合中存储1-12月
        for(int i=1;i<=12;i++){
            data.add(i+"月");
            //女
            boolean flag2=false;
            for (MonthAndCount monthAndCount : nv) {
                if (monthAndCount.getMonth()==i){//7 = 7
                    womanCount.add(monthAndCount.getCount());
                    flag2=true;
                }
            }
            if(!flag2){
                womanCount.add(0);
            }

            //男生
            boolean flag=false;
            for (MonthAndCount monthAndCount : man) {
                if (monthAndCount.getMonth()==i){//7 = 7
                    manCount.add(monthAndCount.getCount());
                    flag=true;
                }
            }
            if(!flag){
                manCount.add(0);
            }
        }

        //  存储了   月份    男生人数  女生人数
        Map<String,Object> map = new HashMap<>();
        map.put("data", data);
        map.put("manCount", manCount);
        map.put("womanCount", womanCount);
        return map;

    }
}
