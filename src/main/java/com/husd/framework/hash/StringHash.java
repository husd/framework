package com.husd.framework.hash;

/**
 * 我们假设要匹配的字符串的字符集中只包含 K 个字符，
 * 我们可以用一个 K 进制数来表示一个子串，
 * 这个 K 进制数转化成十进制数，作为子串的哈希值。
 * 表述起来有点抽象，我举了一个例子，看完你应该就能懂了。
 * <p>
 * 比如要处理的字符串只包含 a～z 这 26 个小写字母，
 * 那我们就用二十六进制来表示一个字符串。
 * 我们把 a～z 这 26 个字符映射到 0～25
 * 这 26 个数字，a 就表示 0，b 就表示 1，以此类推，z 表示 25。
 * <p>
 * 这个hash算法，可以用在字符串匹配-RK 算法中
 *
 * @author hushengdong
 */
public class StringHash {

    //要处理的字符串只包含 a～z 这 26 个小写字母，
    // 那我们就用二十六进制来表示一个字符串。
    // 我们把 a～z 这 26 个字符映射到 0～25 这 26 个数字，
    // a 就表示 0，b 就表示 1，以此类推，z 表示 25。
    public long hash(char[] arr) {

        long sum = 0L;
        int n = arr.length;
        int b = 0; // b表示位
        for (int i = n - 1; i >= 0; i--) {
            //优化的方法，提前把26的次方，存储在数组中，用到的时候直接拿就行了
            long t = (arr[i] - 'a') * pow(26, (b++));
            sum = sum + t;
        }
        return sum;
    }

    private long pow(int a, int b) {

        long sum = 1L;
        for (int i = 0; i < b; i++) {
            sum = sum * a;
        }
        return sum;
    }

    public static void main(String[] args) {

        StringHash stringHash = new StringHash();
        System.out.println(stringHash.hash("cba".toCharArray()));
    }
}
