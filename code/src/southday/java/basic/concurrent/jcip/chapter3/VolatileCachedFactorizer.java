package southday.java.basic.concurrent.jcip.chapter3;

import java.util.Arrays;

/* 要求：
 * 1.服务端的service()方法中对传入的i(Integer)生成相应的字符串数组s(String[])
 * 2.服务端使用两个变量来保存最近一次请求的i和s：lastNumber, lastStrs2
 * 3.确保服务端的线程安全
 */

/**
 * 程序清单 3-12 对数值及其字符串结果进行缓存的不可变容器类 <br>
 * 在某些情况下，不可变对象能够提供一种弱形式的原子性
 * @author southday
 * @date 2018年4月15日
 */
class OneValueCache {
    private final Integer lastNumber;
    private final String[] lastStrs;
    
    public OneValueCache(Integer i, String[] s) {
        lastNumber = i;
        lastStrs = s;
    }
    
    public String[] getStrs(Integer i) {
        if (lastNumber == null || !lastNumber.equals(i)) {
            return null;
        } else {
            return Arrays.copyOf(lastStrs, lastStrs.length);
        }
    }
}

/**
 * 程序清单 3-13 使用指向不可变容器对象的volatile类型引用以缓存最新的结果 <br>
 * 1.当一个线程将volatile类型的cache设置为引用一个新的OneValueCache时，其他线程就会立即看到新缓存的数据 <br>
 * 2.通过使用包含多个状态变量的容器对象来维持不变性条件，并使用一个volatile类型的引用来确保可见性，
 * 使得Volatile Cached Factorizer 在没有显示地使用锁的情况下仍然是线程安全的 <br>
 * 3.使用同步机制的代码：{@link southday.java.basic.concurrent.jcip.chapter2.CachedFactorizer}
 * @author southday
 * @date 2018年4月15日
 */
public class VolatileCachedFactorizer {
    private volatile OneValueCache cache = new OneValueCache(null, null);
    
    /* 原例中是 service(ServletRequest req, ServletResponse resp)
     * 这里我为了方便表达意思，都将参数定义为String类型
     * 以下的：extract(), generateStrs(), encodeIntoResponse()方法也只是表示个意思
     */
    public void service(String req, String resp) {
        Integer i = extract(req);
        String[] strs = cache.getStrs(i);
        if (strs == null) {
            strs = generateStrs(i);
            cache = new OneValueCache(i, strs);
        }
        encodeIntoResponse(resp, strs);
    }
    
    private Integer extract(String req) {
        return new Integer(req);
    }
    
    private String[] generateStrs(Integer i) {
        return new String[]{i.toString()};
    }
    
    private void encodeIntoResponse(String resp, String[] strs) {
        System.out.println("encode into response");
    }
}
