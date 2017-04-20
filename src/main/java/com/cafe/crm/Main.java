package com.cafe.crm;


import com.cafe.crm.initMet.InitClient;
import com.cafe.crm.initMet.InitMenu;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by User on 18.04.2017.
 */

@SpringBootApplication
@EnableAutoConfiguration
public class Main extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }



/*    @Bean(initMethod = "init")
    public InitMenu initTestData() {
        return new InitMenu();
    }


    @Bean(initMethod = "init")
    public InitClient initC() {
        return new InitClient();
    }*/
}
