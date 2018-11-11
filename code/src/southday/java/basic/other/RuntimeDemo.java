package southday.java.basic.other;

/* Runtime 类
 *     |-- 该类中并没有提供构造函数，说明不可以直接new对象，
 *     |-- 那么会直接想到，该类中的方法应该是static的，而发现该类中有非static方法
 *     |-- 那么该类中一定存在static方法来获取该类对象，返回值类型是本类类型
 * 
 * 由这个特点可以看出，该类使用了单例设计模式
 * 该方法是：
 * (1) public static Runtime getRuntime() {...}
 * (2) public Process exec(String command) throws IOException {...} // 在单独的进程中执行指定的字符串命令
 * 
 */

public class RuntimeDemo {
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("D:\\GAMES\\Hash_1.0.4.exe"); // 抛出 IOException
        Thread.sleep(2000); // 让进程先睡4秒，不然杀得太快，根本看不见这个程序被启动过
        
        pr.destroy(); //　杀掉子进程（Process对象是由 Runtime对象的exec返回的指定对象）
        /* 看到 pr.destroy() 我感觉很奇怪，因为 Runtime.exec() 返回的是一个 Process 的引用pr
         * 而 Process中的 destroy() 方法是抽象的，那为什么 pr 能调用destroy()方法呢？？
         * 经过查阅资料才发现： 【网友adan1的回答】：
         *   (1) Runtime.exec() 返回的Process实例是基于Process子类，这点无需质疑。
         * 任何成功实例化的对象所属的类都是非抽象的实现类，抽象的方法已被实现。
         *   (2) Runtime.exec 实际上调用了 java.lang.ProcessBuilder 的实现，不懂的可查 API 文档；
         * 而ProcessBuilder 内部调用 java.lang.ProcessImpl.start(...) 静态方法 返回1个 ProcessImpl实例；
         * ProcessImpl类 正好是 Process类 的子类。
         */
        // 我用下面这条语句来输出 pr这个对象到底是属于那个类的
        System.out.println(pr.getClass().getName()); // 参考java反射机制---输出 ：java.lang.ProcessImpl
        
    }    
}
