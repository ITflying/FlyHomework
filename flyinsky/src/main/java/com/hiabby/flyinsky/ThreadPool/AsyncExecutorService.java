package com.hiabby.flyinsky.ThreadPool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步线程池
 * @author
 * @date 2020/3/16
 **/
public class AsyncExecutorService {
    /**
     * 核心线程数
     */
    private static final int CORE_THREAD_SIZE = 50;
    /**
     * 允许最大的线程数
     */
    private static final int MAX_THREAD_SIZE = 100;
    /**
     * 线程池空闲时，线程存活的时间
     */
    private static final long TIME_OUT_IN_SECOND = 10L;
    /**
     * 队列数量
     */
    private static final int WORKING_QUEUE_SIZE = 100;

    /**
     * 任务队列，对应无界队列
     */
    private static final LinkedBlockingQueue<Runnable> WORKING_QUEUE = new LinkedBlockingQueue<>(WORKING_QUEUE_SIZE);

    /**
     * 异步线程池：用谷歌的guava框架
     * ThreadPoolExecutor参数解释
     *   1.corePoolSize 核心线程池大小
     *   2.maximumPoolSize 线程池最大容量大小
     *   3.keepAliveTime 线程池空闲时，线程存活的时间
     *   4.TimeUnit 时间单位
     *   5.ThreadFactory 线程工厂
     *   6.BlockingQueue任务队列
     *   7.RejectedExecutionHandler 线程拒绝策略
     */
    public static final ThreadPoolExecutor EXECUTOR =
            new ThreadPoolExecutor(CORE_THREAD_SIZE, MAX_THREAD_SIZE, TIME_OUT_IN_SECOND, TimeUnit.SECONDS, WORKING_QUEUE,
                    new ThreadFactoryBuilder().build(), (runnable, executor) -> {
                try {
                    executor.getQueue().put(runnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
    

}
