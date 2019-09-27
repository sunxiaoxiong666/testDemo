package qingxi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ss {
    public static void main(String[] args) {

        String aa="aa bb";
        String a1 = aa.substring(0, aa.indexOf(" ") + 1);
        String a2 = aa.substring(aa.indexOf(" ")+1);
        System.out.println(a1);
        System.out.println(a2);
    }
}
