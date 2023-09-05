package cn.iinux.java.alpha.basic;

import org.apache.commons.cli.*;

public class Starter {
    public static void main(String[] args) {
        Options options = new Options();

        Option functionOption = new Option("f", "function", true, "function");
        options.addOption(functionOption);

        Option hostnameOption = new Option("h", "hostname", true, "hostname");
        options.addOption(hostnameOption);

        Option portOption = new Option("p", "port", true, "port");
        options.addOption(portOption);

        Option title = new Option("t", "title", true, "title");
        options.addOption(title);

        Option content = new Option("c", "content", true, "content");
        options.addOption(content);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        String function = cmd.getOptionValue("function", "msg");

        switch (function) {
            case "server":
                server(cmd);
                break;
            case "msg":
            default:
                Dmb dmb = new Dmb();
                dmb.run(cmd.getOptionValue("content"), cmd.getOptionValue("title"), false);
                break;
        }


    }

    private static void server(CommandLine cmd) {
        int portNum;
        String hostname = cmd.getOptionValue("hostname", "0.0.0.0");
        String port = cmd.getOptionValue("port", "8000");
        if (port.length() > 0) {
            try {
                portNum = Integer.parseInt(port);
                MsgHttpServer m = new MsgHttpServer();
                m.setHostname(hostname);
                m.setPort(portNum);
                m.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
