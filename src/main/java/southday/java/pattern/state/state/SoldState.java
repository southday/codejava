package southday.java.pattern.state.state;

/**
 * 出售糖果状态
 * @author southday
 * @date 2018年5月31日
 */
public class SoldState implements State {
    private CandyMachine candyMachine = null;
    
    public SoldState(CandyMachine candyMachine) {
        this.candyMachine = candyMachine;
    }
    
    @Override
    public void insert1Yuan() {
        // TODO Auto-generated method stub
        System.out.println("投币1元失败，在出售糖果过程中不能投币");
    }

    @Override
    public void eject1Yuan() {
        // TODO Auto-generated method stub
        System.out.println("退回1元失败，在出售糖果过程中禁止退回1元");
    }

    @Override
    public void turnCrank() {
        // TODO Auto-generated method stub
        System.out.println("转动曲柄失败，在出售糖果过程中进禁止动曲柄");
    }

    @Override
    public void dispense() {
        // TODO Auto-generated method stub
        candyMachine.releaseOneCandy();
        if (candyMachine.getCount() == 0) {
            candyMachine.setState(candyMachine.getSoldOutState());
            System.out.println("已发放1颗糖果，糖果售空");
        } else {
            candyMachine.setState(candyMachine.getNo1YuanState());
            System.out.println("已发放1颗糖果");
        }
    }

    public String toString() {
        return "[出售糖果]";
    }
}
