package com.hiabby.flyleetcode.problems;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

/**
 * @desc
 * @date 2018/10/23
 **/
public class OneToSixteen {
    public static void main(String[] args) {
        String s = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";

        System.out.println(longestPalindrome(s));
    }

    /**
     * 1、两数之和
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     * 示例:
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     */
    public static int[] twoSum(int[] nums, int target) {
        int a, b;
        for (int i = 0; i < nums.length - 1; i++) {
            a = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                b = nums[j];
                if (a + b == target) {
                    return new int[]{i, j};
                }
            }

        }
        return null;
    }

    public static int[] twoSum_better(int[] nums, int target) {
        HashMap<Integer, Integer> num2pos = new HashMap<>();
        int complete = 0;
        for (int i = 0; i < nums.length; i++) {
            complete = target - nums[i];
            if (num2pos.containsKey(complete) && num2pos.get(complete) != i) {
                return new int[]{i, num2pos.get(complete)};
            }
            num2pos.put(nums[i], i);
        }
        return null;
    }

    /**
     * 2、两数相加
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * 示例：
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        StringBuilder num1 = new StringBuilder();
        StringBuilder num2 = new StringBuilder();

        ListNode current1 = l1;
        ListNode current2 = l2;
        while (current1 != null || current2 != null) {
            if (current1 != null) {
                num1.append(current1.val);
                current1 = current1.next;
            }

            if (current2 != null) {
                num2.append(current2.val);
                current2 = current2.next;
            }
        }

        String sumStr = getBigNumSum(num1.reverse().toString(), num2.reverse().toString());

        ListNode header = null;
        ListNode nowNode = null;
        for (int i = sumStr.length(); i > 0; i--) {
            ListNode newNode = new ListNode(Integer.parseInt(sumStr.substring(i - 1, i)));
            if (header == null) {
                header = newNode;
                nowNode = header;
            } else {
                nowNode.next = newNode;
                nowNode = newNode;
            }
        }
        return header;
    }

    public static String getBigNumSum(String num1, String num2) {
        BigDecimal decimal1 = new BigDecimal(num1);
        BigDecimal decimal2 = new BigDecimal(num2);
        BigDecimal sum = decimal1.add(decimal2);
        return sum.toString();
    }

    public static ListNode addTwoNumbers_better(ListNode l1, ListNode l2) {
        ListNode sumNode = new ListNode(0);
        ListNode pSumNode = sumNode;
        ListNode p1 = l1;
        ListNode p2 = l2;
        int sum = 0;
        int add = 0;
        while (p1 != null || p2 != null) {
            sum = 0;
            if (p1 != null) {
                sum += p1.val;
                p1 = p1.next;
            }
            if (p2 != null) {
                sum += p2.val;
                p2 = p2.next;
            }
            sum += add;
            add = sum / 10;
            pSumNode.val = sum % 10;
            if (p1 != null || p2 != null || (add > 0)) {
                ListNode newNode = new ListNode(add);
                pSumNode.next = newNode;
                pSumNode = newNode;
            }
        }
        return sumNode;
    }

    /**
     * 3、无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() < 2) {
            return s.length();
        }
        int max = 0;
        char[] chars = s.toCharArray();
        Set<Character> tSet = null;
        for (int i = 0; i < chars.length - 1; i++) {
            tSet = new HashSet<>();
            tSet.add(chars[i]);
            for (int j = i + 1; j < chars.length; j++) {
                if (!tSet.add(chars[j])) {
                    if (max == 0) {
                        max = 1;
                    }
                    break;
                } else {
                    int tempMax = j - i + 1;
                    if (tempMax > max) {
                        max = tempMax;
                    }
                }
            }
        }
        return max;
    }

    public static int lengthOfLongestSubstring_better(String s) {
        Set<Character> tSet = new HashSet<>();
        int max = 0, i = 0, j = 0;
        while (i < s.length() && j < s.length()) {
            if (!tSet.contains(s.charAt(j))) {
                tSet.add(s.charAt(j++));
                max = Math.max(j - i, max);
            } else {
                tSet.remove(s.charAt(i++));
            }
        }
        return max;
    }

    /**
     * 4. 寻找两个有序数组的中位数
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
     * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
     * 你可以假设 nums1 和 nums2 不会同时为空。
     * 示例 1:
     * nums1 = [1, 3]
     * nums2 = [2]
     * 则中位数是 2.0
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        int[] mergeNums = new int[len];
        int i = 0, j = 0;
        int pos = 0;
        while (i < nums1.length || j < nums2.length) {
            if (i < nums1.length && j >= nums2.length) {
                mergeNums[pos++] = nums1[i];
                i++;
                continue;
            } else if (i >= nums1.length && j < nums2.length) {
                mergeNums[pos++] = nums2[j];
                j++;
                continue;
            }

            int num1 = nums1[i];
            int num2 = nums2[j];
            if (num1 == num2) {
                mergeNums[pos++] = num1;
                mergeNums[pos++] = num1;
                i++;
                j++;
            } else if (num1 < num2) {
                mergeNums[pos++] = num1;
                i++;
            } else {
                mergeNums[pos++] = num2;
                j++;
            }
        }

        int target = len % 2;
        if (target != 0) {
            return mergeNums[len / 2];
        } else {
            int middle = mergeNums[len / 2 - 1];
            int middleAfter = mergeNums[(len / 2)];
            return (middle + middleAfter) / 2.0;
        }
    }

    public static double findMedianSortedArrays_simple(int[] nums1, int[] nums2) {
        int[] sum = new int[nums1.length + nums2.length];
        System.arraycopy(nums1, 0, sum, 0, nums1.length);
        System.arraycopy(nums2, 0, sum, nums1.length, nums2.length);
        Arrays.sort(sum);
        int len = sum.length;
        if (len % 2 != 0) {
            return sum[len / 2];
        } else {
            return (sum[len / 2] + sum[len / 2 - 1]) / 2.0;
        }
    }

    /**
     * 5. 最长回文子串
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * 示例 1：
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     */
    /**
     * 该方法暴力搜索所有数组，然后找出对应的最长回文子串，超时了
     */
    public static String longestPalindrome(String s) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String temp = s.substring(i, j);
                stringList.add(temp);
            }
        }
        int max = 0;
        String maxStr = "";
        for (int i = 0; i < stringList.size(); i++) {
            String s1 = stringList.get(i);
            StringBuilder builder = new StringBuilder(s1);
            if (s1.compareTo(builder.reverse().toString()) == 0) {
                if (s1.length() > max) {
                    max = s1.length();
                    maxStr = s1;
                }
            }
        }
        return maxStr;
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}