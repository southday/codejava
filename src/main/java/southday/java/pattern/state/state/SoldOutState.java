package southday.java.pattern.state.state;

/**
 * 糖果售罄状态
 * @author southday
 * @date 2018年5月31日
 */
public class SoldOutState implements State {
    private CandyMachine candyMachine = null;
    
    public SoldOutState(CandyMachine candyMachine) {
        this.candyMachine = candyMachine;
    }
    
    @Override
    public void insert1Yuan() {
        // TODO Auto-generated method stub
        System.out.println("投币1元失败，糖果已售完");
    }

    @Override
    public void eject1Yuan() {
        // TODO Auto-generated method stub
        System.out.println("退回1元失败，糖果已售完，糖果机内无1元");
    }

    @Override
    public void turnCrank() {
        // TODO Auto-generated method stub
        System.out.println("转动曲柄失败，糖果已售完");
    }

    @Override
    public void dispense() {
        // TODO Auto-generated method stub
        System.out.println("发放糖果失败，糖果已售完");
    }

    public String toString() {
        return "[糖果售罄]";
    }
}
