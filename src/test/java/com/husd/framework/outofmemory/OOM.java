package com.husd.framework.outofmemory;

import java.util.LinkedList;
import java.util.List;

/**
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author hushengdong
 */
public class OOM {

    public static void main(String[] args) {

        List<Object> array = new LinkedList<>();
        while (true) {
            array.add(new Object());
        }
    }
}
