package southday.java.thinkinginjava.c12.s04.s01;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Author southday
 * Date   2018/11/14
 */
public class LoggingException extends Exception {
    private static Logger logger = Logger.getLogger("LoggingException");

    public LoggingException() {
        StringWriter trace = new StringWriter();
        printStackTrace(new PrintWriter(trace));
        logger.severe(trace.toString()); // severe 严峻的 严厉的
    }
}
