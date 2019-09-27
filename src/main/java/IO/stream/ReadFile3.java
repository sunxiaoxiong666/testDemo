package IO.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFile3 {

    public static void main(String[] args) {
        String path = "E:\\streamTest.txt";
        List<String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(path));
            Stream<String> stream =  bufferedReader.lines();
            list = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.forEach(System.out::println);
    }
}
