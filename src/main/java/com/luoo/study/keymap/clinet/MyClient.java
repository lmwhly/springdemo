package com.luoo.study.keymap.clinet;

import com.luoo.study.keymap.abs.absTest;

/**
 * client class
 *
 * @author Luoo2
 * @create 2016-04-18 22:55
 */

public class MyClient extends absTest {


    public static void main(String[] args) {
        MyClient myClient = new MyClient();

        myClient.doTest1();

        myClient.doTest2();

        System.out.println("hello");
    }

    @Override
    public void doTest1() {
        super.doTest1();
    }

    @Override
    public void doTest2() {

    }

    @Override
    public void getTestResult() {
        super.getTestResult();
        System.out.println("myclient getTestResult!!!");
    }
}
