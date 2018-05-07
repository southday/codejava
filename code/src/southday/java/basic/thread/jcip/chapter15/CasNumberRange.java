package southday.java.basic.thread.jcip.chapter15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 程序清单 15-3 通过CAS来维持包含多个变量的不变性条件<p>
 * 参看P56 程序清单 4-10
 * @author southday
 * @date 2018年5月1日
 */
public class CasNumberRange {
    private static class IntPair {
        final int lower;
        final int upper; // 不变性条件：lower <= upper
        
        IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
        // ... 其他方法
    }
    
    private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));
    
    public int getLower() {
        return values.get().lower;
    }
    
    public int getUpper() {
        return values.get().upper;
    }
    
    public void setLower(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i > oldv.upper) {
                throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
            }
            IntPair newv = new IntPair(i, oldv.upper);
            if (values.compareAndSet(oldv, newv)) {
                return;
            }
        }
    }
    
    public void setUpper(int i) {
        while (true) {
            IntPair oldv = values.get();
            if (i < oldv.lower) {
                throw new IllegalArgumentException("Can't set upper to " + i + " < lower");
            }
            IntPair newv = new IntPair(oldv.lower, i);
            if (values.compareAndSet(oldv, newv)) {
                return;
            }
        }
    }
}
