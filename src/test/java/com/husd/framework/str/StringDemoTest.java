package com.husd.framework.str;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author hushengdong
 */
public class StringDemoTest {

    @Test
    public void test() {

        String str = StringDemo.toStringWith0(9);
        Assert.assertEquals("0009", str);
    }
}
