package cn.iinux.java.alpha.springboot.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatConfig {
    @Bean(name = "cat1")
    // @ConditionalOnMissingBean
    @ConditionalOnBean
    public Animal animal() {
        return new Cat();
    }
}
