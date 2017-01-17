package com.husd.framework.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class Md5UtilTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testEncode() {
        String a = "123";
        String actual = Md5Util.encode(a);
        String expected = "202cb962ac59075b964b07152d234b70";
        Assert.assertEquals("测试MD5的加密", expected, actual);
    }

}
