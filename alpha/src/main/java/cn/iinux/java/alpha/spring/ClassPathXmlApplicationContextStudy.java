package cn.iinux.java.alpha.spring;

import lombok.var;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassPathXmlApplicationContextStudy {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Object s = ctx.getBean("student");
        System.out.println(s);
    }
}
