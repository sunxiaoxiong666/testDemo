package suanfa;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2020/6/16 14:10
 */

import java.util.Arrays;

public class MaoPaoSort {
    public static void main(String[] args) {
        int[] arr = {9, 6, 3, 5, 2, 1, 8};
        int[] sortArr = sort(arr);
        System.out.println(Arrays.toString(sortArr));
    }

    private static int[] sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int a = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = a;
                }
            }
        }
        return arr;
    }
}
