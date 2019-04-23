package com.hiabby.flyinterview.march;

import com.sun.deploy.util.StringUtils;

import java.util.Objects;

/**
 * @desc: 一些字符串的题目
 * @author: ITflying
 * @create: 2019-03-20 10:32
 **/
public class Eleven {
    public static void main(String[] args) {
        System.out.println(factorial(5));
    }

    /**
     * 反转字符串
     */
    private static void reversStr(){
        String testStr = "abcdefg";

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Adfasdf");
        stringBuffer.reverse();
        System.out.println(stringBuffer);
    }

    /**
     * 字符串寻找
     */
    private static void findInex(){
        String testStr = "abcdefg";
        String s = "bcs";

        int index = testStr.indexOf(s);
        System.out.println(index);
    }

    /**
     * 递归的方式计算阶乘
     */
    private static int factorial(int n ){
        if (Objects.equals(n, 0)){
            return 1;
        }
        return n*factorial(n-1);
    }
}
