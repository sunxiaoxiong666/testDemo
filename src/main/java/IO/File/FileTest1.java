package IO.File;

/*
 * @author: sunxiaoxiong
 */

import java.io.File;
import java.io.IOException;

public class FileTest1 {
    public static void main(String[] args) throws Exception {
        File file=new File("C:\\Users\\admin\\Desktop\\财务代码/1.txt");
        System.out.println(file.exists());
        System.out.println(file.getName());
        System.out.println(file.getParent());
        System.out.println(file.getPath()+"++");
        System.out.println(file.length());

        File file1=new File("C:\\Users\\admin\\Desktop\\财务代码/2.txt");
        boolean b = file1.createNewFile();
        System.out.println(b);

        File file2 = new File("C:\\Users\\admin\\Desktop\\财务代码/2");
        boolean mkdir = file2.mkdir();
        System.out.println(mkdir);

        File file3 = new File("C:\\Users\\admin\\Desktop\\财务代码/2/1");
        boolean mkdirs = file3.mkdirs();
        System.out.println(mkdirs);

        boolean delete = file1.delete();
        System.out.println(delete);

//        file2.delete();

        String[] list = file2.list();
        for(String str:list){
            System.out.println(str);
        }

        File[] files = file2.listFiles();
        for (File f:files){
            System.out.println(f);
        }
    }
}
