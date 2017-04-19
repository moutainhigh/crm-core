package com.cafe.crm;

import com.cafe.crm.initMet.InitClient;
import com.cafe.crm.initMet.InitMenu;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAutoConfiguration
public class Main extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean(initMethod = "init")
    public InitClient initClient() {
        return new InitClient();
    }
    @Bean(initMethod = "init2")
    public InitMenu initTestData() {
        return new InitMenu();
    }
}
