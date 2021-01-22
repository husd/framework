package com.husd.framework.jvm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class ObejctInMem {

    public static void main(String[] args) {

        Object obj = new Object();

        //查看对象内部信息
        System.out.println("查看对象内部信息");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        synchronized (obj) {
            System.out.println(" ------  查看对象内部信息 ------ 加锁之后");
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }

    }
}
