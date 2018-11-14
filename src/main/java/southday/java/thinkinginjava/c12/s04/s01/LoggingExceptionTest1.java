package southday.java.thinkinginjava.c12.s04.s01;

/**
 * Author southday
 * Date   2018/11/14
 */
public class LoggingExceptionTest1 {
    public static void main(String[] args) {
        try {
            throw new LoggingException();
        } catch (LoggingException e) {
            System.err.println("Caught " + e);
        }

        try {
            throw new LoggingException();
        } catch (LoggingException e) {
            System.err.println("Caught " + e);
        }
    }
}

/*
11月 14, 2018 7:47:06 下午 southday.java.thinkinginjava.c12.s04.s01.LoggingException <init>
严重: southday.java.thinkinginjava.c12.s04.s01.LoggingException
	at southday.java.thinkinginjava.c12.s04.s01.LoggingExceptionTest1.main(LoggingExceptionTest1.java:10)

Caught southday.java.thinkinginjava.c12.s04.s01.LoggingException
11月 14, 2018 7:47:06 下午 southday.java.thinkinginjava.c12.s04.s01.LoggingException <init>
严重: southday.java.thinkinginjava.c12.s04.s01.LoggingException
	at southday.java.thinkinginjava.c12.s04.s01.LoggingExceptionTest1.main(LoggingExceptionTest1.java:16)

Caught southday.java.thinkinginjava.c12.s04.s01.LoggingException
 */