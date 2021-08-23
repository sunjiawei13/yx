package cn.baizhi.entity;

import lombok.*;

import java.io.Serializable;


//@Setter   //set
//@Getter   //get
//@ToString //toString
@NoArgsConstructor//无参构造
@AllArgsConstructor//全参构造
@Data   //相当于set.get.tostring.无参，没有全参。 有全参就必须无参

public class Admin implements Serializable {

  private String id;
  private String username;
  private String password;
  private long status;

}