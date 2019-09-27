package commandLine;


import org.apache.commons.cli.*;

public class Dog {

    public static void main(String[] args) {

        int port = 8080;
        int number = 1;
//        String[] args2={"p","66"};
        Options option = new Options();
        option.addOption("p", "port", true, "默认是" + port);
        option.addOption("n", "number", true, "默认是1");
        option.addOption("h","help",false,"帮助信息");
        System.out.println(option.toString());
        CommandLineParser commandLineParser = new GnuParser();
        try {
            CommandLine commandLine = commandLineParser.parse(option, args);
            String p = commandLine.getOptionValue("p");
            System.out.println(p);
            if (commandLine.hasOption("n")) {
                String n = commandLine.getOptionValue("n");
                number = Integer.parseInt(n);
            }
            System.out.println(number);
            if(commandLine.hasOption("h")){
                HelpFormatter help=new HelpFormatter();
                help.printHelp("Dog",option);
                System.exit(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(args.toString());
    }
}
