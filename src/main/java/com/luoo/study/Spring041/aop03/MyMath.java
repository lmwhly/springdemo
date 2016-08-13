package com.luoo.study.Spring041.aop03;

import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author Luoo
 * @create 2016-08-11 16:29
 */

@Service
public class MyMath {
    //加
    @MyAnno
    public int add(int n1, int n2) {
        int result = n1 + n2;
        System.out.println(n1 + "+" + n2 + "=" + result);
        return result;
    }

    //减
    public int sub(int n1, int n2) {
        int result = n1 - n2;
        System.out.println(n1 + "-" + n2 + "=" + result);
        return result;
    }

    //乘
    public int mut(int n1, int n2) throws SQLException {
        int result = n1 * n2;
        System.out.println(n1 + "X" + n2 + "=" + result);
        if(1==1)
            throw new SQLException("SQL test");
        return result;
    }

    //除
    public int div(int n1, int n2) {
        int result = n1 / n2;
        System.out.println(n1 + "/" + n2 + "=" + result);
        return result;
    }

    public int argtest(int n1) throws SQLException {
        System.out.println("this is "+n1);

        return n1;
    }






}
