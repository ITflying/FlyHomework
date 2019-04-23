package com.hiabby.flyinterview.march;

import java.util.Scanner;

/**
 * @desc: 输出一个整数, 表示得到的最小价值
 * 有一种有趣的字符串价值计算方式:统计字符串中每种字符出现的次数,然后求所有字符次数的平方和作为字符串的价值
 * 例如: 字符串"abacaba",里面包括4个'a',2个'b',1个'c',于是这个字符串的价值为4 * 4 + 2 * 2 + 1 * 1 = 21
 * 牛牛有一个字符串s,并且允许你从s中移除最多k个字符,你的目标是让得到的字符串的价值最小。
 * 思路：就是计算字符串当中所有的字符数量，然后不断从数量最多的字符中扣减
 * @author: ITflying
 * @create: 2019-03-14 15:40
 **/
public class Ten {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一串字符串：");
        String input = scanner.nextLine();
        System.out.println("请输入可移除字符串数量：");
        int num = scanner.nextInt();

        input = input.toLowerCase();
        int[] words = new int[26];
        for (int i = 0; i < input.length(); i++) {
            int charNum = input.charAt(i) - 97;
            words[charNum]++;
        }
        for (int i = 0; i < num; i++) {
            int pos = findMaxPos(words);
            words[pos]--;
        }

        long value = 0;
        for (int i = 0; i < words.length; i++) {
            if (words[i] > 0) {
                value += words[i] * words[i];
            }
        }
        System.out.println("总价值为：" + value);
    }

    private static int findMaxPos(int[] words) {
        int max = words[0], pos = 0;
        for (int i = 1; i < words.length; i++) {
            if (words[i] > max) {
                max = words[i];
                pos = i;
            }
        }
        return pos;
    }
}
