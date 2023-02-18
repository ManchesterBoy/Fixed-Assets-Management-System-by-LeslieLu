package com.example.sboot;

import com.github.yulichang.injector.MPJSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * springboot启动配置
 * @author LeslieLu
 */
@SpringBootApplication(exclude = {MPJSqlInjector.class})
//@EnableCaching //开启缓存
//@MapperScan("com.example.sboot.common")
public class SbootApplication {



    public static void main(String[] args) {
        SpringApplication.run(SbootApplication.class, args);
    }


    }




