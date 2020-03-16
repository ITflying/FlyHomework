package com.hiabby.flyquestion.niuke.febuary;


import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc: 用于一些题目的验证
 * @author: ITflying
 * @create: 2019-02-12 21:42
 **/
public class ClassQuestion {
    public static void main(String args[]) {
        // test1
        extendTestRun();

        // test2
        int a = 5, b = 6;
        System.out.println(a + ~b);
        System.out.println(new HashMap<String, String>().values());
    }

    /**
     * 1. A 派生出子类 B ， B 派生出子类 C ，如下声明会有问题吗？
     */
    static class ExtendTestA {
    }

    static class ExtendTestB extends ExtendTestA {
    }

    static class ExtendTestC extends ExtendTestB {
    }

    static void extendTestRun() {
        ExtendTestA a = new ExtendTestA();
        ExtendTestA b = new ExtendTestB();
        ExtendTestA c = new ExtendTestC();
    }

}
