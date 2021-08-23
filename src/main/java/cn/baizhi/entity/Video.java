package cn.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {

  private String id;
  private String title;
  private String brief;
  private String coverpath;
  private String videopath;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date createdate;
  private Category category;
  private User user;
  private String groupid;


}
