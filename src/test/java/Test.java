

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
    }
}
