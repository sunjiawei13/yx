package cn.baizhi.dao;

import cn.baizhi.entity.Admin;

public interface AdminDao {

    //查一个
    Admin queryByUsername(String username);
}
