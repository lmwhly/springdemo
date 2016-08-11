package com.luoo.study.Spring041.aop04;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * @author Luoo
 * @create 2016-08-11 17:07
 */

public class DynamicProxy implements MethodInterceptor {
    Object targetObject;

    public Object getProxyObject(Object object) {
        this.targetObject = object;
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(this);
        enhancer.setSuperclass(targetObject.getClass());
        return enhancer.create();
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //被织入的内容，开始时间
        long start = System.currentTimeMillis();
        lazy();
        //使用反射在目标对象上调用方法并传入参数
        Object result = methodProxy.invoke(targetObject, args);
        //被织入的内容，结束时间
        Long span = System.currentTimeMillis() - start;
        System.out.println("共用时：" + span);

        return result;
    }

    //模拟延时
    public void lazy() {
        try {
            int n = (int) new Random().nextInt(500);
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
