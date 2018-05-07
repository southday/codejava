package southday.java.basic.thread.jcip.chapter3;

/**
 * 程序清单 3-6 使内部的可变状态逸出（不要这么做）<br>
 * 1.发布（一个对象）：使对象能够在当前作用域之外的代码中使用 <br>
 * 2.逸出：某个不应该发布的对象被发布
 * @author southday
 * @date 2018年4月14日
 */
public class UnsafeStates {
    private String[] states = new String[] {"AK", "AL"};
    private int number = 0;
    
    public String[] getStates() {
        return states;
    }
    
    public int getNumber() {
        return number;
    }
}

/* 上述UnsafeStates对states的发布，使数组states逸出了它所在的作用域，
 * 导致任何调用者都能修改这个数组的内容。
 * 
 * 封装能够使得对程序的正确性进行分析变得可能，并使得无意中破坏设计约束条件变得更难
 */
class OtherClass001 {
    public static void main(String[] args) {
        UnsafeStates us = new UnsafeStates();
        String[] sts = us.getStates(); // 对于数组，传递的是引用
        sts[0] = "AP";
        System.out.println(us.getStates()[0]); // 输出 AP
        
        int n = us.getNumber(); // 对于基本类型变量，传递的是值
        n = 10;
        System.out.println(us.getNumber()); // 输出 0
    }
}
