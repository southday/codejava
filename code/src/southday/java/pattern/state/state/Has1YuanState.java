package southday.java.pattern.state.state;

import java.util.Random;

/**
 * 有1元状态
 * @author southday
 * @date 2018年5月31日
 */
public class Has1YuanState implements State {
    private Random rnd = new Random(System.currentTimeMillis());
    private CandyMachine candyMachine = null;
    
    public Has1YuanState(CandyMachine candyMachine) {
        this.candyMachine = candyMachine;
    }

    @Override
    public void insert1Yuan() {
        // TODO Auto-generated method stub
        System.out.println("投币1元失败，糖果机内尚有1元未使用，请使用完后再投币");
    }

    @Override
    public void eject1Yuan() {
        // TODO Auto-generated method stub
        candyMachine.setState(candyMachine.getNo1YuanState());
        System.out.println("退回1元成功");
    }

    @Override
    public void turnCrank() {
        // TODO Auto-generated method stub
        System.out.println("转动曲柄成功，准备出售糖果");
        int winner = rnd.nextInt(10);
        if ((winner == 0) && (candyMachine.getCount() > 1)) {
            candyMachine.setState(candyMachine.getWinnerState());
        } else {
            candyMachine.setState(candyMachine.getSoldState());
        }
    }

    @Override
    public void dispense() {
        // TODO Auto-generated method stub
        System.out.println("发放糖果失败，请先转动曲柄");
    }

    public String toString() {
        return "[有1元]";
    }
}
