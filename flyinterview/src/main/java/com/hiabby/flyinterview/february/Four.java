package com.hiabby.flyinterview.february;

import java.util.Objects;

/**
 * @desc: 单例模式
 * @author: ITflying
 * @create: 2019-02-26 21:45
 **/
public class Four {

}

/**
 * 静态变量单例模式:只适合单线程环境
 */
class Singleton1 {
    private static Singleton1 instance = null;

    public Singleton1() {
    }

    public static Singleton1 getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Singleton1();
        }
        return instance;
    }
}

/**
 * 懒汉式单例模式:多线程，加锁会有耗时
 */
class Singleton2 {
    private static Singleton2 instance = null;

    public Singleton2() {
    }

    public static synchronized Singleton2 getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Singleton2();
        }
        return instance;
    }
}

/**
 * 双重加锁单例模式:多线程，加锁会有耗时
 */
class Singleton3 {
    private static Singleton3 instance = null;

    public Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (Singleton3.class) {
                if (Objects.isNull(instance)) {
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }
}

/**
 * 恶汉式单例模式:直接静态初始化一个变量，没有懒加载的效果，降低了内存的使用率
 */
class Singleton4 {
    private static Singleton4 instance = new Singleton4();

    public Singleton4() {
    }

    public static Singleton4 getInstance() {
        return instance;
    }
}

/**
 * 静态内部类单例模式：达到懒加载的方式，推荐使用
 */
class Singleton5 {
    private Singleton5() {
    }

    private static class SingleHandler {
        private final static Singleton5 instance = new Singleton5();
    }

    public static Singleton5 getInstance() {
        return SingleHandler.instance;
    }
}

/**
 * 枚举单例模式：简单有效
 */
class Resourse {

}

enum Singleton6 {
    INSTANCE;

    private Resourse resourse;

    Singleton6() {
        resourse = new Resourse();
    }

    public Resourse getResourse() {
        return resourse;
    }
}