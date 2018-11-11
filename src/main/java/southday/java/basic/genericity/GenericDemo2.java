package southday.java.basic.genericity;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * ? 叫通配符，也可以理解为占位符
 * 泛型的限定：
 * ? extends E: 可以接收E类型或者E的子类型，这是上限定
 * ? super E: 可以接收E类型或者E的父类型，这是下限定
 */
public class GenericDemo2 {
    public static void main(String[] args) {
        /*ArrayList<String> as = new ArrayList<String>();
        as.add("haha");
        as.add("dododo");
        as.add("yoyoy");
        printColl_2(as);
        
        ArrayList<Integer> ai = new ArrayList<Integer>();
        ai.add(5);
        ai.add(6);
        ai.add(8);
        printColl_2(ai);*/
        
        //关于泛型限定
        ArrayList<Animal> aa = new ArrayList<Animal>();
        aa.add(new Animal("animal--1"));
        aa.add(new Animal("animal--2"));
        aa.add(new Animal("animal--3"));
        
        ArrayList<Bird> ab = new ArrayList<Bird>();
        ab.add(new Bird("Bird--1"));
        ab.add(new Bird("Bird--2"));
        ab.add(new Bird("Bird--3"));
        
        /* 如果printColl_3 定义为： public static void printColl_3(ArrayList<Animal> al){}
         * 这时如果调用 printColl_3(ab)会出错，因为该语句内部类似于：
         *         ArrayList<Animal> al = new ArrayList<Bird>();
         * 虽然说  Bird 是  Animal 的子类，但是你在声明的时候说：
         * （1）ArrayList<Animal> 我的ArrayList是用来关动物的，
         * （2）然而你实际是 new ArrayList<Bird>，就是说你的ArrayList实际只相当与一个鸟巢，只能来关鸟，
         * （3）别的动物 dog, pig能进你这个鸟巢生活？？ 这显然有错误
         * 
         * 使用泛型类型限定： ArrayList<? extends Animal>就可以避免这个问题
         */
        printColl_3(ab);
        printColl_3(aa);
    }
    
    public static <T> void printColl_1(ArrayList<T> al) {
        Iterator<T> it = al.iterator();
        while(it.hasNext()) {
            T t = it.next();    //这里的T是具体类型，可以操作，而如果用通配符?，则不可以这么写： ? t = it.next();
            System.out.println(t);
        }
        System.out.println("--------------------");
    }
    
    //不明确类型时，可用<?>来表示，?是通配符
    //它与上面定义的<T>泛型方法的区别是：T是具体类型，可以操作如： T t = it.next(); ? 则不能
    public static void printColl_2(ArrayList<?> al) {
        Iterator<?> it = al.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
            //利用泛型，会导致不能使用对象中的特有方法，如输出 it.next().length()是不行的，因为Integer对象中无该方法。
        }
        System.out.println("--------------------");
    }
    
    //关于泛型的类型限定： ArrayList<? extends Animal>，限定了只能是Animal及其子类
    //下限定：ArrayList<? super Bird>， 上限定：ArrayList<? extends Animal>
    public static void printColl_3(ArrayList<? extends Animal> al) {
        Iterator<? extends Animal> it = al.iterator();
        while(it.hasNext()) {    //如果用? super Bird,则需要向上转型为Animal
            //System.out.println(((Animal) it.next()).getName()); 针对 ? super Bird，但局限于只能调用Animal中的方法
            System.out.println(it.next().getName()); //针对 ? extends Animal，这里的getName()其实就Animal类中的方法
        }
        System.out.println("--------------------");
    }
}

//讲解：关于泛型的类型限定
class Animal {
    private String name;

    Animal(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

class Bird extends Animal {
    Bird(String name) {
        super(name);
    }
}
