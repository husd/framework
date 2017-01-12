package com.husd.framework.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class NumberUtil {

    final static String regx = "[\\+\\-]?[\\d]+([\\.][\\d]*)?([Ee][+-]?[\\d]+)?";// 科学计数法正则表达式
    final static Pattern pattern = Pattern.compile(regx);

    /**
     * 判断这个数字是不是科学计数法表示的数字。
     * 
     * @param num
     * @return
     */
    public static boolean isENum(String num) {
        if (StringUtils.isBlank(num)) {
            return false;
        }
        return pattern.matcher(num).matches();
    }
}
