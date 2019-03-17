package southday.java.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * OutOfMemoryTest<br/>
 * VM Args: -Xms20 -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author southday
 * @date 2019/3/2
 */
public class HeapOutOfMemoryTest {
    static class Obj {}

    public static void main(String[] args) {
        List<Obj> list = new ArrayList<>();
        while (true)
            list.add(new Obj());
    }
}
