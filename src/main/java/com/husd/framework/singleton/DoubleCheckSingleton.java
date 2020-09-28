package com.husd.framework.singleton;

/**
 * 双重校验加锁的实现方法
 *
 * @author hushengdong
 */
public class DoubleCheckSingleton {

    private static DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
        System.out.println("Singleton has loaded");
    }

    //这个时候如果把synchronized直接加到方法上去，也可以实现效果
    //但是问题就是只有初始化的时候才需要1次锁，但是如果加到方法上，
    //之后的每次调用都需要锁，效率会很低
    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
