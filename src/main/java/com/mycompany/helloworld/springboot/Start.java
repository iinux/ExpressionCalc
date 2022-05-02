package com.mycompany.helloworld.springboot;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        scanBasePackages = "com.mycompany.helloworld.springboot")
// @MapperScan("com.mycompany.helloworld.springboot.db.mapper")
@EnableScheduling
@Slf4j
public class Start {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);

        JSONObject returnObject = new JSONObject();
        returnObject.put("a", "b");
        log.info("started, obj={}", returnObject);
    }
}
