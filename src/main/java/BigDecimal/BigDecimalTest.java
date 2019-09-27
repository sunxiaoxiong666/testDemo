package BigDecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTest {

    public static void main(String[] args) {
       /* NumberFormat currency=NumberFormat.getCurrencyInstance();//建立货币格式化引用
        NumberFormat percent=NumberFormat.getPercentInstance();//建立百分比格式化引用
        percent.setMaximumFractionDigits(3);//百分比小数点最多3位

        BigDecimal loanAmout=new BigDecimal("1500.66");//贷款金额
        BigDecimal rate=new BigDecimal("0.008");//利率
        BigDecimal sum=loanAmout.multiply(rate);//相乘得到利息

        System.out.println("金额为："+currency.format(loanAmout));
        System.out.println("利率为："+percent.format(rate));
        System.out.println("利息为："+currency.format(sum));*/

        BigDecimal a = new BigDecimal("66");
        BigDecimal b = new BigDecimal(6.66);
        BigDecimal b2 = new BigDecimal(Double.toString(6.6));
        BigDecimal b3 = BigDecimal.valueOf(6.6);
        BigDecimal c = new BigDecimal(666);
        BigDecimal add = a.add(b2);//加
        BigDecimal multiply = a.multiply(b3);//乘
        BigDecimal subtract = a.subtract(c);//减
        BigDecimal divide = a.divide(b3);//除
        BigDecimal divide1 = a.divide(b, 1, RoundingMode.HALF_UP);
        System.out.println(divide1 + "======");

        int i = a.compareTo(b2);//进行相比较
        int i1 = a.compareTo(c);
        System.out.println(i);
        System.out.println(i1);

        System.out.println(multiply);
        System.out.println(subtract);
        System.out.println(divide);
        System.out.println(add);
        System.out.println(a + "," + b + "," + b2 + "," + b3 + "," + c);
    }
}
