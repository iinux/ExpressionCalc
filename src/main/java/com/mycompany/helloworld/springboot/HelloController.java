package com.mycompany.helloworld.springboot;

import com.mycompany.helloworld.springboot.apiversion.ApiVersion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class HelloController implements CommandLineRunner {
    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MyFactoryBean myFactoryBean;

    @Autowired
    private HelloService helloService;

    @GetMapping("/allBeans")
    public String[] allBeans() {
        String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        return beans;
    }

    @GetMapping("/newUser")
    public String newUser() {
        helloService.asyncNewUser();
        return "success";
    }

    @ApiVersion("1.0")
    @GetMapping("/apiVersion")
    public String apiVersion() {
        return "api version 1";
    }

    @ApiVersion("2.0")
    @GetMapping("/apiVersion")
    public String apiVersion2() {
        return "api version 2";
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.getClass() + " CommandLineRunner");
        System.out.println(myFactoryBean);
    }

    // @Qualifier("haha")


    @Test
    public void test1() {
        System.out.println(AnnotationUtils.findAnnotation(this.getClass(), Component.class));
        System.out.println(AnnotationUtils.findAnnotation(this.getClass(), Test.class));
    }
}
