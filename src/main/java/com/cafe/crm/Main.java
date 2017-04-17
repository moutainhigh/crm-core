package com.cafe.crm;

import com.cafe.crm.initMet.Test;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by User on 16.04.2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Main extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");

    }

    @Bean(initMethod = "init")
    public Test initTestData() {
        return new Test();
    }


}
