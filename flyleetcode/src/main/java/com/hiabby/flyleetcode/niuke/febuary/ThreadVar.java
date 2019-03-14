package com.hiabby.flyleetcode.niuke.febuary;

/**
 * @description: 假设 a 是一个由线程 1 和线程 2 共享的初始值为 0 的全局变量，则线程 1 和线程 2 同时执行一段代码，结果不可能是1
 * @author: ITflying
 * @create: 2019-02-12 21:18
 **/
public class ThreadVar {
    static int a = 0;

    public static void main(String args[]) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                boolean isOdd = false;

                for (int i = 1; i <= 2; ++i) {
                    isOdd = i % 2 == 1;
                    a += i * (isOdd ? 1 : -1);
                }
                System.out.println(a + "");
            }
        };
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
    }
}
