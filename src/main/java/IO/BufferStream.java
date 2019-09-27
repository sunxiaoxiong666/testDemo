package IO;

/*
 * @author: sunxiaoxiong
 */

import java.io.*;

public class BufferStream {
    public static void main(String[] args) {
        try {
            //1.创建字节输出流
            FileOutputStream out = new FileOutputStream("C:/Users/admin\\Desktop/财务代码\\1.txt", true);
            //将out流对象，封装成高效流对象
            BufferedOutputStream bout = new BufferedOutputStream(out);
            bout.write("\r\n高效输出流就是牛逼".getBytes());
            bout.close();

            FileInputStream in = new FileInputStream("C:/Users/admin\\Desktop/财务代码\\1.txt");
            BufferedInputStream bin = new BufferedInputStream(in);
            byte[] buf = new byte[1024];
            int i = -1;
            while ((i = bin.read(buf)) != -1) {
                System.out.println(new String(buf, 0, i)+"=====");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
