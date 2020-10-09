package com.husd.framework.thread;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal的用法，以及怎么发生内存泄漏的
 *
 * @author hushengdong
 */
public class ThreadLocalTest {

    ThreadLocal<Object> userContext = new ThreadLocal<>();

    @Test
    public void test() throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //通常的用法如下
                try {
                    userContext.set(new Object());
                    Object user = userContext.get();
                    System.out.println("业务逻辑1");
                    System.out.println("业务逻辑2");
                    System.out.println("业务逻辑3");
                } finally {
                    userContext.remove();
                }
            }
        });
        t.start();
        t.join();
        TimeUnit.SECONDS.sleep(2);
    }

}
