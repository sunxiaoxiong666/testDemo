package IO;

/*
 * @author: sunxiaoxiong
 */

import java.io.*;

public class FileCopy {
    public static void main(String[] args) throws Exception {
//        m1();
//        m2();
//        m3();
        m4();
    }

    //1.使用基本流，逐个字节复制
    public static void m1() {
        try {
            FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\财务代码\\租房提取.doc");
            FileOutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\财务代码\\第二步代码\\租房提取.doc");
            //创建一个int变量，用于保存每次读取到的字节码值
            int i = -1;
            long t1 = System.currentTimeMillis();
            while ((i = in.read()) != -1) {
                //输出
                out.write(i);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("时间1：" + (t2 - t1));
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //2.使用基本流字节数组复制
    public static void m2() throws Exception {
        FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\财务代码\\租房提取.doc");
        FileOutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\财务代码\\第二步代码\\租房提取.doc");
        //创建一个字节数组，保存每一次读取到的字节码值
        byte[] b = new byte[1024];
        //创建一个变量，用于保存每次读取的字节个数
        int i = -1;
        long t1 = System.currentTimeMillis();
        while ((i = in.read(b)) != -1) {
//            System.out.println(i);
            out.write(b, 0, i);
        }
        System.out.println("时间2：" + (System.currentTimeMillis() - t1));
        in.close();
        out.close();
    }

    //3.使用高效流，逐字节复制
    public static void m3() throws Exception {
        FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\财务代码\\租房提取.doc");
        BufferedInputStream bin = new BufferedInputStream(in);
        FileOutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\财务代码\\第二步代码\\租房提取.doc");
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int i = -1;
        long t1 = System.currentTimeMillis();
        while ((i = bin.read()) != -1) {
            System.out.println(i);
            bout.write(i);
        }
        System.out.println("时间3：" + (System.currentTimeMillis() - t1));
        bin.close();
        bout.close();
    }

    //4.使用高效流，字节数组
    public static void m4() throws Exception {
        FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\财务代码\\租房提取.doc");
        BufferedInputStream bin = new BufferedInputStream(in);
        System.out.println(bin.available());
        FileOutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\财务代码\\第二步代码\\租房提取.doc");
        BufferedOutputStream bout = new BufferedOutputStream(out);

        byte[] b = new byte[1024];
        int i = -1;
        long t1 = System.currentTimeMillis();
        while ((i = bin.read(b)) != -1) {
            System.out.println(new String(b,0,i));
            System.out.println("++++++++++++++++++++++++++++++++++++");
            bout.write(b, 0, i);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("时间4：" + (t2 - t1));
        bin.close();
        bout.close();
    }
}
