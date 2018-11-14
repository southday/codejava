package southday.java.thinkinginjava.c15.s04.s06;

import java.util.HashSet;
import java.util.Set;

/**
 * Set工具集，用来求解两个集合a,b的合集，交集，差集，不相交集
 * @author southday
 * @date 2018年10月25日
 */
public class SetUtils {
    // a | b (a,b的合集)
    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<T>(a);
        result.addAll(b);
        return result;
    }
    
    // a & b (a,b的交集)
    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<T>(a);
        result.retainAll(b);
        return result;
    }
    
    // a - b (a,b的差集)
    public static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
        Set<T> result = new HashSet<T>(superset);
        result.removeAll(subset);
        return result;
    }
    
    // (a | b) - (a & b) (a,b的不相交集)
    public static <T> Set<T> complement(Set<T> a, Set<T> b) {
        return difference(union(a, b), intersection(a, b));
    }
}
