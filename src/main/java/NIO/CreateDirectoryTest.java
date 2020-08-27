package NIO;

/*
 * @author: helloWorld
 * @date  : Created in 2020/8/27 13:19
 */

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateDirectoryTest {
    public static void main(String[] args) {
        //注意：使用这个Files.createDirectory(path1)这个方法创建文件时，要首先创建父目录，才能创建子目录
        //例如下面：要先创建tycdata目录，才能创建company_app_info目录
        try {
            String pa = "E:\\tycdata\\company_app_info\\";
            Path path1 = FileSystems.getDefault().getPath(pa);

            if (!Files.exists(path1)) {
                Files.createDirectory(path1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
