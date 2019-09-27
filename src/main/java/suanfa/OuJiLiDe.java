package suanfa;

/**
 * Created by admin on 2018/5/11.
 */
public class OuJiLiDe {
    public static void main(String[] args) {
        int a = 8;
        int b = 4;
        int result = gcd(b, a);
        System.out.println(result);
    }

    //欧几里德算法--求两个数的最大公约数
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        int c = a % b;
        return gcd(b, c);
    }
}
