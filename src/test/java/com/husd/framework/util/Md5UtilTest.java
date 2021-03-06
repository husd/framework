package com.husd.framework.util;

import junit.framework.Assert;
import org.junit.Test;

public class Md5UtilTest {

    @Test
    public void testEncode() {
        String a = "123";
        String actual = Md5Util.encode(a);
        String expected = "202cb962ac59075b964b07152d234b70";
        Assert.assertEquals("测试MD5的加密", expected, actual);
    }

}
