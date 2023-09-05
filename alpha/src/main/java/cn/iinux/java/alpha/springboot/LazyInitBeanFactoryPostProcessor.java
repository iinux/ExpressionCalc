package cn.iinux.java.alpha.springboot;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LazyInitBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (true) {
            System.out.println("LazyInitBeanFactoryPostProcessor postProcessBeanFactory");
            return;
        }
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            beanFactory.getBeanDefinition(beanName).setLazyInit(true);
        }
    }
}
