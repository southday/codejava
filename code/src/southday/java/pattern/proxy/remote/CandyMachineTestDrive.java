package southday.java.pattern.proxy.remote;

import java.rmi.Naming;

/**
 * 糖果机远程代理监视测试
 * @author southday
 * @date 2018年6月1日
 */
public class CandyMachineTestDrive {
    public static void main(String[] args) {
        String[] locations = {"rmi://santafe.mightycandy.com/candymachine",
                              "rmi://boulder.mightycandy.com/candymachine",
                              "rmi://seattle.mightycandy.com/candymachine"};
        
        CandyMachineMonitor[] monitors = new CandyMachineMonitor[locations.length];
        
        for (int i = 0; i < locations.length; i++) {
            try {
                // RMI通过网络、I/O、序列化与反序列化将远程的candyMachine对象"返回到"本地中
                CandyMachineRemote machine = (CandyMachineRemote) Naming.lookup(locations[i]);
                monitors[i] = new CandyMachineMonitor(machine);
                System.out.println(monitors[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 0; i < monitors.length; i++) {
            monitors[i].report();
        }
    }

}
