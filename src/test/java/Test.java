

/*
 * @author: sunxiaoxiong
 */

import com.google.common.collect.Lists;
import qingxi.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


        // differentDays
        String time1 = "2020-03-03 05:57:13";
        String time2 = "2020-06-03 05:57:12";
        Integer days = Utils.differentDays(time2, "yyyy-MM-dd HH:mm:ss", time1, "yyyy-MM-dd HH:mm:ss");
        System.out.println(days);


        String postfix = "";
        boolean equals = postfix.equals(null);
        System.out.println(equals);

        LocalDate localDate1 = LocalDate.of(2020, 4, 1);
        // localDate1.getYear()
        int day = localDate1.getDayOfMonth();
        LocalDate yesterday = localDate1.plusDays(-1);
        int dayOfMonth = yesterday.getDayOfMonth();
        if (dayOfMonth == 31) {
            postfix = "even";
        } else if (day % 2 == 0) {
            postfix = "even";
        } else {
            postfix = "odd";
        }
        System.out.println(postfix);



        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start_time = LocalDateTime.parse("2020-09-06 00:10:59", df);
        String s1 = start_time.toString();
        System.out.println(s1);
        LocalDateTime end_time = LocalDateTime.parse("2020-09-06 00:20:00", df);
        if (start_time.toInstant(ZoneOffset.of("+8")).toEpochMilli() >=
                end_time.toInstant(ZoneOffset.of("+8")).toEpochMilli()) {
            System.out.println("大于");
        } else {
            System.out.println("小于");
        }

        forTest();

    }

    public static void forTest() {
        List list = new ArrayList();
        List list2 = Lists.newArrayList(1, 2, 3);
        list2.forEach(data -> {
            if (data.equals(1)) {
                list.add(data);
                return;
            } else if (data.equals(2)) {
                list.add(data);
            } else {
                list.add(data);
            }
            System.out.println("22");
        });
        list.forEach(data -> {
            System.out.println(data);
        });
    }

    public static void forTest2() {
        List list = new ArrayList();
        List list2 = Lists.newArrayList(1, 2, 3);

        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).equals(1)) {
                list.add(list2.get(i));
                // break;
            } else if (list2.get(i).equals(2)) {
                list.add(list2.get(i));
                return;
                // continue;
            } else {
                list.add(list2.get(i));
            }
            System.out.println("22++");
        }
        System.out.println("33");
        list.forEach(data -> {
            System.out.println(data);
        });
    }

    public static void forTest3() {
        List list = new ArrayList();
        List list2 = Lists.newArrayList(1, 2, 3);

        for (Object data : list2) {
            if (data.equals(1)) {
                list2.add(data);
                // break;
                // return;
            } else if (data.equals(2)) {
                list2.add(data);
                continue;
            } else {
                list2.add(data);
            }
            System.out.println("22++");
        }
        System.out.println("33");
        list2.forEach(data -> {
            System.out.println(data);
        });
    }
}
//develop==2
//develop==11
//master
//bbb