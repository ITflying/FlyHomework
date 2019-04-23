package com.hiabby.flyinterview.march;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @desc: 死锁，思路就是创建两个list，然后让线程1拿着锁a去请求锁b，让线程2拿着锁b去请求锁a
 * @author: ITflying
 * @create: 2019-03-14 11:54
 **/
public class Seven {
    public static void main(String[] args) {
        deadLock();
    }

    private static void deadLock(){
        final List<Integer> list1 = Lists.newArrayList(1,2,3,4);
        final List<Integer> list2 = Lists.newArrayList(11,22,33,44);

        // 线程1拿着锁a请求锁b
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list1){
                    list1.forEach( x -> System.out.println(x));
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    synchronized (list2){
                        list2.forEach(x -> System.out.println(x));
                    }
                }
            }
        }).start();

        // 线程2拿着锁b请求锁a
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list2){
                    list2.forEach( x -> System.out.println(x));
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    synchronized (list1){
                        list1.forEach(x -> System.out.println(x));
                    }
                }
            }
        }).start();
    }
}
