/**
 * 
 */
package com.husd.framework.util;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author hushengdong
 *
 */
public class NumberUtilTest extends TestCase {

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for {@link com.husd.framework.util.NumberUtil#isENum(java.lang.String)}.
     */
    public void testIsENum() {
        boolean actual = NumberUtil.isENum("1.44");
        boolean expected = true;
        Assert.assertEquals("测试小数等科学技术法", expected, actual);

        actual = NumberUtil.isENum("+1.44");
        expected = true;
        Assert.assertEquals("测试加号", expected, actual);

        actual = NumberUtil.isENum("-1.44");
        expected = true;
        Assert.assertEquals("测试负数", expected, actual);

        actual = NumberUtil.isENum("--1.44");
        expected = false;
        Assert.assertEquals("测试不合法数字", expected, actual);
    }

    /**
     * Test method for {@link com.husd.framework.util.NumberUtil#calcPercent(double, double)}.
     */
    public void testCalcPercent() {
        String actual = NumberUtil.calcPercent(1, 2);
        String expected = "50.00%";
        Assert.assertEquals("测试整数相除", expected, actual);

        actual = NumberUtil.calcPercent(1, 0);
        expected = "0.00%";
        Assert.assertEquals("测试分母为0", expected, actual);

        actual = NumberUtil.calcPercent(0, 0);
        expected = "0.00%";
        Assert.assertEquals("测试分子分母为0", expected, actual);

        actual = NumberUtil.calcPercent(0, 10);
        expected = "0.00%";
        Assert.assertEquals("测试分子为0", expected, actual);
    }

}
