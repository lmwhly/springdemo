package com.luoo.study.Spring041.aop08;

import org.aspectj.lang.JoinPoint;

/**
 * @author Luoo
 * @create 2016-08-11 17:47
 */

public class Advices {
    public void before(JoinPoint jp) {
        System.out.println("--------------------bofore--------------------");

        System.out.println("方法名：" + jp.getSignature() + "，参数：" + jp.getArgs().length + "，代理对象：" + jp.getTarget());


    }

    public void after(JoinPoint jp) {
        System.out.println("--------------------after--------------------");
    }


}
