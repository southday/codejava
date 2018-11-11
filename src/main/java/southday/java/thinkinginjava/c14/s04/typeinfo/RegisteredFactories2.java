package southday.java.thinkinginjava.c14.s04.typeinfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 练习14：不使用显示的工厂，而是将类对存储到List中，并使用newInstance()来创建对象
 * @author southday
 * @date 2018年10月24日
 */
public class RegisteredFactories2 {
    private static final List<Class<? extends Part>> allTypes = 
            Collections.unmodifiableList(Arrays.asList(
                    AirFilter.class, Belt.class, CabinAirFilter.class,
                    FuelFilter.class, FanBelt.class, GeneratorBelt.class,
                    PowerSteeringBelt.class, OilFilter.class));
    
    private static Random rand = new Random(47);
    public static Part createRandom() {
        int n = rand.nextInt(allTypes.size());
        Part part = null;
        try {
            part = allTypes.get(n).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return part;
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            System.out.println(createRandom());
    }
}
