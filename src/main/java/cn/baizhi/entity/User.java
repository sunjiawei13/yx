package cn.baizhi.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable{


  @Excel(name = "用户id")
  private String id;
  @Excel(name = "用户名")
  private String username;
  @Excel(name = "用户手机")
  private String phone;
  @Excel(name = "用户头像",type = 2)
  private String headimg;
  @Excel(name = "用户描述")
  private String brief;
  @Excel(name = "用户微信")
  private String wechat;
  @Excel(name = "用户注册时间",exportFormat = "yyyy-MM-dd")
  @JsonFormat(pattern = "yyyy-MM-dd")//日期格式
  private Date createdate;
  @Excel(name = "用户状态")
  private Integer status;
  @Excel(name = "用户性别")
  private String sex;

  //日期格式
//  public String getCreatedate(){
//    return new SimpleDateFormat("yyyy-MM-dd").format(createdate);
//
//  }

}
