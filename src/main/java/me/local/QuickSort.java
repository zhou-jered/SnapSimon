package me.local;

import java.util.Arrays;

public class QuickSort {

    void halfSort(int[] nums, int left, int right) {

        if (right - left < 1) {
            return;
        }
        int splitNum = nums[left];

        int leftIdx = left;
        int rigthIdx = right;
        while (leftIdx < rigthIdx) {

            while (leftIdx < rigthIdx && nums[rigthIdx] >= splitNum) {
                rigthIdx--;
            }
            if (leftIdx < rigthIdx) {
                nums[leftIdx] = nums[rigthIdx];
            }
            while (leftIdx < rigthIdx && nums[leftIdx] < splitNum) {
                leftIdx++;
            }
            if (leftIdx < rigthIdx) {
                nums[rigthIdx] = nums[leftIdx];
            }
        }
        nums[leftIdx] = splitNum;
        halfSort(nums, left, leftIdx - 1);
        halfSort(nums, leftIdx + 1, right);
    }

    void quickSort(int[] nums) {
        halfSort(nums, 0, nums.length - 1);
    }


    public static void main(String[] args) {
        int capacity = 10;
        int[] nums = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            nums[i] = i;
        }
        for (int i = 0; i < capacity * 10; i++) {
            int left = (int) (Math.random() * capacity * 100) % capacity;
            int right = (int) (Math.random() * capacity * 100) % capacity;
            if (left == right) {
                continue;
            }
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
        }

        System.out.println("before:");
        System.out.println(Arrays.toString(nums));
        QuickSort qs = new QuickSort();
        qs.quickSort(nums);
        System.out.println("after:");
        System.out.println(Arrays.toString(nums));
    }

}
