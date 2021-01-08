package com.husd.framework.thread;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class SimpleDateFormatTest {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //测试下SimpleDateFormat的多线程并发问题
    @Test
    public void test() throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long time = format.parse("2020-10-12 00:00:00").getTime();
                        System.out.println("time is :" + time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            executor.execute(t);
        }
        Thread.sleep(10000);
    }
}
