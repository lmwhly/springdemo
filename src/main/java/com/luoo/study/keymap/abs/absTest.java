package com.luoo.study.keymap.abs;

import com.luoo.study.keymap.interfaces.ITestSV;

/**
 * abs
 *
 * @author Luoo2
 * @create 2016-04-18 22:47
 */

public abstract class absTest implements ITestSV{
    public void doTest1() {
        System.out.println("do test1");
    }

    public abstract void doTest2();

    @Override
    public void getTestResult() {
        System.out.println("absTest get Test Result!!");


    }

}
