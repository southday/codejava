package southday.java.thinkinginjava.c14.s04.typeinfo;

/**
 * 
 * @author southday
 * @date 2018年10月24日
 */
public class RegisteredFactories {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            System.out.println(Part.createRandom());
    }
}
