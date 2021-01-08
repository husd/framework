package com.husd.framework.guava;

import com.google.common.base.Splitter;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.Iterator;

/**
 * 这里是这个类的功能描述
 *
 * @author hushengdong
 */
public class MultisetTest {

    @Test
    public void test() {

        //模拟文章内容
        String[] arr = {"123", "123", "1234", "12345"};

        Multiset<String> multiset = HashMultiset.create();
        for (String s : arr) {
            multiset.add(s);
        }
        System.out.println(multiset.count("123"));
        System.out.println(multiset.size());

        ArrayListMultimap<String, String> arrayListMultimap = ArrayListMultimap.create();
        arrayListMultimap.put("abc", "1");
        arrayListMultimap.put("abc", "12");
        arrayListMultimap.put("abc", "123");
        arrayListMultimap.put("abc", "1234");
        System.out.println(arrayListMultimap.get("abc"));
    }


    @Test
    public void test2() {

        BiMap<String, Integer> nameIdBiMap = HashBiMap.create();
        nameIdBiMap.put("a", 1);
        nameIdBiMap.put("b", 2);
        nameIdBiMap.put("c", 3);
        nameIdBiMap.put("a", 4);
        System.out.println("a的ID:" + nameIdBiMap.get("a"));
        System.out.println("ID为1:" + nameIdBiMap.inverse().get(1));
    }

    @Test
    public void test3() {

        Table<Integer, Integer, Integer> table = HashBasedTable.create();
        table.put(1, 1, 3);
        table.put(1, 2, 4);
        table.put(2, 1, 5);
        table.put(2, 2, 6);

        System.out.println(table.row(1));
        System.out.println(table.column(1));

        System.out.println(table.get(1, 1));
    }

    @Test
    public void test4() {

        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}

        System.out.println(rangeSet.contains(1));
        System.out.println(rangeSet.contains(6));
    }

    @Test
    public void test5() {

        Iterable<String> iterable = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split(",a,,b,");
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
