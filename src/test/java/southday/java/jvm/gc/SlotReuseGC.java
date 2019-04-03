package southday.java.jvm.gc;

/**
 * 局部变量表中Slot复用对GC行为的影响
 * jvm args: -verbose:gc
 * @author southday
 * @date 2019/3/17
 */
public class SlotReuseGC {
    // 1) 在执行System.gc()时，变量placeholder还处于作用域之内，虚拟机自然不能回收placeholder的内存；
//    public static void main(String[] args) {
//        byte[] placeholder = new byte[64 * 1024 * 1024];
//        System.gc();
//    }
    /*
    [0.078s][info][gc] Using G1
    [0.263s][info][gc] GC(0) Pause Initial Mark (G1 Humongous Allocation) 5M->3M(126M) 1.764ms
    [0.263s][info][gc] GC(1) Concurrent Cycle
    [0.297s][info][gc] GC(1) Pause Remark 68M->68M(126M) 0.808ms
    [0.301s][info][gc] GC(2) Pause Full (System.gc()) 68M->67M(126M) 3.776ms
    [0.302s][info][gc] GC(1) Pause Cleanup 67M->67M(126M) 0.001ms
    [0.302s][info][gc] GC(1) Concurrent Cycle 38.835ms
     */

    // 2) 代码虽然已经离开了placeholder的作用域，但在此之后，没有任何对局部变量表的读写操作，
    // placeholder原本所占用的Slot还没有被其他变量所复用，所以作为GC Roots一部分的局部变量表仍然保持着对它的关联；
//    public static void main(String[] args) {
//        {
//            byte[] placeholder = new byte[64 * 1024 * 1024];
//        }
//        System.gc();
//    }
    /*
    [0.022s][info][gc] Using G1
    [0.207s][info][gc] GC(0) Pause Initial Mark (G1 Humongous Allocation) 5M->2M(126M) 1.828ms
    [0.207s][info][gc] GC(1) Concurrent Cycle
    [0.235s][info][gc] GC(1) Pause Remark 68M->68M(126M) 0.815ms
    [0.236s][info][gc] GC(1) Pause Cleanup 68M->68M(126M) 0.102ms
    [0.239s][info][gc] GC(2) Pause Full (System.gc()) 68M->67M(126M) 3.706ms
    [0.240s][info][gc] GC(1) Concurrent Cycle 33.330ms
     */

    // 3) int a = 0，对placeholder所占用的Slot进行复用，所以会被回收；
    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }

    /*
    [0.022s][info][gc] Using G1
    [0.212s][info][gc] GC(0) Pause Initial Mark (G1 Humongous Allocation) 5M->2M(126M) 2.102ms
    [0.213s][info][gc] GC(1) Concurrent Cycle
    [0.243s][info][gc] GC(1) Pause Remark 68M->68M(126M) 0.788ms
    [0.252s][info][gc] GC(2) Pause Full (System.gc()) 68M->2M(14M) 8.515ms
    [0.252s][info][gc] GC(1) Concurrent Cycle 39.880ms
     */
}
