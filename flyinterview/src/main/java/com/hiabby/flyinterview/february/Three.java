package com.hiabby.flyinterview.february;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @desc: 遍历一个数组，找出里面最大最小值
 * @author: ITflying
 * @create: 2019-02-26 21:38
 **/
public class Three {
    public static void main(String[] args) {
        List<Integer> keys = Lists.newArrayList(8, 3, 1, 6, 4, 7, 10, 14, 13);

        // 使用lambda表达式排序取出最大值
        Integer max = keys.stream().reduce(Integer::max).get();
        Integer min = keys.stream().reduce(Integer::min).get();

        System.out.println("最大值："+max+"\n最小值:"+min);

        Integer max2 = keys.stream().sorted().collect(Collectors.toList()).get(0);
        Integer min1 = keys.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
        System.out.println("\n最大值："+max+"\n最小值:"+min);
    }
}
