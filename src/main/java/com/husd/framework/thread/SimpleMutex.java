package com.husd.framework.thread;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

//这个类是AQS的一个最简单的独占锁的例子，来自AQS的论文
// 非公平锁，不可重入
public class SimpleMutex {

    class Sync extends AbstractQueuedSynchronizer {

        // 0表示没有锁，1表示持有锁
        public boolean tryAcquire(int ignore) {
            //这就表示不可重入
            return compareAndSetState(0, 1);
        }

        public boolean tryRelease(int ignore) {
            //这就表示不可重入
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(0);
    }

    public void unlock() {
        sync.release(0);
    }
}
