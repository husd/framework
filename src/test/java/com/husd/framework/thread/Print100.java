package com.husd.framework.thread;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */

//轮流打印奇数偶数
public class Print100 {

    private static Object obj = new Object();
    private static volatile int i = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (obj) {
                    while (i <= 10) {
                        try {
                            if (i % 2 == 1) {
                                System.out.println(Thread.currentThread().getName() + " t1此时不满足条件，尝试拿锁 " + i);
                                obj.wait();
                            } else {
                                i++;
                                System.out.println(Thread.currentThread().getName() + " t1此时满足条件尝试拿锁 " + i);
                                obj.notify();
                            }
                            Thread.sleep(1000 * 3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                synchronized (obj) {
                    while (i <= 10) {
                        try {
                            if (i % 2 == 1) {
                                System.out.println(Thread.currentThread().getName() + " t1此时不满足条件，尝试拿锁 " + i);
                                obj.wait();
                            } else {
                                i++;
                                System.out.println(Thread.currentThread().getName() + " t1此时满足条件尝试拿锁 " + i);
                                obj.notify();
                            }
                            Thread.sleep(1000 * 3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        t2.start();
        Thread.sleep(1000 * 3);
        t1.start();
    }

    @Test
    public void test() {

        final Map<Integer, String> map = new HashMap<>();
        //map = new HashMap<>();
        map.getOrDefault(123, "默认值");
        map.putIfAbsent(123, "123");

    }
}
