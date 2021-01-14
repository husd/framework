package com.husd.framework.forkjoin;

import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 计算1-100000000000000的和
 */
public class MyRecursiveTask extends RecursiveTask {

    private long start;
    private long end;

    public MyRecursiveTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if(end - start < 1000) {
            //如果觉得任务不能再细分了，就执行计算逻辑
            // 这里的计算逻辑是计算sum(start,end)
            // 包含结束的节点
            return LongStream.rangeClosed(start,end).sum();
        } else {
            //如果觉得任务还是太大，可以拆分成子任务处理。
            long mid = start + (end - start) / 2;
            MyRecursiveTask left = new MyRecursiveTask(start,mid);
            MyRecursiveTask right = new MyRecursiveTask(mid + 1,end);
            left.fork();
            right.fork();
            return (Long) left.join() +(Long) right.join();
        }
    }
}
