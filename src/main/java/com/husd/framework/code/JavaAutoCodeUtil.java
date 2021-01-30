package com.husd.framework.code;

import com.google.common.base.CaseFormat;

/**
 * @author hushengdong
 */
public class JavaAutoCodeUtil {

    public static String firstCharUpper(String str) {

        char[] arr = str.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return String.valueOf(arr);
    }


    public static String firstCharLower(String str) {

        char[] arr = str.toCharArray();
        arr[0] = Character.toLowerCase(arr[0]);
        return String.valueOf(arr);
    }

    public static String camel(String str) {

        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }

}
