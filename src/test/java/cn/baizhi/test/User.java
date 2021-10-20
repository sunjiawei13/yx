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
    //                  导出类型 1是文本 2是图片,3是函数,10 是数字 默认是文本
    @Excel(name = "用户头像",type = 2,width = 30,height = 30)
    private String headpath;
    @Excel(name = "用户名字")
    private String username;

}
