package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {

    //根据级别 查询 级别
    List<Category> queryByLevels(int levels);

    //根据父项id 查询所有二级类别
    List<Category> queryByParentId(String id);

    // 添加类别
    void save(Category category);

    //根据id删除
    void delete(String id);

    //根据id查询出这条数据的信息
    Category queryById(String id);




}
