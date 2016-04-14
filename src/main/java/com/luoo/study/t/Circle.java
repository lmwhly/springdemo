package com.luoo.study.t;

/**
 * Created by Luoo2 on 2016/3/31.
 */
public class Circle extends Shape {


    @Override
    public void draw(Canvas c) {
        System.out.println("在画布" + c + "上画一个圆");
    }
}
