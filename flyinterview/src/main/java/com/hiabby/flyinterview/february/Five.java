package com.hiabby.flyinterview.february;

import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc: 二分查找法
 * @author: ITflying
 * @create: 2019-02-26 22:41
 **/
public class Five {
    public static void main(String[] args) {
        List<Integer> keys = Lists.newArrayList(8, 3, 1, 6, 4, 7, 10, 14, 13);
        keys = keys.stream().sorted().collect(Collectors.toList());
        System.out.println(keys);

        System.out.println("要找到10的位置：" + binSearch(keys, 10));
        System.out.println("要找到1的位置：" + binSearch(keys, 1));
        System.out.println("要找到14的位置：" + binSearch(keys, 14));
    }

    /**
     * 普通循环实现
     *
     * @param keys
     * @param target
     */
    public static int binSearch(List<Integer> keys, int target) {
        if (CollectionUtils.isEmpty(keys)) {
            System.out.println("数据为空");
            return -1;
        }

        int left = 0;
        int right = keys.size() - 1;

        while (left <= right) {
            int mid = (right + left) / 2;
            int compare = keys.get(mid);
            if (target == compare) {
                return mid + 1;
            } else if (target > compare) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }

        }
        return -1;
    }
}