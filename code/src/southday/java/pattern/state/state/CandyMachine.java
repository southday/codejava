package southday.java.pattern.state.state;

/**
 * 糖果机
 * @author southday
 * @date 2018年5月31日
 */
public class CandyMachine {
    private int count = 0;
    private State NO_1YUAN;
    private State HAS_1YUAN;
    private State SOLD;
    private State SOLD_OUT;
    private State WINNER;
    private State state = SOLD_OUT;
    
    public CandyMachine(int count) {
        NO_1YUAN = new No1YuanState(this);
        HAS_1YUAN = new Has1YuanState(this);
        SOLD = new SoldState(this);
        SOLD_OUT = new SoldOutState(this);
        WINNER = new WinnerState(this);
        this.count = count;
        if (this.count > 0) {
            this.state = NO_1YUAN;
        }
    }
    
    /**
     * 投币1元
     */
    public void insert1Yuan() {
        state.insert1Yuan();
    }
    
    /**
     * 退回1元
     */
    public void eject1Yuan() {
        state.eject1Yuan();
    }
    
    /**
     * 转动曲柄
     */
    public void turnCrank() {
        state.turnCrank();
        state.dispense();
    }
    
    /**
     * 设置状态，按道理State中的状态对象不应该被客户端获取到，这里为了表达清晰，不考虑程序安全性等
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }
    
    public State getNo1YuanState() {
        return this.NO_1YUAN;
    }
    
    public State getHas1YuanState() {
        return this.HAS_1YUAN;
    }
    
    public State getSoldState() {
        return this.SOLD;
    }
    
    public State getSoldOutState() {
        return this.SOLD_OUT;
    }
    
    public State getWinnerState() {
        return this.WINNER;
    }
    
    public int getCount() {
        return this.count;
    }
    
    /**
     * 发放1颗糖果
     */
    public void releaseOneCandy() {
        this.count--;
    }
    
    /**
     * 重填糖果
     * @param count
     */
    public void refill(int count) {
        this.count = count;
    }
    
    public String toString() {
        return "糖果机剩余" + this.count + "颗糖，处于" + this.state + "状态";
    }
}
