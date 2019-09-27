package suanfa;

/*
 * @author: sunxiaoxiong
 */

//直接插入排序算法
public class ZhiJieChaRuPaiXu {
    public static void main(String[] args) {
        int a[] = {6, 5, 7, 9, 3, 2};
        sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    public static void sort(int[] a) {
        int length = a.length;
        //要插入的数据
        int insertNum;
        //第一个元素不用判断，i从1开始
        for (int i = 1; i < length; i++) {
            //9.10.6.5.3
            insertNum = a[i];
            //前面要进行判断的元素个数
            int j = i - 1;
            //从后往前进行判断，大于insertNum的数往后移动一位
            while (j >= 0 && a[j] > insertNum) {
                //大于insertNum的数往后移动一位
                a[j + 1] = a[j];
                j--;
            }
            //循环判断插入元素前面的每一个元素后，找到插入元素的位置。
            a[j + 1] = insertNum;
        }
    }
}
