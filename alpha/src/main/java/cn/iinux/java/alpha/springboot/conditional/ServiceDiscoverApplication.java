package cn.iinux.java.alpha.springboot.conditional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

// @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServiceDiscoverApplication implements CommandLineRunner {

    @Autowired
    private Animal animal;

    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoverApplication.class, args);
    }

    //测试方法
    @Override
    public void run(String... args) throws Exception {
        String eat = animal.eat();
        System.out.println(eat);
    }
}
