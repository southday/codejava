package southday.java.thinkinginjava.c15.s04.s04;

import southday.java.thinkinginjava.c15.s03.Generator;

/**
 * 
 * @author southday
 * @date 2018年10月25日
 */
public class BasicGeneratorDemo {
    public static void main(String[] args) {
        // 类型参数推断
        Generator<CountedObject> gen = BasicGenerator.create(CountedObject.class);
        for (int i = 0; i < 5; i++)
            System.out.println(gen.next());
        
        System.out.println("----------------");
        // 第二种写法代码量明显更多
        Generator<CountedObject> gen2 = new BasicGenerator<CountedObject>(CountedObject.class);
        for (int i = 0; i < 5; i++)
            System.out.println(gen2.next());
    }
}
