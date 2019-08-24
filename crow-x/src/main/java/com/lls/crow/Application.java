package com.lls.crow;

import com.lls.crow.cli.AntCli;
import com.lls.crow.cli.LinuxCli;
import org.apache.commons.cli.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/************************************
 * Application
 * @author liliangshan
 * @date 2019/8/23
 ************************************/
public class Application {


    public static void main(String[] args) {
        testAntCli(args);
        testLinuxCli(args);
    }

    private static void demo(String[] args) throws ParseException {
        //  gradle run --args=-t
        Options options = new Options();
        options.addOption("t", false, "display current time");
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);
        if (commandLine.hasOption("t")) {
            System.out.println((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
        } else {
            System.out.println((new SimpleDateFormat("yyyy-MM-dd")).format(new Date()));
        }
    }

    private static void testAntCli(String[] args) {
        // gradle run --args=-help  / gradle run --args="--build-file 87" / gradle run --args="-bf 87"
        AntCli antCli = new AntCli();
        antCli.execCommand(args);
    }

    private static void testLinuxCli(String[] args) {
        String[] arg = new String[]{ "--block-size=10" };
        LinuxCli.testOption(arg);
    }

}
