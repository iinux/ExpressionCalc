package cn.iinux.java.alpha.springboot;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.io.Reader;

@SpringBootApplication(
        scanBasePackages = "cn.iinux.java.alpha.springboot")
// @MapperScan("cn.iinux.java.alpha.springboot.db.mapper")
@EnableScheduling
@EnableAsync
@Slf4j
public class Start {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Start.class, args);

        JSONObject returnObject = new JSONObject();
        returnObject.put("a", "b");
        log.info("started, obj={}", returnObject);
        context.start();
    }

    // @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(reader);
    }
}
