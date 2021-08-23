package cn.baizhi.test;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Excel(name = "用户id")
    private String id;
    @Excel(name = "用户路径",type = 2,width = 30,height = 30)
    private String headpath;
    @Excel(name = "用户名字")
    private String username;

}
