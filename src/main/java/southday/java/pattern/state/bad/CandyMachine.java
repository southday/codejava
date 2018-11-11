package southday.java.pattern.state.bad;

/**
 * 糖果机
 * @author southday
 * @date 2018年5月31日
 */
public class CandyMachine {
    private int count = 0;
    private State state = State.SOLD_OUT;
    
    public CandyMachine(int count) {
        this.count = count;
        if (this.count > 0) {
            state = State.NO_1YUAN;
        }
    }
    
    private enum State {
        NO_1YUAN, HAS_1YUAN, SOLD, SOLD_OUT
    }
    
    /**
     * 投币1元
     */
    public void insert1Yuan() {
        if (state == State.NO_1YUAN) {
            state = State.HAS_1YUAN;
            System.out.println("投币1元成功");
        } else if (state == State.HAS_1YUAN) {
            System.out.println("投币1元失败，糖果机内尚有1元未使用，请使用完后再投币");
        } else if (state == State.SOLD) {
            System.out.println("投币1元失败，在出售糖果过程中不能投币");
        } else if (state == State.SOLD_OUT) {
            System.out.println("投币1元失败，糖果已售完");
        }
    }
    
    /**
     * 转动曲柄
     */
    public void turnCrank() {
        if (state == State.NO_1YUAN) {
            System.out.println("转动曲柄失败，请先投币1元");
        } else if (state == State.HAS_1YUAN) {
            state = State.SOLD;
            System.out.println("转动曲柄成功，准备出售糖果");
            dispense();
        } else if (state == State.SOLD) {
            System.out.println("转动曲柄失败，在出售糖果过程中禁止转动曲柄");
        } else if (state == State.SOLD_OUT) {
            System.out.println("转动曲柄失败，糖果已售完");
        }
    }
    
    /**
     * 退回1元
     */
    public void eject1Yuan() {
        if (state == State.NO_1YUAN) {
            System.out.println("退回1元失败，糖果机内无1元");
        } else if (state == State.HAS_1YUAN) {
            state = State.NO_1YUAN;
            System.out.println("退回1元成功");
        } else if (state == State.SOLD) {
            System.out.println("退回1元失败，在出售糖果过程中禁止退回1元");
        } else if (state == State.SOLD_OUT) {
            System.out.println("退回1元失败，糖果已售完，糖果机内无1元");
        }
    }
    
    /**
     * 发放糖果
     */
    public void dispense() {
        count--;
        if (count == 0) {
            state = State.SOLD;
            System.out.println("已发放1颗糖果，糖果售空");
        } else {
            state = State.NO_1YUAN;
            System.out.println("已发放1颗糖果");
        }
    }
    
    public int getCount() {
        return count;
    }
}
