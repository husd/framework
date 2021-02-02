package com.husd.framework.str;

import java.text.DecimalFormat;

/**
 * @author hushengdong
 */
public class StringDemo {

    //from nginx source code
    public static char toLower(char c) {

        return (char) ((c >= 'A' && c <= 'Z') ? (c | 0x20) : c);
    }

    //from nginx source code
    public static char toUpper(char c) {

        return (char) ((c >= 'a' && c <= 'z') ? (c & ~0x20) : c);
    }

    private static final String STR_FORMAT = "0000";

    //格式化补0
    public static String toStringWith0(int num) {
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(num);
    }

}
