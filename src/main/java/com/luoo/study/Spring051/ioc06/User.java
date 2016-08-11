package com.luoo.study.Spring051.ioc06;

import org.springframework.stereotype.Component;

/**
 * @author Luoo
 * @create 2016-08-11 16:24
 */

@Component("user1")
public class User {
    public User() {
        System.out.println("创建User对象");
    }

    public User(String msg) {
        System.out.println("创建User对象" + msg);
    }

    public void show() {
        System.out.println("一个学生对象！");
    }
}
