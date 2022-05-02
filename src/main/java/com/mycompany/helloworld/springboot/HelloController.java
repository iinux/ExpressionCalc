package com.mycompany.helloworld.springboot;

import com.mycompany.helloworld.sensitive.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController implements CommandLineRunner {
    @Autowired
    private ApplicationContext appContext;

    @GetMapping("/allBeans")
    public String[] allBeans() {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        return beans;
    }

    @GetMapping("/newUser")
    public String newUser() {
        appContext.publishEvent(new UserRegisterEvent(this, new User()));
        return "success";
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.getClass() + " CommandLineRunner");
    }

    // @Qualifier("haha")


    public void generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        // 指定 逆向工程配置文件
        File configFile = new File("src/main/resources/generatorConfig.xml");
        // File configFile = new File("generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    public void db() throws IOException {
        SqlSession sqlSession = null;
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 查询数据库内容
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test1() {
        System.out.println(AnnotationUtils.findAnnotation(this.getClass(), Component.class));
        System.out.println(AnnotationUtils.findAnnotation(this.getClass(), Test.class));
    }
}
