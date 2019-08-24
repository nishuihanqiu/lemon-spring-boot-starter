package com.lls.crow.cli;

import org.apache.commons.cli.*;

/************************************
 * AntCli
 * @author liliangshan
 * @date 2019/8/24
 ************************************/
public class AntCli {

    private Options buildOptions(String[] args) {
        Option help = new Option("help", "print this message");
        Option projectHelp = new Option("ph", "project-help",  false, "print project help information");
        Option version = new Option("version", "print the version information and exit");
        Option quiet = new Option("quiet", "be extra quiet");
        Option verbose = new Option("verbose", "be extra verbose");
        Option debug = new Option("debug", "print debugging information");
        Option emacs = new Option("emacs", "produce logging information without adornments");

        Option logfile = Option.builder("logfile").argName("file")
                .hasArg()
                .desc("use given file for log")
                .build();

        Option logger = Option.builder("logger").argName("classname")
                .hasArg()
                .desc("the class which it to perform " + "logging")
                .build();

        Option listener = Option.builder("listener").argName("classname")
                .hasArg()
                .desc("add an instance of class as " + "a project listener")
                .build();

        Option buildFile = Option.builder("bf").argName("file")
                .hasArg()
                .longOpt("build-file")
                .desc("use given build-file")
                .build();

        Option find = Option.builder("find").argName("file")
                .hasArg()
                .desc("search for build-file towards the " + "root of the filesystem and use it")
                .build();
        Option property = Option.builder("D").argName("property=value")
                .numberOfArgs(2)
                .valueSeparator()
                .desc("use value for given property")
                .build();

        Options options = new Options();
        options.addOption(help);
        options.addOption(projectHelp);
        options.addOption(version);
        options.addOption(quiet);
        options.addOption(verbose);
        options.addOption(debug);
        options.addOption(emacs);
        options.addOption(logfile);
        options.addOption(logger);
        options.addOption(listener);
        options.addOption(buildFile);
        options.addOption(find);
        options.addOption(property);
        return options;
    }

    private void parseLine(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("build-file")) {
                System.out.println(line.getOptionValue("build-file"));
            }
            if (line.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("ant", options, true);
            }
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    public void execCommand(String[] args) {
        Options options = this.buildOptions(args);
        this.parseLine(options, args);
    }

}
