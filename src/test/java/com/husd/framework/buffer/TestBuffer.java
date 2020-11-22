package com.husd.framework.buffer;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class TestBuffer {

    @Test
    public void test1() throws InterruptedException {

        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        long tid = Thread.currentThread().getId();
        String s = "t1 is running pid is:" + pid + " tid is:" + tid;
        System.out.println(s);
        Thread.sleep(1000 * 10);
        int i = 0;
        while (true) {
            Thread.sleep(1000 * 2);
            if (++i % 2 == 0) {
                byte[] byteArray = new byte[1024 * 1024 * 6];
            } else {
                byte[] byteArray = new byte[1024 * 1024 * 3];
            }
        }
    }

    @Test
    public void test() throws InterruptedException {

        Runtime runtime = Runtime.getRuntime();
        int n = runtime.availableProcessors();
        System.out.println("avalable processors is :" + n);
        while (true) {
            Thread.sleep(300);
            locate();
            printMem();
        }
    }

    private void locate() {

        byte[] arrays = new byte[1024 * 1024];
    }

    private void printMem() {

        Runtime runtime = Runtime.getRuntime();
        System.out.println("total mem:" + runtime.totalMemory() / 1024 / 1024 + " free mem:" + runtime.freeMemory() / 1024 / 1024);
    }
}
