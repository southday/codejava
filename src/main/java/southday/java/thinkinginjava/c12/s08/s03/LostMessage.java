package southday.java.thinkinginjava.c12.s08.s03;

/*
finally 使用不当，则可能导致某些异常信息丢失，如下面的例子

一个简单的例子：
try {
    throw new RuntimeException();
} finally {
    return;
}
运行这个程序，即使内部抛出了异常，也不会产生任何输出

也许在未来的Java版本中会修正这个问题，另一方面，要把所有抛出异常的方法，全部打包放到try-catch子句里
比如下面的：dispose()方法
 */

class VeryImportantException extends Exception {
    public String toString() {
        return "A very important exceptino!";
    }
}

class HoHumException extends Exception {
    public String toString() {
        return "A trivial exception";
    }
}

/**
 * Author southday
 * Date   2018/11/15
 */
public class LostMessage {
    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }

    void dispose() throws HoHumException {
        throw new HoHumException();
    }

    public static void main(String[] args) {
        try {
            LostMessage lm = new LostMessage();
            try {
                lm.f();
            } finally {
                lm.dispose(); // 抛出HoHumException，而f()抛出的VeryImportantException丢失
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
