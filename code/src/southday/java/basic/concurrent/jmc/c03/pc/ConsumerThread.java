package southday.java.basic.concurrent.jmc.c03.pc;

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
