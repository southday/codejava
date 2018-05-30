package southday.java.pattern.state.state;

/**
 * 赢家状态
 * @author southday
 * @date 2018年5月31日
 */
public class WinnerState implements State {
    private CandyMachine candyMachine = null;
    
    public WinnerState(CandyMachine candyMachine) {
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
        System.out.println("恭喜你成为赢家！你将获得2颗糖果！");
        candyMachine.releaseOneCandy();
        if (candyMachine.getCount() == 0) {
            System.out.println("很遗憾，糖果机内只剩1颗糖果");
            candyMachine.setState(candyMachine.getSoldOutState());
        } else {
            candyMachine.releaseOneCandy();
            if (candyMachine.getCount() > 0) {
                System.out.println("已发放2颗糖果");
                candyMachine.setState(candyMachine.getNo1YuanState());
            } else {
                System.out.println("已发放2颗糖果，糖果售空");
                candyMachine.setState(candyMachine.getSoldOutState());
            }
        }
    }

    public String toString() {
        return "[赢家]";
    }
}
