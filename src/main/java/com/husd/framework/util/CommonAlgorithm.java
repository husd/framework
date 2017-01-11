package com.husd.framework.util;

import org.apache.commons.lang3.ArrayUtils;

public class CommonAlgorithm {

    public static void main(String[] args) {

        int[] row = new int[Integer.MAX_VALUE];
        for (int i = 0; i < row.length; i++) {
            row[i] = i;
        }
        splitAndPrint(row, Integer.MAX_VALUE / 4);
    }

    /**
     * 这个算法是分批次读取大数组中的数据，maxRow是一次读取的最大数据。
     * 
     * @param row
     * @param maxRow
     */
    public static void splitAndPrint(int[] row, int maxRow) {

        if (maxRow <= 0) {
            maxRow = 32;
        }
        int startIndex = 0;
        int endIndex = startIndex + maxRow;
        int rowLength = row.length;
        while (endIndex < rowLength) {
            if (endIndex <= 0) {
                endIndex = rowLength;
            }
            ArrayUtils.subarray(row, startIndex, endIndex);
            startIndex = endIndex;
            endIndex = startIndex + maxRow;
        }
        endIndex = rowLength;
        if (startIndex < rowLength) {
            ArrayUtils.subarray(row, startIndex, endIndex);
        }
    }

}
