package southday.java.thinkinginjava.c14.s08;

import java.util.List;

/**
 * 空对象-Robot
 * @author southday
 * @date 2018年10月25日
 */
public interface Robot {
    String name();
    String model();
    List<Operation> operations();
    
    class Test {
        public static void test(Robot r) {
            if (r instanceof Null) // == Null.class.isInstance(r)
                System.out.println("[Null Robot]");
            System.out.println("Robot name: " + r.name());
            System.out.println("Robot model: " + r.model());
            for (Operation op : r.operations()) {
                System.out.println(op.description());
                op.command();
            }
        }
    }
}
