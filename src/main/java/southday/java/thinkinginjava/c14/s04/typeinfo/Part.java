package southday.java.thinkinginjava.c14.s04.typeinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import southday.java.thinkinginjava.c14.s04.typeinfo.factory.Factory;

/**
 * 注册工厂
 * @author southday
 * @date 2018年10月24日
 */
public class Part {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    static List<Factory<? extends Part>> partFactories = 
            new ArrayList<Factory<? extends Part>>();
    
    static {
        partFactories.add(new FuelFilter.Factory());
        partFactories.add(new AirFilter.Factory());
        partFactories.add(new CabinAirFilter.Factory());
        partFactories.add(new OilFilter.Factory());
        partFactories.add(new FanBelt.Factory());
        partFactories.add(new PowerSteeringBelt.Factory());
        partFactories.add(new GeneratorBelt.Factory());
    }
    
    private static Random rand = new Random(47);
    public static Part createRandom() {
        int n = rand.nextInt(partFactories.size());
        return partFactories.get(n).create();
    }
}
