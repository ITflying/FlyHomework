package com.hiabby.flyinsky.ThreadPool;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author
 * @date 2020/3/16
 **/
public class MyThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程的各种方法：实现Runnable接口，继承Thread，使用Callable和Future创建线程，使用线程池创建
        new Thread(() -> System.out.println(Thread.currentThread().getName() + ":正在执行....")).start();

        new MThread1().start();
        new Thread(new MThread2()).start();


        FutureTask<Integer> task = new FutureTask<>(new MThread3());
        Thread thread = new Thread(task, "线程A");
        thread.start();
        System.out.println("Callable 返回结果为：" + task.get());

        System.out.println("================ 线程池创建 ================");
        System.out.println("=> 自建异步线程池调用");
        AsyncExecutorService.EXECUTOR.execute(() ->{
            System.out.println(Thread.currentThread().getName() + ":正在执行....\n");
        });
        
        ThreadPoolExecutorService pool = new ThreadPoolExecutorService();
        pool.createSingleThreadExecutor();
        pool.createFixedThreadPool();
        pool.createCachedThreadPool();
        pool.createScheduledThreadPoolExecutor();


    }
}


class MThread1 extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":正在执行....");
    }
}

class MThread2 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ":正在执行....");
    }
}

class MThread3 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + ":进入Callable....");
        int retVal = 10;
        return retVal;
    }
}


class ThreadPoolExecutorService {
    /**
     * 单线程线程池：只有一个线程工作，按照任务提交顺序执行
     */
    void createSingleThreadExecutor() {
        System.out.println("=> 单线程线程池 newSingleThreadExecutor");
        ExecutorService pool = Executors.newSingleThreadExecutor();

        Thread t1 = new MThread1();
        Thread t2 = new MThread1();
        Thread t3 = new MThread1();

        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);

        pool.shutdown();
    }

    /**
     * 固定数量线程池
     */
    void createFixedThreadPool() {
        System.out.println("=> 固定数量线程线程池 newFixedThreadPool");
        ExecutorService pool = Executors.newFixedThreadPool(2);

        Thread t1 = new MThread1();
        Thread t2 = new MThread1();
        Thread t3 = new MThread1();
        Thread t4 = new MThread1();

        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);

        pool.shutdown();
    }

    void createCachedThreadPool() {
        System.out.println("=> 缓冲线程池 newCachedThreadPool");
        ExecutorService pool = Executors.newCachedThreadPool();

        Thread t1 = new MThread1();
        Thread t2 = new MThread1();
        Thread t3 = new MThread1();
        Thread t4 = new MThread1();

        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);

        pool.shutdown();
    }

    void createScheduledThreadPoolExecutor() {
        System.out.println("=> 无限制线程池 ScheduledThreadPoolExecutor ");
        ScheduledThreadPoolExecutor exc = new ScheduledThreadPoolExecutor(1);
        // 每隔一段时间输出一次
        exc.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + ":正在执行A....");
            System.out.println(new Date(System.nanoTime()));
        }, 1000, 5000, TimeUnit.MILLISECONDS);

        // 不会相互影响
        exc.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + ":正在执行B....");
            System.out.println(new Date(System.nanoTime()));
        }, 1000, 2000, TimeUnit.MILLISECONDS);
    }
}