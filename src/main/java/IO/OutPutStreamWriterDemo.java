package IO;

/*
 * @author: sunxiaoxiong
 */

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Arrays;

public class OutPutStreamWriterDemo {
    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\表\\1.txt");
        OutputStreamWriter ow = new OutputStreamWriter(out, "utf-8");
        ow.write("你好，字符流");
        ow.close();
        byte[] bytes = "你好".getBytes();
        System.out.println(Arrays.toString(bytes));

        FileUtils.copyDirectoryToDirectory(new File("C:\\Users\\admin\\Desktop\\财务代码"),new File("C:\\Users\\admin\\Desktop\\表"));

        FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\表\\1.txt");
//        InputStreamReader ir = new InputStreamReader(in);

        FileReader ir=new FileReader("C:\\Users\\admin\\Desktop\\表\\1.txt");

        int i = -1;
        char[] c = new char[1024];
        while ((i = ir.read(c)) != -1) {
            String s = new String(c, 0, i);
            System.out.println(s);
            System.out.println(i);
        }
        ir.close();
    }
}
