package cn.baizhi.controller;


import cn.baizhi.entity.Category;
import cn.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


//@Controller //创建对象
//@ResponseBody //后端没页面，响应json

@RestController  //合二为一
@CrossOrigin//解决跨域问题
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService cs;

    //根据级别 查询 级别
    @RequestMapping("/queryByLevels")
    public List<Category> queryByLevels(int levels){
        return cs.queryByLevels(levels);
    }
    //根据父项id 查询所有二级类别
    @RequestMapping("/queryByParentId")
    public List<Category> queryByParentId(String id){
        return cs.queryByParentId(id);
    }
    // 添加类别
    @RequestMapping("/save")
    public void save(@RequestBody Category category){
        cs.save(category);
    }
    //删除二级类别
    @RequestMapping("/delete")
    public Map<String,Object> delete(String id){
        return cs.delete(id);
    }


}
