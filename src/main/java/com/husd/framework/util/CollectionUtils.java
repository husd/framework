package com.husd.framework.util;

import java.util.Collection;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 * @date 2020/6/2
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {

        return collection == null && collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection collection) {

        return collection != null && collection.size() > 0;
    }
}
