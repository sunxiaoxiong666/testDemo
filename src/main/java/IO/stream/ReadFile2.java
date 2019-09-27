package IO.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFile2 {

    public static void main(String[] args) {
        String path="E:\\streamTest.txt";
        List<String> list=new ArrayList<String>();
        try {
            Stream<String> stram= Files.lines(Paths.get(path));
            list=stram.filter(line ->!line.startsWith("line3"))
                    .map(String::toUpperCase)
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                    .collect(Collectors.toList());
            System.out.println(list);
            list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}