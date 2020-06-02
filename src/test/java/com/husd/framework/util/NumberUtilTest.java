/**
 *
 */
package com.husd.framework.util;

import com.husd.framework.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hushengdong
 *
 */
public class NumberUtilTest {

    /**
     * Test method for {@link com.husd.framework.util.NumberUtil#isENum(java.lang.String)}.
     */
    @Test
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
    @Test
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

    @Test
    public void testNumber() {

        FileUtil fileUtil = new FileUtil();
        List<String> stringList = fileUtil.readFrom("number.lst");
        for (String s : stringList) {
            org.junit.Assert.assertTrue(s, NumberUtil.isNumber(s));
        }
        for (int i = 0; i < 100; i++) {
            String s1 = String.valueOf(TestHelper.anyDouble());
            org.junit.Assert.assertTrue(s1, NumberUtil.isNumber(s1));
        }
    }

    @Test
    public void testNonNumber() {

        FileUtil fileUtil = new FileUtil();
        List<String> stringList = fileUtil.readFrom("non_number.lst");
        for (String s : stringList) {
            org.junit.Assert.assertFalse(s, NumberUtil.isNumber(s));
        }
    }

    @Test
    public void test() {

        NumberUtil.isNumber("1.1");
        BigDecimal bigDecimal = new BigDecimal("2.123345E+3");
        Double d = Double.parseDouble("2.123345E+3");
        System.out.println(d);
        System.out.println(bigDecimal.toString());
        Integer a1 = Integer.parseInt("+1");
        System.out.println(a1);
    }

}
