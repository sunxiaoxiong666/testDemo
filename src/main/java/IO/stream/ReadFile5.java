package IO.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile5 {

    public static void main(String[] args) {
        String path = "E:/streamTest.txt";
        Scanner scanner;


        try {
            scanner = new Scanner(new File(path));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
