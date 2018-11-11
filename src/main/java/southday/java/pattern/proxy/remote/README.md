#### 远程代理

需求：糖果厂总部想要监视分布于各地的糖果机的状态（位置、糖果个数、糖果机状态）

- 糖果机位置与数目
    + santafe.mightycandy.com 100
    + boulder.mightycandy.com 50
    + seattle.mightycandy.com 60

实现：

1. 糖果机提供方法：
- `getCount()`：返回糖果数目
- `getLocation()`：返回糖果机位置
- `getState()`：返回糖果机状态

2. 糖果厂总部建立监视器Monitor，`report()`远程调用各地糖果机的相应方法，生成报告

使用远程代理（java.rmi.*），Java RMI框架已经帮你完成了网络和I/O部分

1. 创建接口`CandyMachineRemote`
- `getCount()`：返回糖果数目
- `getLocation()`：返回糖果机位置
- `getState()`：返回糖果机状态

2. `CandyMachine`继承`UnicastRemoteObject`并实现`CandyMachineRemote`

3. 在`RMI registry`中注册
    1) 终端进入到.../YourProject/bin/目录下，运行命令`rmiregistry`
    2) 将各个地方的糖果机注册到RMI中，例：执行命令`java CandyMachineTestRegist seattle.mightycandy.com 60`

4. 创建监视器类`CandyMachineMonitor`，将`CandyMachineRemote`作为参数传入，以便`report()`调用生成报告

5. 最后运行`CandyMachineTestDrive`进行测试

---

结构：

```text
客户端 <-----> 客户端Stub <-----------> 服务端skeleton <--------> 服务对象candyMachine
   |               |                        |                        |
monitor     CandyMachineRemote对象      处理stub的请求          真正实现业务逻辑
                   |                                                 |
            通过Naming.lookup()获得                            被序列化后发送到客户端
```

注：Java5的RMI和动态代理搭配使用，动态代理产生stub，远程对象的stub是java.lang.reflect.Proxy实例（连同一个调用处理器），
它是自动产生的，来处理所有把客户的本地调用变成远程调用的细节。所以，你不再需要使用rmic，客户和远程对象沟通的一切都在幕后处理掉了。

