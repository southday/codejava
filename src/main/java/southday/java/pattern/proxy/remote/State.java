package southday.java.pattern.proxy.remote;

import java.io.Serializable;

/**
 * 糖果机状态
 * @author southday
 * @date 2018年6月1日
 */
public interface State extends Serializable {
    // 注意：在CandyMachineRemote中定义的方法返回类型必须是：原语类型或可序列化类型
    // 所以这里State 继承了 Serializable
}
