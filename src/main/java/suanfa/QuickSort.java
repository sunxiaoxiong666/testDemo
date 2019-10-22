package suanfa;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2019/10/21 10:05
 */

/*
 * 快速排序
 * 找一个基准数，把基准数大的放在基准数右边，把基准数小的放在基准数左边
 * */
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {2, 1, 45, 66, 23, 88, 22, 11, 55, -2, 9};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后：");
        for (int i : arr) {
            System.out.println(i);
        }
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            //找基准数据的正确索引
            int index = getIndex(arr, low, high);

            //进行迭代，对index之前和之后的数组进行相同的操作使整个数组变成有序的
            quickSort(arr, 0, index - 1);
            quickSort(arr, index + 1, high);
        }
    }

    private static int getIndex(int[] arr, int low, int high) {

        // 基准数据
        int temp = arr[low];
        while (low < high) {
            //当队尾的数据大于等于基准数据，向前挪动high指针
            while (low < high && arr[high] >= temp) {
                high--;
            }
            //如果队尾数据小于temp，就将high位置的数据赋给low位置
            arr[low] = arr[high];
            //当队首的数据小于等于基准数据，向前挪动low指针
            while (low < high && arr[low] <= temp) {
                low++;
            }
            //如果队首的数据大于temp时，将low位置的数据赋给high位置
            arr[high] = arr[low];
        }
        //跳出循环时low和high相等，此时low或者high就是temp的正确索引
        //low位置的值并不是temp，将temp赋值给arr[low]
        arr[low] = temp;
        //返回temp的正确位置
        return low;
    }

}
