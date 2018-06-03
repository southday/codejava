package southday.java.pattern.templatemethod;

public class Coffee extends CaffeineBeverage {

    @Override
    public void brew() {
        // TODO Auto-generated method stub
        System.out.println("用沸水冲泡咖啡");
    }

    @Override
    public void addCondiments() {
        // TODO Auto-generated method stub
        System.out.println("添加糖和牛奶");
    }

}
