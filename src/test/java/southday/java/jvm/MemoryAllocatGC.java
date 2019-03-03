package southday.java.jvm;

import org.junit.Test;

/**
 * 测试内存分配和回收
 * @author southday
 * @date 2019/3/3
 */
public class MemoryAllocatGC {
    private static final int _1MB = 1024 * 1024;

    /**
     * vm args: -Xms20m -Xmx20m -Xmn10m -Xlog:gc* -XX:SurvivorRatio=8
     */
    @Test
    public void testAllocation() {
        byte[] a1, a2, a3, a4;
        a1 = new byte[2 * _1MB];
        a2 = new byte[2 * _1MB];
        a3 = new byte[2 * _1MB];
        a4 = new byte[4 * _1MB];
    }

    /**
     * vm args:
     * -Xms20m -Xmx20m -Xmn10m -Xlog:gc* -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728（该参数只对Serial和ParNew两款收集器有效）
     */
    @Test
    public void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB]; // 直接分配到老年代
    }

    /**
     * vm args:
     * -Xms20m -Xmx20m -Xmn10m -Xlog:gc* -XX:SurvivorRatio=8
     * -XX:MaxTenuringThreshold=1（分别=1，=15时候测试）
     * -XX:+PrintTenuringDistribution（不支持）
     */
    @Test
    public void testTenuringThreshold() {
        byte[] a1, a2, a3;
        a1 = new byte[_1MB / 4];
        // 什么时候进入老年代取决于XX:MaxTenuringThreshold设置
        a2 = new byte[4 * _1MB];
        a3 = new byte[4 * _1MB];
        a3 = null;
        a3 = new byte[4 * _1MB];
    }
}
