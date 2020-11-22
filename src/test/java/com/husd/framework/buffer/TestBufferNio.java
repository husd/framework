package com.husd.framework.buffer;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class TestBufferNio {

    @Test
    public void test1() throws InterruptedException {

        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        long tid = Thread.currentThread().getId();
        String s = "t1 is running pid is:" + pid + " tid is:" + tid;
        System.out.println(s);
        Thread.sleep(1000 * 10);
        while (true) {
            Thread.sleep(1000 * 2);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 3);
        }
    }
}
