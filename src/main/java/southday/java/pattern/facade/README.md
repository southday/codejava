### 外观模式

#### 需求

周末想在家里看一场电影，无奈每次都要进行很多操作才能开始看电影：
- 打开DVD
- 打开投影仪
- 打开空调
- 炸爆米花
- ...等等

为了讲解方便，我只涉及了DVD、爆米花相关操作。
- PopcornPopper (爆米花机)：on()、off()、pop()等操作
- DvdPlayer (DVD播放器)：on()、off()、play()、stop()等操作

我想让看电影的这个准备过程简单一些，不用自己去做那么多操作

#### 设计

外观模式：提供了一个统一的接口，用来访问子系统中的一群接口。外观定义了一个高层接口，让子系统更容易使用。

1. 定义外观`HomeTheaterFacade`，将`PopcornPopper`和`DvdPlayer`对象传入

2. 在外观中定义更加简洁的方法，如：`watchMovie()`、`endMovie()`等

3. `watchMovie()`等方法中内部去调用各个组件的相关方法，而客户只需调用Facade的`watchMovie()`方法，
从而简化了对外的接口

4. 注意：例子中的`PopcornPopper`、`DvdPlayer`和`HomeTheaterFacade`我是以类的形式来实现的，而实际中更多会以接口方式来呈现

5. 通过`FacadeTestDrive`来测试外观模式 