package com.mycompany.helloworld.springboot.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DogConfig {
    @Bean
    // @ConditionalOnMissingBean
    // @ConditionalOnBean
    public Animal animal() {
        return new Dog();
    }
}
