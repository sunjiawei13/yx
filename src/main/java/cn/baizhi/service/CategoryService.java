package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    //根据级别 查询 级别
    List<Category> queryByLevels(int levels);
    //根据父项id 查询所有二级类别
    List<Category> queryByParentId(String id);
    //添加类别
    void save(Category category);
    //删除
    Map<String,Object> delete(String id);

}
