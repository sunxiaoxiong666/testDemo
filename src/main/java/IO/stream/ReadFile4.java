package IO.stream;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFile4 {

    public static void main(String[] args) {
        String path="E:\\streamTest.txt";
        FileReader file= null;
        try {
            file = new FileReader(path);
            BufferedReader reader=new BufferedReader(file);
            while (StringUtils.isNotBlank(reader.readLine())){
                System.out.println(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
