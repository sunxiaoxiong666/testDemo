package IO.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFile {

    public static void main(String[] args) {
        String path = "E:\\streamTest.txt";
        try {
            System.out.println(Paths.get(path));
            Stream<String> stream = Files.lines(Paths.get(path));
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
