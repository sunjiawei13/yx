package cn.baizhi.test;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Excel(name = "学生id")
    private String id;
    @Excel(name ="学生姓名")
    private String name;
    @Excel(name ="学生年龄")
    private Integer age;
    @Excel(name ="学生生日",exportFormat = "yyyy年MM月dd日",importFormat ="yyyy年MM月dd日" )
    private Date bir;
}
