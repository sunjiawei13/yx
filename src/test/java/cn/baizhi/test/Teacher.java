package cn.baizhi.test;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    //                   是否需要纵向合并单元格(用于含有list中,单个的单元格,合并list创建的多个row)
    @Excel(name = "老师id",needMerge = true)
    private String id;
    @Excel(name = "老师名字",needMerge = true)
    private String name;
    @Excel(name = "老师工资",needMerge = true)
    private double salary;
    //      集合
    @ExcelCollection(name = "对应的学生")
    private List<Student> stus;



}
