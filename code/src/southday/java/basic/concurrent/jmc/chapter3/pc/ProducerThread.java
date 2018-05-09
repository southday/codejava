package southday.java.basic.concurrent.jmc.chapter3.pc;

public class ProducerThread implements Runnable {
    private Producer producer;
    
    public ProducerThread(Producer producer) {
        this.producer = producer;
    }
    
    @Override
    public void run() {
        while (true) {
            producer.sendGoods();
        }
    }
}
