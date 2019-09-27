package IO;

/*
 * @author: sunxiaoxiong
 */

import java.io.*;

public class OutPutStream {
    public static void main(String[] args) {
        try {
            //输出
            OutputStream out = new FileOutputStream("C:/Users/admin\\Desktop/财务代码\\1.txt", true);
            byte[] b = "hello world".getBytes();
            /*for (byte bb : b) {
                System.out.println(bb);
            }*/
            out.write("\r\n".getBytes());
            out.write("你好".getBytes());
            out.close();
            //读入
            InputStream in = new FileInputStream("C:/Users/admin\\Desktop/财务代码\\1.txt");
            byte[] bb = new byte[1024];
            int i = 0;
            int num = 0;
            i = in.read(bb);
            System.out.println(i);
            //将字节转化为字符串
            String s = new String(bb, 0, i);
            System.out.println(s);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
