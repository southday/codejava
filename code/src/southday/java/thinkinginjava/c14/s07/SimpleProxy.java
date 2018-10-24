package southday.java.thinkinginjava.c14.s07;

/**
 * 简单代理模式
 * @author southday
 * @date 2018年10月24日
 */
public class SimpleProxy implements Interface {
    private Interface proxied;
    
    public SimpleProxy(Interface proxied) {
        this.proxied = proxied;
    }
    
    @Override
    public void doSomething() {
        /* 当你想添加一些额外的操作，又不想改变原来的类方法时，就可以使用代理
         * 比如：度量 proixed.doSomething()被调用的次数，就可以通过代理来实现
         */
        System.out.println("SimpleProxy doSomething");
        proxied.doSomething();
    }

    @Override
    public void somethingElse(String arg) {
        // TODO Auto-generated method stub
        System.out.println("SimpleProxy somethingElse");
        proxied.somethingElse(arg);
    }

}
