package southday.java.basic.concurrent.jmc.c03.pc;

public class Consumer {
    private Storage storage;
    
    public Consumer(Storage storage) {
        this.storage = storage;
    }
    
    public void takeGoods() {
        try {
            synchronized (storage) {
                if (!storage.getHasGoods()) {
                    System.out.println("-----consumer [" + Thread.currentThread().getName() + "] waiting...");
                    storage.wait();
                } else {
                    Thread.sleep(1000);
                    System.out.println("-----consumer [" + Thread.currentThread().getName() + "] running");
                    storage.setHasGoods(false);
//                    storage.notify();
                    storage.notifyAll();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
