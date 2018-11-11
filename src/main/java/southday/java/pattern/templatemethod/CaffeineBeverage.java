package southday.java.pattern.templatemethod;

/**
 * 咖啡因饮品
 * @author southday
 * @date 2018年6月3日
 */
public abstract class CaffeineBeverage {
    
    // 使用final修饰，防止算法结构被子类修改
    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        addCondiments();
        // 钩子的形式不当如此，只要可实现类似效果的都是钩子
        hook();
    }
    
    /**
     * 冲泡
     */
    public abstract void brew();
    
    /**
     * 添加辅料
     */
    public abstract void addCondiments();
    
    public void boilWater() {
        System.out.println("把水煮沸");
    }
    
    public void pourInCup() {
        System.out.println("把饮料倒入杯子");
    }
    
    // 钩子
    public void hook() {
        
    }
}
