package cn.baizhi.commont;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommontResult {

    private String status;
    private String message;
    private Object data;

    //定义一个方法表示成功的状态
    public void success(){

    }
}
