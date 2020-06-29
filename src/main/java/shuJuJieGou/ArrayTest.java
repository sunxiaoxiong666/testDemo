package shuJuJieGou;

/*
 * @author: sunxiaoxiong
 * @date  : Created in 2020/6/11 14:18
 */

//数组
public class ArrayTest {
    public static void main(String[] args) {
        int[] oldArray = {1, 2, 3};
        int[] newArray = new int[20];
        System.out.println(oldArray.length);
        /*for (int i = 0; i < oldArray.length; i++) {
            newArray[i] = oldArray[1];
        }*/
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        oldArray = newArray;
        System.out.println(oldArray.length);
        System.out.println(oldArray[4]);
    }
}
