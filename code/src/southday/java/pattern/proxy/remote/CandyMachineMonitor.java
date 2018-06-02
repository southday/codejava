package southday.java.pattern.proxy.remote;

/**
 * 糖果机监视器，相当于客户端
 * @author southday
 * @date 2018年6月1日
 */
public class CandyMachineMonitor {
    private CandyMachineRemote candyMachine;
    
    public CandyMachineMonitor(CandyMachineRemote candyMachine) {
        this.candyMachine = candyMachine;
    }
    
    public void report() {
        try {
            System.out.println("Candy Machine: " + candyMachine.getLocation());
            System.out.println("Current inventory: " + candyMachine.getCount());
            System.out.println("Current state: " + candyMachine.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
