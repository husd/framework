//package com.husd.framework.thread;
//
//import jdk.internal.misc.Unsafe;
//import org.junit.Test;
//
//public class UnsafeTest {
//
//    private static final Unsafe U = Unsafe.getUnsafe();
//    private volatile long VALUE;
//
//    @Test
//    public void testUnsafe() {
//        int a = 10;
//        int next = a + 1;
//        boolean unsafe = U.compareAndSetInt(this,VALUE,a,next);
//        if(unsafe) {
//            System.out.println("CAS成功， a is :" + a);
//        } else {
//            System.out.println("CAS失败， a is :" + a);
//        }
//    }
//
//}
