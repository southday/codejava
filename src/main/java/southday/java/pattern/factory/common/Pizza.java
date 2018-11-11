package southday.java.pattern.factory.common;

/**
 * 披萨
 * @author southday
 * @date 2017年2月19日
 */
public abstract class Pizza {
    
    public final void bake() {
        System.out.println("bake...");
    }
    
    public final void cut() {
        System.out.println("cut...");
    }
    
    public final void box() {
        System.out.println("box...");
    }
}
