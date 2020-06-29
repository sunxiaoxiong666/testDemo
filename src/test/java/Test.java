

/*
 * @author: sunxiaoxiong
 */

import qingxi.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String oneHoursAgoTime = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        String aa = "-17";
        int b = Integer.parseInt(aa);
        rightNow.add(Calendar.HOUR, b);
        Date dt1 = rightNow.getTime();
        oneHoursAgoTime = sdf.format(dt1);
        String s = Utils.formatDate(oneHoursAgoTime, "yyyy-MM-dd");
        System.out.println(s);

        System.out.println((double) 1370 / 400);
        String x = Utils.multiply(2, 3);
        x = Utils.divide(Utils.multiply(x, 2), 4, 5);
        System.out.println(x);


        //异或运算，交换两个变量的值（不采用中间临时变量）
        int y = 3;
        int z = 5;
        y = y ^ z;
        System.out.println("y=" + y + ",z=" + z);
        z = y ^ z;
        System.out.println("y=" + y + ",z=" + z);
        y = y ^ z;
        System.out.println("y=" + y + ",z=" + z);
    }
}
