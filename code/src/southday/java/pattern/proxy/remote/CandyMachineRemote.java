package southday.java.pattern.proxy.remote;

import java.rmi.Remote;

/**
 * 糖果机远程对象接口
 * @author southday
 * @date 2018年6月1日
 */
public interface CandyMachineRemote extends Remote {
    // 注意：返回值类型必须是原语类型或可序列化类型
    
    /**
     * 返回糖果数目
     * @return
     */
    int getCount();
    
    /**
     * 返回糖果机位置
     * @return
     */
    String getLocation();
    
    /**
     * 返回糖果机状态
     * @return
     */
    State getState();
    
}
