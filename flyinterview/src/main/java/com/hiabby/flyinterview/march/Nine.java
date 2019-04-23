package com.hiabby.flyinterview.march;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @desc: 各种排序算法展示：冒泡排序，选择排序，插入排序，快速排序，其它比如堆排序、希尔排序、归并排序等等在其它之后遇到了再学
 * @author: ITflying
 * @create: 2019-03-14 14:24
 **/
public class Nine {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(9, 7, 2, 6, 15, 8, 9, 9, 9, 9, 9);

        System.out.println("原来的列表数据：" + list.toString());

        System.out.println("冒泡排序之后的数据：" + SortAI.bubbleSort(list).toString());

        System.out.println("选择排序之后的数据：" + SortAI.selectSort(list).toString());

        System.out.println("插入排序之后的数据：" + SortAI.insertSort(list).toString());

        System.out.println("快速排序之后的数据：" + SortAI.quickSort(list).toString());
    }


}

class SortAI {
    /**
     * 冒泡排序：就是前后循环交换位置就可以了
     */
    static List<Integer> bubbleSort(List<Integer> list) {
        List<Integer> sortedList = Lists.newArrayList(list);
        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = i + 1; j < sortedList.size(); j++) {
                if (sortedList.get(i) > sortedList.get(j)) {
                    switchList(sortedList, i, j);
                }
            }
        }

        return sortedList;
    }

    /**
     * 选择排序：就是不断从后面的数值里面选择最小的依次放在前面循环的位置
     */
    static List<Integer> selectSort(List<Integer> list) {
        List<Integer> sortedList = Lists.newArrayList(list);

        for (int i = 0; i < sortedList.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < sortedList.size(); j++) {
                if (sortedList.get(j) < sortedList.get(min)) {
                    min = j;
                }
            }
            if (min != i) {
                switchList(sortedList, i, min);
            }
        }

        return sortedList;
    }

    /**
     * 插入排序：就是循环往后遍历，然后判断是否需要跟前面的数据插入位置，如果需要后面的数据要往后推迟
     */
    static List<Integer> insertSort(List<Integer> unSortedList) {
        List<Integer> list = Lists.newArrayList(unSortedList);
        int i, j, temp;

        for (i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) {
                temp = list.get(i);
                for (j = i; j > 0 && list.get(j - 1) > temp; j--) {
                    list.set(j, list.get(j - 1));
                }
                list.set(j, temp);
            }
        }

        return list;
    }

    /**
     * 快速排序（快排）：随机选择一个数字，然后小于该数字的放在左边，大的放右边，对左右重复该过程，这就是哨兵的过程
     */
    static List<Integer> quickSort(List<Integer> sortedList) {
        List<Integer> list = Lists.newArrayList(sortedList);

        quickSort(list, 0, list.size() - 1);
        return list;
    }

    private static void quickSort(List<Integer> list, int left, int right) {
        int i, j, temp;

        if (left > right) {
            return;
        }

        temp = list.get(left);
        i = left;
        j = right;

        while (i != j) {
            while (list.get(j) >= temp && i < j) {
                j--;
            }
            while (list.get(i) <= temp && i < j) {
                i++;
            }
            if (i < j) {
                switchList(list, i, j);
            }
        }

        list.set(left, list.get(i));
        list.set(i, temp);

        quickSort(list, left, i - 1);
        quickSort(list, i + 1, right);
    }

    private static void switchList(List<Integer> list, int i, int j) {
        Integer num = list.get(i);
        list.set(i, list.get(j));
        list.set(j, num);
    }
}
