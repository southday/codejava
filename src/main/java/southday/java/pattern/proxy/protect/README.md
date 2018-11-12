#### 保护代理

基于权限控制对资源的访问

##### 需求

一个在线社交系统
+ 用户可以设置和获取自己的基本信息，但是不能给自己评分（Hot or Not）
+ 用户可以获取他人的基本信息（但不能设置），用户可以给他人评分（Hot or Not）


##### 设计

1. 定义`PersonBean`接口，包括各种getter、setter方法
2. 定义`PersonBeanImpl`类，实现`PersonBean`接口
3. 创建`InvocationHandler`，用于控制对`PersonBean`对象的访问
    - `OwnerInvocationHandler`：控制用户对自己信息的访问
    - `NonOwnerInvocationHandler`：控制用户对他人信息的访问
4. 通过`Proxy.newProxyInstance()`来创建动态代理（记为对象proxy，类型为PersonBean），
此后对`PersonBean`中方法的访问实际是通过proxy来进行，而其内部则是通过`InvocationHandler`来控制访问的
5. 运行`MatchMakingTestDrive`来测试保护代理模式