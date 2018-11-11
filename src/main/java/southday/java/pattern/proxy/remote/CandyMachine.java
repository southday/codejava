package southday.java.pattern.proxy.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 糖果机，相当于服务端（是真正的处理业务逻辑的对象）
 * @author southday
 * @date 2018年6月1日
 */
public class CandyMachine extends UnicastRemoteObject implements CandyMachineRemote {
    private int count = 0;
    private String location = null;
    
    public CandyMachine(String location, int count) throws RemoteException {
        this.location = location;
        this.count = count;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return count;
    }

    @Override
    public String getLocation() {
        // TODO Auto-generated method stub
        return location;
    }

    // 只是为了说明：CandyMachineRemote接口中定义的方法的返回值类型必须是原语类型或可序列化类型，实际测试时不使用
    @Override
    public State getState() {
        // TODO Auto-generated method stub
        return null;
    }

}
