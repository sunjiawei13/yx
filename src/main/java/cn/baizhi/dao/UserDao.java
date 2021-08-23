package cn.baizhi.dao;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    //范围查询
    List<User> queryRange(@Param("start") int start,@Param("end") int end);

    //查总条数
    int queryPageNum();

    //修改状态
    void updateStatus(@Param("id") String id,@Param("status") int status);

    //添加
    void add(User user);

    //删除
    void deleteById(String id);
    //查所有
    List<User>queryAll();
    //查询男生每月注册人数
    List<MonthAndCount> queryMonthCount(String sex);

    //查询女生每月注册人数
}
