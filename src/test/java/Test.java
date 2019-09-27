

/*
 * @author: sunxiaoxiong
 */

import qingxi.Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Test {
    public static void main(String[] args) {
        String s = "dddddddddddddddddddddddddddddddddddddddddddddddddddddd";
        System.out.println(s.length());
        String divide = Utils.divide("22 aa", "33 bb");
        System.out.println(divide);
        String s1 = Utils.extractStr("2.2 aa", "(\\d+\\.\\d+)|(\\d+)");
        System.out.println(s1+"aaaaaa");
        boolean containNumber = Utils.isContainNumber("22 aa");
        System.out.println(containNumber);
        // System.out.println(0 / 0);


        String aa="6";
        BigDecimal bb=new BigDecimal(-3);
        BigDecimal a=new BigDecimal(aa);
        System.out.println(a.subtract(bb));

        String lastReportYear = new BigDecimal("2016").subtract(new BigDecimal(1)).toPlainString();//上一年份
        System.out.println(lastReportYear);

        DecimalFormat format = new DecimalFormat("0.00");
        String abc ="1289.3656";
        abc=abc.replace(",","");
        String a1 = format.format(new BigDecimal(abc));
        System.out.println(a1);

        String str = "aa是是是";
        String reg = "[^\u4e00-\u9fa5]";
        str = str.replaceAll(reg, "");
        System.out.println(str);

    }
}
