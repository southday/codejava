package southday.java.basic.concurrent.jmc.c01;

/* stop()方法已被废除，其是一种非常暴力的线程停止方法，
 * 用该方法停止线程可能会给数据造成不一致的结果。
 * 
 * 1) 调用stop()方法，会抛出java.lang.ThreadDeath异常，而这个异常一般不需要显示的捕捉
 * 
 * 2) 如下面的例子，说明了使用stop()方法停止线程导致输出的数据不一致
 * 按理来说应该是输出: b bb
 * 而实际却输出：        b aa
 * 原因： 
 *     username = "a" password = "aa" 是默认值
 *  Thread的run()中传入参数"b" "bb"对应的要覆盖username和password
 *  可是，当"b"覆盖掉"a"后，Thread线程被stop()了，"bb"还没来得及覆盖"aa"，
 *  所以最后输出的时候，username = "b" password = "aa"
 */

public class StopMethodThread {
    public static void main(String[] args) {
        try {
            SynchronizedObject sobj = new SynchronizedObject();
            Thread Thread = new Thread() {
                @Override
                public void run() {
                    sobj.printString("b", "bb");
                }
            };
            Thread.start();
            Thread.sleep(500);
            Thread.stop();
            System.out.println(sobj.getUsername() + " " + sobj.getPassword());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SynchronizedObject {
    private String username = "a";
    private String password = "aa";
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public synchronized void printString(String username, String password) {
        try {
            this.username = username;
            Thread.sleep(100000);
            this.password = password;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
