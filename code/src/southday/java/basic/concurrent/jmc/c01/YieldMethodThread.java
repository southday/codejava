package southday.java.basic.concurrent.jmc.c01;

/* 使用yield()方法让调用者暂时放弃CPU资源，将它让给其他的任务去占用CPU执行时间。
 * 但放弃的时间不确定，有可能刚放弃又马上获取CPU时间片。
 * 
 * 本例主要是看一下，通过yield()放弃CPU资源的程序执行效果
 * 1) 在没有执行Thread.yield()情况下：spend time = 2ms;
 * 2) 在执行了Thread.yield()情况下： spend time = 948ms;
 */

public class YieldMethodThread {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                for (int i = 0; i < 5000000; i++) Thread.yield();
                long endTime = System.currentTimeMillis();
                System.out.println("spend time = " + (endTime - startTime) + "ms");
            }
        };
        thread.start();
    }
}
