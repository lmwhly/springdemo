package com.luoo.study.Spring041.aop05;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Luoo
 * @create 2016-08-11 17:21
 */

public class SurroundAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation i) throws Throwable {
        //前置横切逻辑
        System.out.println("方法" + i.getMethod() + " 被调用在对象" + i.getThis() + "上，参数 " + i.getArguments());
        //方法调用
        Object ret = i.proceed();
        //后置横切逻辑
        System.out.println("返回值：" + ret);
        return ret;
    }
}
