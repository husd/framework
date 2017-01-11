package com.husd.framework;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MemoryLeakTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ThreadPoolExecutor threadPool =
                new ThreadPoolExecutor(10, 10, 1000, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(100));
        for (int i = 0; i < 10; i++) {
            Leak leak = new Leak();
            threadPool.execute(leak);
        }
        while (true) {

        }
    }

    private static class Leak implements Runnable {

        @Override
        public void run() {
            while (true) {
                LargeMemory largeMemory = new LargeMemory();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    private static class LargeMemory {

        private final ThreadLocal<byte[]> b = new ThreadLocal<byte[]>() {
            @Override
            protected byte[] initialValue() {
                return new byte[1024 * 1024 * 1024 * 1024 * 1024];
            }
        };

    }
}
