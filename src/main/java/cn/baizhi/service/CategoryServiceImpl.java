package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao cd;


    //根据级别 查询 级别
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByLevels(int levels) {
        return cd.queryByLevels(levels);
    }
    //根据父项id 查询所有二级类别
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByParentId(String id) {
        return cd.queryByParentId(id);
    }
    // 添加类别
    @DeleteCache
    @Override
    public void save(Category category) {
        category.setId(UUID.randomUUID().toString());
        cd.save(category);
    }
    //删除二级类别
    @DeleteCache
    @Override
    public Map<String,Object> delete(String id) {
        /*
        1现根据地查询当前类别信息
        2如果是二级直接删
        3如果是一级类别。判断下有没有二级类别  ，有不能删，没有可以删
         */
        Map<String,Object>map=new  HashMap<>();
        Category category = cd.queryById(id);
        Integer levels = category.getLevels();
        if (levels==1){
            //一级类别
            List<Category> categories = cd.queryByParentId(id);
            if(categories.size()==0){
                cd.delete(id);
                map.put("flat",true);
            }else {
                map.put("flat",false);
                map.put("msg","无法删除,内部存在二级类别" );
            }
        }else{
            //无论传递的是一级类别 还是二级类别  目前都是删除
            cd.delete(id);
        }
        return map;
    }


}
