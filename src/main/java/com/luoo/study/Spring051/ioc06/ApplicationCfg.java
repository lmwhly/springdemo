package com.luoo.study.Spring051.ioc06;

/**
 * @author Luoo
 * @create 2016-08-11 16:20
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 容器的配置类
 */
@Configuration
@ComponentScan(basePackages="com.luoo.study.Spring051.ioc06")
public class ApplicationCfg {
    @Bean
    public User getUser(){
        return new User("成功");
    }

}
