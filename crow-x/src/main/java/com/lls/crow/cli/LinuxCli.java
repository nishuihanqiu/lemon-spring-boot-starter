package com.lls.crow.cli;

import org.apache.commons.cli.*;

/************************************
 * LinuxCli
 * @author liliangshan
 * @date 2019/8/24
 ************************************/
public class LinuxCli {

    public static void testOption(String[] args) {
        CommandLineParser parser = new DefaultParser();

        // create the Options
        Options options = new Options();
        options.addOption("a", "all", false, "do not hide entries starting with .");
        options.addOption("A", "almost-all", false, "do not list implied . and ..");
        options.addOption("b", "escape", false, "print octal escapes for non graphic "
                + "characters");
        options.addOption(Option.builder("bs")
                .desc("use SIZE-byte blocks")
                .hasArg()
                .longOpt("block-size")
                .argName("SIZE")
                .build());
        options.addOption("B", "ignore-backups", false, "do not list implied entried "
                + "ending with ~");
        options.addOption("c", false, "with -lt: sort by, and show, ctime (time of last "
                + "modification of file status information) with "
                + "-l:show ctime and sort by name otherwise: sort "
                + "by ctime");
        options.addOption("C", false, "list entries by columns");
        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("block-size")) {
                System.out.println(line.getOptionValue("block-size"));
            }
        } catch (ParseException exp) {
            System.out.println("Unexpected exception:" + exp.getMessage());
        }

    }

}
