package com.luoo.study.Spring041.aop05;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author Luoo
 * @create 2016-08-11 17:20
 */

public class AfterAdvice  implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("-----------------后置通知-----------------");
    }
}
