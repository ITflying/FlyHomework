package com.hiabby.flyinterview.february;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @desc: 生产者消费者模式，使用多种方式可以实现：阻塞队列、使用notify/wait()和await()/signal()在生产者和消费者线程中合作
 * @author: ITflying
 * @create: 2019-02-25 21:31
 **/
public class One {
    /**
     * 实例化lock
     */
    public static ReentrantLock lock = new ReentrantLock();

    /**
     * 锁a
     */
    public static Condition empty = lock.newCondition();

    /**
     * 锁b
     */
    public static Condition full = lock.newCondition();

    public static void main(String[] args) {
        int tag = 3;
        runProductConsumerFactory(tag);
    }

    public static void runProductConsumerFactory(int tag) {
        switch (tag) {
            case 1:
                // 1. 使用阻塞队列的方式
                BlockingQueue sharedQueue = new LinkedBlockingQueue();

                Thread blockingProduct = new Thread(new BlockingProduct(sharedQueue));
                Thread blockingConsumer = new Thread(new BlockingConsumer(sharedQueue));

                blockingProduct.start();
                blockingConsumer.start();
                break;
            case 2:
                // 2.使用wait和notify方法在生产者和消费者线程中合作
                List<Integer> queue = Lists.newArrayList();
                int length = 10;
                NWProducer p1 = new NWProducer(queue, length);
                NWProducer p2 = new NWProducer(queue, length);
                NWProducer p3 = new NWProducer(queue, length);
                NWConsumer c1 = new NWConsumer(queue);
                NWConsumer c2 = new NWConsumer(queue);
                NWConsumer c3 = new NWConsumer(queue);
                ExecutorService service = Executors.newCachedThreadPool();
                service.execute(p1);
                service.execute(p2);
                service.execute(p3);
                service.execute(c1);
                service.execute(c2);
                service.execute(c3);
                break;
            case 3:
                // 2.使用await和signalAll方法在生产者和消费者线程中合作
                List<Integer> queue3 = Lists.newArrayList();
                int length3 = 12;
                ASProducer ap1 = new ASProducer(queue3, length3);
                ASProducer ap2 = new ASProducer(queue3, length3);
                ASProducer ap3 = new ASProducer(queue3, length3);
                ASConsumer ac1 = new ASConsumer(queue3);
                ASConsumer ac2 = new ASConsumer(queue3);
                ASConsumer ac3 = new ASConsumer(queue3);
                ExecutorService service3 = Executors.newCachedThreadPool();
                service3.execute(ap1);
                service3.execute(ap2);
                service3.execute(ap3);
                service3.execute(ac1);
                service3.execute(ac2);
                service3.execute(ac3);
                break;
            default:
                System.out.println("没有可选择运行方式");
                break;
        }
    }
}


/**
 * 1.使用阻塞队列
 * 　因为BlockingQueue是一个阻塞队列，它的存取可以保证只有一个线程在进行
 * 所以根据逻辑，生产者在内存满的时候进行等待，并且唤醒消费者队列，反过来消费者在饥饿状态下等待并唤醒生产者进行生产。
 */
class BlockingProduct implements Runnable {
    private final BlockingQueue sharedQueue;

    public BlockingProduct(BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        int size = 10;
        for (int i = 0; i < size; i++) {
            try {
                System.out.println("生产者：" + i);
                sharedQueue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BlockingConsumer implements Runnable {
    private final BlockingQueue sharedQueue;

    public BlockingConsumer(BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("消费者：" + sharedQueue.take());
                if (sharedQueue.size() <= 0) {
                    System.out.println("无生产者，阻塞中");
                }
            } catch (InterruptedException e) {
                Logger.getLogger(BlockingConsumer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}


/**
 * 2.使用notify/wait()和await()/signal()在生产者和消费者线程中合作
 */
class NWProducer implements Runnable {
    private List<Integer> queue;
    private int len;

    public NWProducer(List<Integer> queue, int len) {
        this.queue = queue;
        this.len = len;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                Random random = new Random();
                int temp = random.nextInt(100);
                System.out.println(Thread.currentThread().getId() + "生产了：" + temp);
                synchronized (queue) {
                    if (queue.size() >= len) {
                        queue.notifyAll(); // 只有当 notify/notifyAll() 被执行时候，才会唤醒一个或多个正处于等待状态的线程，
                        // 然后继续往下执行，直到执行完synchronized 代码块的代码或是中途遇到wait() ，
                        // 再次释放锁。
                        queue.wait(); // 当线程执行wait()方法时候，会释放当前的锁，然后让出CPU，进入等待状态。
                    } else {
                        queue.add(temp);
                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class NWConsumer implements Runnable {
    private List<Integer> queue;

    public NWConsumer(List<Integer> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                Integer data = null;
                synchronized (queue) {
                    if (Objects.equals(queue.size(), 0)) {
                        queue.wait();
                        queue.notifyAll();
                    }
                    data = queue.remove(0);
                }
                System.out.println("消费者id:" + Thread.currentThread().getId() + "消费了：" + data);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class ASProducer implements Runnable {
    private List<Integer> queue;
    private int len;

    public ASProducer(List<Integer> queue, int len) {
        this.queue = queue;
        this.len = len;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                Random random = new Random();
                int data = random.nextInt(100);
                One.lock.lock();
                if (queue.size() >= len) {
                    One.empty.signalAll();
                    One.full.await();
                }
                Thread.sleep(1000);
                queue.add(data);
                One.lock.unlock();
                System.out.println("生产者ID:" + Thread.currentThread().getId() + " 生产了:" + data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ASConsumer implements Runnable {
    private List<Integer> queue;

    public ASConsumer(List<Integer> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                Integer data = null;
                One.lock.lock();
                if (Objects.equals(queue.size(), 0)) {
                    One.full.signalAll(); // 唤醒
                    One.empty.await(); // 暂停
                }
                Thread.sleep(1000);
                data = queue.remove(0);
                One.lock.unlock();
                System.out.println("消费者id:" + Thread.currentThread().getId() + " 消费了：" + data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}