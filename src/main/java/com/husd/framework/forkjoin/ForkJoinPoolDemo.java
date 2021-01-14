package com.husd.framework.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class ForkJoinPoolDemo {

    private static final long end = 9000000L;

    static AtomicLong sum = new AtomicLong(0L);

    public static void main(String[] args) {

        t1();
        t2();
        t3();
        t4();
    }

    public static void t1() {

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        long s1 = System.currentTimeMillis();
        ForkJoinTask<Long> result = forkJoinPool.submit(new MyRecursiveTask(0,end));
        try {
            if(result.isCompletedAbnormally()) {
                System.out.println("fork join版本抛出了异常" + result.getException());
            }
            System.out.println("fork join 版本和是:" + result.get());
            long s2 = System.currentTimeMillis();
            System.out.println("fork join 版本耗时：" + (s2 - s1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            forkJoinPool.shutdown();
        }
    }

    public static void t2() {

        long s1 = System.currentTimeMillis();
        long total = 0;
        for(long i=0;i<end;i++) {
            total += i;
        }
        System.out.println("for 版本和是:" + total);
        long s2 = System.currentTimeMillis();
        System.out.println("for 版本耗时：" + (s2 - s1));
    }

    public static void t3() {

        long s1 = System.currentTimeMillis();
        long total = LongStream.rangeClosed(0,end).sum();
        System.out.println("stream 版本和是:" + total);
        long s2 = System.currentTimeMillis();
        System.out.println("stream 版本耗时：" + (s2 - s1));
    }

    public static void t4() {

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.submit(new MyRecursiveAction(0,end,sum));
        long s2 = System.currentTimeMillis();
        //这个会立刻返回，所以需要等待一下，结果才会正确
        try {
            forkJoinPool.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long s3 = System.currentTimeMillis();
        //TODO 这里得结果不正确，要分析下原因
        System.out.println("fork join 版本(RecursiveAction)和是:" + sum.get());
        System.out.println("fork join 版本(RecursiveAction)耗时：" + (s3 - s2));
    }
}
