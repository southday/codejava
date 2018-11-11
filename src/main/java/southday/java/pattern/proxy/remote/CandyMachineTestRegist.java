package southday.java.pattern.proxy.remote;

import java.rmi.Naming;

/**
 * 糖果机注册程序
 * @author southday
 * @date 2018年6月1日
 */
public class CandyMachineTestRegist {
    public static void main(String[] args) {
        // args[]: location count
        // 命令行调用：>java CandyMachineRegister seattle.mightycandy.com 60`
        try {
            int count = Integer.parseInt(args[1]);
            CandyMachineRemote candyMachine = new CandyMachine(args[0], count);
            /* 由于我没有注册：seattle.mightycandy.com这个域名，也没真正建立服务器，所以代码示例是这样写，但实际测试不能正常运行
             * 如果想要测试通过，可以把下面的代码换成：
             * Naming.rebind("candymachine", candyMachine);
             * 然后客户端：Naming.lookup("rmi://127.0.0.1/candymachine");
             */
            Naming.rebind("//" + args[0] + "/candymachine", candyMachine);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
