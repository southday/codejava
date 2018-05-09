package southday.java.basic.concurrent.jmc.chapter3.pc;

public class Producer {
    private Storage storage;
    
    public Producer(Storage storage) {
        this.storage = storage;
    }
    
    public void sendGoods() {
        try {
            synchronized (storage) {
                if (storage.getHasGoods()) {
                    System.out.println("producer [" + Thread.currentThread().getName() + "] waiting...");
                    storage.wait();
                } else {
                    Thread.sleep(1000);
                    System.out.println("producer [" + Thread.currentThread().getName() + "] runing");
                    storage.setHasGoods(true);
//                    storage.notify();
                    storage.notifyAll();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
