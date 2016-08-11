package com.luoo.study.Spring041.aop05;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author Luoo
 * @create 2016-08-11 17:19
 */

public class BeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("-----------------前置通知-----------------");
    }
}
