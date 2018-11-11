package southday.java.basic.concurrent.jcip.c02;

/* 要求：
 * 1.服务端的service()方法中对传入的i(Integer)生成相应的字符串数组s(String[])
 * 2.服务端使用两个变量来保存最近一次请求的i和s：lastNumber, lastStrs2
 * 3.确保服务端的线程安全
 */

/**
 * 程序清单 2-8 使用synchronized进行同步 <br>
 * 使用不可变对象和volatile类型变量实现：{@link southday.java.basic.concurrent.jcip.c03.VolatileCachedFactorizer}
 * @author southday
 * @date 2018年4月15日
 */
public class CachedFactorizer {
    private Integer lastNumber;
    private String[] lastStrs;
    
    /* 使用同步代码块进行同步需要注意的两个问题：
     * 1.简单性：对整个方法进行同步
     * 2.并发性：对尽可能短的代码路径进行同步
     * 
     * 由于获取和释放锁等操作需要一定的开销，所以我们尽可能对整个方法进行同步，
     * 此外，对于一些较为耗时且无需同步的代码，我们将其放在同步代码块之外，以提升并发性
     */
    public void service(String req, String resp) {
        Integer i = extract(req);
        String[] strs = null;
        synchronized (this) {
            if (i.equals(lastNumber)) {
                strs = lastStrs.clone();
            }
        }
        if (strs == null) {
            // 将耗时且无需同步的代码放在同步代码块之外，以提高效率
            strs = generateStrs(i);
            synchronized (this) {
                lastNumber = i;
                lastStrs = strs.clone();
            }
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
