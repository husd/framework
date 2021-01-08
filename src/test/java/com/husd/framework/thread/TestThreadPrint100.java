package com.husd.framework.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class TestThreadPrint100 {

    private static int i = 0;
    //    private static volatile int i = 0;
    static List<Integer> arrList = new ArrayList<>(100);

    public static void main(String[] args) throws InterruptedException {

        new Thread() {

            @Override
            public void run() {
                super.run();
                while (i <= 100) {
                    if (i % 2 == 0) {
                        arrList.add(i);
                        i++;
                    }
                }
            }

        }.start();

        Thread t2 = new Thread(() -> {
            while (i <= 99) {
                if (i % 2 == 1) {
                    arrList.add(i);
                    i++;
                }
            }
        });

        //t1.start();
        t2.start();
        //t1.join();
        t2.join();
        for (int a : arrList) {
            System.out.println(a);
        }
    }
}