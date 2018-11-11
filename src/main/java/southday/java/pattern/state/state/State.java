package southday.java.pattern.state.state;

/**
 * 状态接口
 * @author southday
 * @date 2018年5月31日
 */
public interface State {
    /**
     * 投币1元
     */
    void insert1Yuan();
    
    /**
     * 退回1元
     */
    void eject1Yuan();
    
    /**
     * 转动曲柄
     */
    void turnCrank();
    
    /**
     * 发放糖果
     */
    void dispense();
}