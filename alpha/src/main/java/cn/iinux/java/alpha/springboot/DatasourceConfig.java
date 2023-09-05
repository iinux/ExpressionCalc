package cn.iinux.java.alpha.springboot;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://127.0.0.1:3306/testdb?useSSL=false")
                .username("root")
                .password("root")
                .build();
    }
}
