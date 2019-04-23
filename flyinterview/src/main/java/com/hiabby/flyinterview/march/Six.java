package com.hiabby.flyinterview.march;

/**
 * @desc: 睡眠排序(根据时钟来进行排序，不够准确)
 * @author: ITflying
 * @create: 2019-03-14 09:33
 **/
public class Six {
    public static void main(String[] args) {
        int[] nums = {9, 7, 2, 6, 15, 8, 9, 9, 9, 9, 9};
        sleepSort(nums);
        for (int n : nums) {
            System.out.printf("%d    ", n);
        }
    }

    public static void sleepSort(int[] nums) {
        SleepSort.idx = 0;
        SleepSort.output = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            new SleepSort(nums[i]).start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < SleepSort.output.length; i++) {
            nums[i] = SleepSort.output[i];
        }
    }

}

class SleepSort extends Thread {
    public static int[] output;
    public static int idx;

    private int sleepTime;

    public SleepSort() {
        this.sleepTime = 0;
    }

    public SleepSort(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        output[idx++]=this.sleepTime;
    }
}
