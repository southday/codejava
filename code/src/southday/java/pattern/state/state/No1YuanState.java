package southday.java.pattern.state.state;

/**
 * 无1元状态
 * @author southday
 * @date 2018年5月31日
 */
public class No1YuanState implements State {
    private CandyMachine candyMachine = null;
    
    public No1YuanState(CandyMachine candyMachine) {
        this.candyMachine = candyMachine;
    }

    @Override
    public void insert1Yuan() {
        // TODO Auto-generated method stub
        candyMachine.setState(candyMachine.getHas1YuanState());
        System.out.println("投币1元成功");
    }

    @Override
    public void eject1Yuan() {
        // TODO Auto-generated method stub
        System.out.println("退回1元失败，糖果机内无1元");
    }

    @Override
    public void turnCrank() {
        // TODO Auto-generated method stub
        System.out.println("转动曲柄失败，请先投币1元");
    }

    @Override
    public void dispense() {
        // TODO Auto-generated method stub
        System.out.println("发放糖果失败，请先投币1元");
    }

    public String toString() {
        return "[无1元]";
    }
}
