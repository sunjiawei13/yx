package cn.baizhi.commont;

import java.util.HashMap;
import java.util.Map;

public class CommontResult2 {


    public static Map<String,Object> success(String message,Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("status", "100");
        map.put("message", message);
        map.put("data", data);
        return map;
    }


    public static Map<String,Object> fail(String message,Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("status", "104");
        map.put("message", message);
        map.put("data", data);
        return map;
    }
}

