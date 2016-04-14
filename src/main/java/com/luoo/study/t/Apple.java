package com.luoo.study.t;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luoo2 on 2016/3/31.
 */
public class Apple<T> {
    private T info;


    public Apple(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public <T> void test1(List<T> l) {
        for (T i : l) {
            System.out.println(i.getClass());
        }
    }

    public static void main(String[] args) {
        Apple<String> ap1 = new Apple<String>("Ð¡Æ»¹û");
        System.out.println("ap1 = " + ap1.getInfo());

        Apple<Double> ap2 = new Apple<Double>(1.23);
        System.out.println("ap2 = " + ap2.getInfo());

        List list = new ArrayList();
        list.add("ddd");

        ap1.test1(list);
    }
}
