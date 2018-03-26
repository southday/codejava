package southday.java.basic.thread.jmc.chapter3.pc;

public class ConsumerThread implements Runnable {
    private Consumer consumer;
    
    public ConsumerThread(Consumer consumer) {
        this.consumer = consumer;
    }
    
    @Override
    public void run() {
        while (true) {
            consumer.takeGoods();
        }
    }
}
