package com.example.blogapp;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JpaTestConfig {

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.h2.Driver");
        dataSource.setUrl("jdfc:h2:mem:test;DB_CLOSE_DELAY=-1");
        return dataSource;
    }
}
