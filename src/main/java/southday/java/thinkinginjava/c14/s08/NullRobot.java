package southday.java.thinkinginjava.c14.s08;

import java.lang.reflect.Proxy;

/**
 * 动态代理创建空对象-测试
 * @author southday
 * @date 2018年10月25日
 */
public class NullRobot {
    public static Robot newNullRobot(Class<? extends Robot> type) {
        /* 创建一个动态代理，代理类型是 NullRobot
         * 该代理类型要实现：Null和Robot接口
         * 被代理的是 type.class
         */
        return (Robot)Proxy.newProxyInstance(
                NullRobot.class.getClassLoader(),
                new Class[] {Null.class, Robot.class},
                new NullRobotProxyHandler(type));
    }
    
    public static void main(String[] args) {
        Robot[] bots = {
                new SnowRemovalRobot("SnowBot"),
                newNullRobot(SnowRemovalRobot.class)
        };
        /* NRobot 代理了 SnowRemovalRobot.class
         * 即：为SnowRemovalRobot类型创建了一个空对象
         */
        for (Robot r : bots)
            Robot.Test.test(r);
    }
}
