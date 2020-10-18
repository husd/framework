package com.husd.framework.outofmemory;

import java.util.LinkedList;
import java.util.List;

/**
 * jdk1.7以及之前： -XX:PermSize10m -XX:MaxPermSize10m
 * jdk1.8以及之后： -XX:MetaspaceSize=1m -XX:MaxMetaspaceSize=2m -XX:+PrintGCDetails
 * <p>
 * 静态常量池溢出
 *
 * @author hushengdong
 */
public class ConstantOOM {

    public static void main(String[] args) {

        List<String> arr = new LinkedList<>();
        int a = 0;
        while (true) {
            arr.add(String.valueOf(a++).intern());
        }
    }
}
