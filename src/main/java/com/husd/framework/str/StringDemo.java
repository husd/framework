package com.husd.framework.str;

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

}
