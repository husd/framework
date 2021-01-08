package com.husd.framework.guava;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class CollectionUtil {

    public void set() {

        final ImmutableSet<Integer> set = ImmutableSet.<Integer>builder()
                .add(12)
                .add(30)
                .add(12)
                .build();
        ImmutableSortedSet<Integer> set1 = ImmutableSortedSet.of(1, 2, 3, 4, 5);
    }
}
