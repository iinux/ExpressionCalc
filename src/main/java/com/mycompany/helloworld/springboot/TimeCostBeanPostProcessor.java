package com.mycompany.helloworld.springboot;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TimeCostBeanPostProcessor implements BeanPostProcessor {

    Map<String, Long> costMap = Maps.newConcurrentMap();

    @Override
    public Object postProcessBeforeInitialization(Object bean, @NotNull String beanName) throws BeansException {
        costMap.put(beanName, System.currentTimeMillis());
        String classname = bean.getClass().getName();
        System.out.println(beanName + " " + classname + " in postProcessBeforeInitialization");

        boolean containsBean = defaultListableBeanFactory.containsBean(beanName);
        if (containsBean) {
            // System.out.println(containsBean);
        }

        if (false) {
            //移除bean的定义和实例
            defaultListableBeanFactory.removeBeanDefinition(beanName);
            //注册新的bean定义和实例
            defaultListableBeanFactory.registerBeanDefinition(beanName,
                    BeanDefinitionBuilder.genericBeanDefinition(EnhancerStudy.Car.class).getBeanDefinition());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        long start;
        try {
            start = costMap.get(beanName);
        } catch (NullPointerException e) {
            System.out.printf("bean %s NullPointerException%n", beanName);
            return bean;
        }
        long cost = System.currentTimeMillis() - start;
        if (cost > 1000 || true) {
            costMap.put(beanName, cost);
            System.out.printf("class: %s bean: %s time: %d\n", bean.getClass().getName(), beanName, cost);
        }
        return bean;
    }

    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;
}
