package southday.java.pattern.state.state;

/**
 * 糖果机测试
 * @author southday
 * @date 2018年5月31日
 */
public class CandyMachineTestDrive {
    public static void main(String[] args) {
        CandyMachine candyMachine = new CandyMachine(5);
        
        System.out.println(candyMachine);
        
        candyMachine.insert1Yuan();
        candyMachine.turnCrank();
        
        System.out.println(candyMachine);
        
        candyMachine.insert1Yuan();
        candyMachine.turnCrank();
        candyMachine.insert1Yuan();
        candyMachine.turnCrank();
        
        System.out.println(candyMachine);
    }
}
