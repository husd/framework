package com.husd.framework.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class CommonAlgorithm {

    public static void main(String[] args) {
        int[] inventory = {1033121857, 0, 20876, 135, 1132, 34, 85, 104, 0, 0, 0, 35, 53, 333, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 233, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] weight = {0.143631, 0.0, 0.0, 0.10097, 0.02957, 0.0, 0.012223, 0.024394, 0.01453, 0.043149, 0.015115,
                0.032435, 0.023933, 0.028092, 0.015179, 0.01335, 0.029809, 0.022694, 0.02071, 0.019617, 0.021569, 0.003796,
                0.006528, 0.015466, 0.006574, 0.01848, 0.01206, 0.008026, 0.011676, 0.0, 0.001593, 0.004387, 0.010052,
                0.002667, 0.002726, 0.002546, 0.001264, 0.0, 0.008041, 0.0, 0.02442, 0.012389, 0.001877, 0.006413,
                0.008854, 0.009709, 0.022253, 0.008818, 0.003281, 0.01106};
        int target = 3000;
        int result[] = advice(inventory, weight, target);
        int[] real = {0, 0, 0, 405, 0, 0, 0, 26, 78, 231, 82, 139, 75, 0, 82, 72, 159, 121, 110, 104, 115, 21, 35, 83, 35, 98, 65, 44, 64, 0, 8, 24, 54, 15, 14, 14, 7, 0, 44, 0, 130, 66, 10, 34, 47, 52, 119, 47, 18, 53};
        System.out.println(Arrays.toString(result));
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

    public static int[] advice(int[] inventoryArr, double[] weight, int target) {
        int[] result = new int[inventoryArr.length];
        if (calcDoubleArraySum(weight) == 0) {
            for (int i = 0; i < weight.length; i++) {
                weight[i] = 1;
            }
        }
        //0的权重不补货
        for (int i = 0; i < inventoryArr.length; i++) {
            if (weight[i] <= 0) {
                result[i] = 0;
            }
        }
        //权重提升为现货
        double radio = findRadioAndChangeWeight(inventoryArr, weight);
        for (int i = 0; i < inventoryArr.length; i++) {

        }

        return result;
    }

    private static double findRadioAndChangeWeight(int[] inventoryArr, double[] weight) {
        for (int i = 0; i < weight.length; i++) {
            if (weight[i] != 0 && inventoryArr[i] != 0) {

            }
        }
        return 0;
    }


    public static int[] advice1(int[] inventoryArr, double[] weight, int target) {

        int totalCurrentInventory = calcIntArraySum(inventoryArr);
        double totalWeight = calcDoubleArraySum(weight);
        int totalTargetInventory = totalCurrentInventory + target;
        int[] result = new int[inventoryArr.length];
        double correctTotalWeight = totalWeight;
        boolean hasNegative = false;
        for (int i = 0; i < inventoryArr.length; i++) {
            double radio = weight[i] / totalWeight;
            int currentInventory = inventoryArr[i];
            result[i] = (int) (totalTargetInventory * radio) - currentInventory;
            //如果有负值,就记录并且把该仓的补货量设置为0
            if (result[i] < 0) {
                correctTotalWeight = correctTotalWeight - weight[i];
                result[i] = 0;
                hasNegative = true;
            }
        }
        //没有负值就直接返回结果
        if (!hasNegative) {
            return result;
        }
        int totalNegativeInventory = calcIntArraySum(result) - target;
        System.out.println("中间值:" + Arrays.toString(result) + " negative value is :" + totalNegativeInventory);
        for (int i = 0; i < inventoryArr.length; i++) {
            if (result[i] == 0) {
                continue;
            }
            double radio = weight[i] / correctTotalWeight;
            int current = result[i];
            int b = (int) (totalNegativeInventory * radio);
            System.out.println("current is :" + current + " b is :" + b);
            result[i] = current - b;
        }
        return result;
    }

    private static int calcIntArraySum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int total = 0;
        for (int element : arr) {
            total += element;
        }
        return total;
    }

    private static double calcDoubleArraySum(double[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        double total = 0;
        for (double element : arr) {
            total += element;
        }
        return total;
    }


}
