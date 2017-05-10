package com.husd.framework.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MoneyUtilTest extends TestCase {


    public void testTransfer2RMB() {
        double num = 3;
        String actual = MoneyUtil.transferDouble2RMB(num);
        String expected = "￥3.00";
        Assert.assertEquals("测试正常", expected, actual);

        num = 3.12;
        actual = MoneyUtil.transferDouble2RMB(num);
        expected = "￥3.12";
        Assert.assertEquals("测试正常", expected, actual);

        num = 3;
        actual = MoneyUtil.transferDouble2RMB(num);
        expected = "￥3.00";
        Assert.assertEquals("测试整数", expected, actual);

        num = -3.12;
        actual = MoneyUtil.transferDouble2RMB(num);
        expected = "-￥3.12";
        Assert.assertEquals("测试负数", expected, actual);

        num = -0;
        actual = MoneyUtil.transferDouble2RMB(num);
        expected = "￥0.00";
        Assert.assertEquals("测试-0", expected, actual);
    }
}
