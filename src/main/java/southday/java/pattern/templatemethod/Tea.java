package southday.java.pattern.templatemethod;

public class Tea extends CaffeineBeverage {

    @Override
    public void brew() {
        // TODO Auto-generated method stub
        System.out.println("用沸水浸泡茶叶");
    }

    @Override
    public void addCondiments() {
        // TODO Auto-generated method stub
        System.out.println("添加柠檬");
    }
    
    @Override
    public void hook() {
        System.out.println("使用了hook： 这茶真好喝！");
    }

}
