package com.hiabby.flyinterview.march;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

/**
 * @desc: 生产者消费模式
 * @author: ITflying
 * @create: 2019-03-14 12:10
 **/
public class Eight {
    public static void main(String[] args) {
        prodcutConsumer();
    }

    private static void prodcutConsumer() {
        int len = 10;
        List<Integer> queue = Lists.newArrayList();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Random random = new Random();
                        int date = random.nextInt(100);
                        System.out.println(Thread.currentThread().getId() + "生产了：" + date);
                        synchronized (queue) {
                            if (queue.size() > len) {
                                queue.notifyAll();
                                queue.wait();
                            }
                            queue.add(date);
                        }

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Integer date = null;
                        synchronized (queue) {
                            if (queue.size() == 0) {
                                queue.wait();
                                queue.notifyAll();
                            }
                            date = queue.remove(0);
                        }
                        System.out.println(Thread.currentThread().getId() + "消费了：" + date);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
