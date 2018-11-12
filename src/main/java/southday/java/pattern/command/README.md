### 命令模式

#### 需求

1. 现有两件电器
    - 电灯(Light)：开(on)、关(off)
    - 电视(TV)：开(on)、关(off)、换台(changeChannel)

2. 需要制作可以统一控制的遥控器(RemoteControl)
    - 有2个插槽(用于)：电灯、电视
    - 每个插槽有2个按钮：on、off

3. 将来可能有更多电器的控制需要集成到该遥控器，而且每种电器的操作可能不同

#### 设计

命令模式：将“请求”封装成对象，以便使用不同的请求、队列或者日志来参数化其他对象。命令模式也支持撤销操作。

1. 定义接口`Command`，包含方法`execute()`
2. 创建电灯类(Light)和电视类(TV)，并实现相应的操作：on、off等
3. 创建所需`命令对象类`(实现`Command`接口)，如：`LightOnCommand`表示`电灯开`命令对象类，并将实际执行操作的`Light`类对象作为参数传入
4. 当调用者按下`电灯开`的按钮时，`LightOnCommand`对象调用`execute()`方法，而该方法将实现委托给内置`Light对象`的相应操作`on()`

#### 测试

1. 创建遥控器类`RemoteControl`，将各个命令对象添加到插槽中
2. 通过`RemoteControlTestDrive`来测试命令模式下的遥控器