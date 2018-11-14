package southday.java.thinkinginjava.c12.s04.s01;

import southday.java.thinkinginjava.c14.s08.Null;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Author southday
 * Date   2018/11/14
 */
public class LoggingExceptionTest2 {
    /*
    更常见的情况是：我们需要捕获和记录其他人编写的异常，
    如下面的：NullPointerException
     */

    private static Logger logger = Logger.getLogger("LoggingExceptionTest2");

    public static void logException(Exception e) {
        StringWriter trace = new StringWriter();
        e.printStackTrace(new PrintWriter(trace));
        logger.severe(trace.toString());
    }

    public static void main(String[] args) {
        try {
            throw new NullPointerException();
        } catch (NullPointerException e) {
            logException(e);
        }
    }
}

/*
11月 14, 2018 8:00:31 下午 southday.java.thinkinginjava.c12.s04.s01.LoggingExceptionTest2 logException
严重: java.lang.NullPointerException
	at southday.java.thinkinginjava.c12.s04.s01.LoggingExceptionTest2.main(LoggingExceptionTest2.java:29)
 */

