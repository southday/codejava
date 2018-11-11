package southday.java.basic.concurrent.jmc.c01;

public class ThreadDemo1 extends Thread {
    private String mesg;
    private int count;
    
    @Override
    public void run() {
        while (count-- > 0) {
            System.out.println(mesg + count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println(mesg + " all done " + count);
        }
    }
    
    public ThreadDemo1(final String mesg, int n) {
        this.mesg = mesg;
        count = n;
        setName(mesg + "runner Thread");
    }
    
    public static void main(String[] args) {
        new ThreadDemo1("Hello from x", 10).start();
        new ThreadDemo1("Hello from y", 15).start();
    }
}
