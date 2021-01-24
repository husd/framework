package com.husd.framework.jvm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class JolDemo {

    public static void main(String[] args) {

        Object obj = new Object();

        //查看对象内部信息
        System.out.println("查看对象内部信息");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        //查看对象外部信息：包括引用的对象
        System.out.println("查看对象外部信息：包括引用的对象");
        System.out.println(GraphLayout.parseInstance(obj).toPrintable());

        //查看对象占用空间总大小
        System.out.println("查看对象占用空间总大小");
        System.out.println(GraphLayout.parseInstance(obj).totalSize());
    }
}
