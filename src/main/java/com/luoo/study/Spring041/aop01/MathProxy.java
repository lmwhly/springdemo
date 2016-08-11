package com.luoo.study.Spring041.aop01;

import java.util.Random;

/**
 * @author Luoo
 * @create 2016-08-11 16:34
 */

public class MathProxy implements IMath{
    //被代理的对象
    IMath math = new Math();

    //加
    public int add(int n1, int n2) {
        //开始时间
        long start = System.currentTimeMillis();
        lazy();
        int result = math.add(n1, n2);
        Long span = System.currentTimeMillis() - start;
        System.out.println("共用时：" + span);
        return result;
    }

    @Override
    public int sub(int n1, int n2) {
        //开始时间
        long start = System.currentTimeMillis();
        lazy();
        int result = math.sub(n1, n2);
        Long span = System.currentTimeMillis() - start;
        System.out.println("共用时：" + span);
        return result;
    }

    @Override
    public int mut(int n1, int n2) {
        //开始时间
        long start = System.currentTimeMillis();
        lazy();
        int result = math.mut(n1, n2);
        Long span = System.currentTimeMillis() - start;
        System.out.println("共用时：" + span);
        return result;
    }

    @Override
    public int div(int n1, int n2) {
        //开始时间
        long start = System.currentTimeMillis();
        lazy();
        int result = math.div(n1, n2);
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
