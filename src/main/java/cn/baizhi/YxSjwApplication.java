package cn.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("cn.baizhi.dao") //dao才能创建对象
@SpringBootApplication
public class YxSjwApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxSjwApplication.class, args);
    }

}
