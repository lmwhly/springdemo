package com.luoo.study.t;

import sun.nio.cs.ext.SJIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Luoo2 on 2016/3/31.
 */
public class Canvas {

    public String toString() {
        return "Canvas";
    }

    public void drawAll(List<? extends Shape> shapes) {
        for (Shape shape : shapes) {
            shape.draw(this);
        }
    }

    static <T> void fromArrayToCollection(T[] a,Collection<T> c){

        for (T t : a) {
            c.add(t);
        }

    }

    static <T> void fromArrayToCollection(Collection<? extends T> a,Collection<T> b){

        for (T t : a) {

            b.add(t);

        }

    }

    public <T> T getObject(Class<T> c) throws IllegalAccessException, InstantiationException {

       T t = c.newInstance();
        return t;
    }



    public static void main(String[] args) {
        List<Shape> list = new ArrayList<Shape>();

        list.add(new Circle());
        list.add(new Rectangle());
        Canvas c = new Canvas();
        c.drawAll(list);

        List<String> a = new ArrayList<String>();

        List<Object> b = new ArrayList<Object>();

        fromArrayToCollection(a, b);


    }



}
