package southday.java.basic.genericity;

/*
 * 什么时候定义泛型类？
 * 【】：当类中要操作的【引用数据类型】不确定的时候，早期定义Object来完成扩展，现在定义泛型来完成扩展。
 * 
 * 【】：泛型类定义的泛型在整个类中有效，如果被方法使用，泛型类中的对象明确要操作的具体类型后，所有方法要操作的类型就已经固定了。
 * 为了让不同方法可以操作不同类型，而且类型还不确定，那么可以将泛型定义在方法上。
 * 列子： public <T> void show() {}
 * 
 * 【】：静态方法不可以访问类上定义的泛型，如果静态方法操纵的应用数据类型不确定，可以将泛型定义在静态方法上。
 * 定义方法： public static <T> void show() {}，注意<T>是在函数返回类型void前面，而不是在static前面
 * 
 * 【】：此外，泛型也可以定义在接口上，比如：
 * Interface B<T> { public void show(T t){}}
 * 而子类要实现接口B，则可以写为：
 *         （1）子类明确引用类型：class A<String> implements B<String>{ public void show(String t){}}
 *         （2）子类不明确引用类型：class A<T> implements B<T> { public void show(T t){}}，让创建类对象者去明确就OK
 */

class Basketball {
    String type = "Basketball";
    public String toString() {
        return type;
    }
    
    public void showBasketball() {
        System.out.println("篮球");
    }
}

class Soccerball {
    String type = "Soccerball";    
    public String toString() {
        return type;
    }
    
    public void showSoccerball() {
        System.out.println("足球");
    }
}

//泛型类的定义！！
class Information<T> {
    private T objType;
    
    public void setObject(T objType) {
        this.objType = objType;
    }
    
    public T getObject() {
        System.out.println(this.objType.toString());
        return this.objType;
    }
}

//泛型定义在方法上！！—— 泛型可以同时定义在类上和方法上
class NewInformation<T> {
    public void show(T t) {    //这里的T与类中的T一致
        System.out.println("show: " + t);
    }
    
    //传什么类型的引用进来，就输出什么类型的值
    @SuppressWarnings("hiding")
    public <T> void print(T t) { //这里的T是方法中的T，与类上的T无关系
        System.out.println("print: " + t);
    }
    
    //定义静态方法（含泛型）
    public static <T> void speak(T t) {
        System.out.println("speak: " + t);
    }
    /*
    如果不包含泛型而这样写的话，就会出错误，因为静态方法是在类加载时就存在的，而(T t)中的T是在创建对象时才添加的
    public static void speak(T t) {
        System.out.println("speak: " + t);
    }
    */
    
}

public class DefGenericClassDemo {
    public static void main(String[] args) {
        Information<Basketball> basketball = new Information<Basketball>();
        basketball.setObject(new Basketball());
//        basketball.setObject(new Soccerball()); //使用泛型后，让程序出错出现在编译阶段
        basketball.getObject();
        
        NewInformation<String> nif = new NewInformation<String>();
        nif.show("hahaha");    //String类型
//        nif.show(5); //这条语句会报错，因为创建nif对象时，T为String,而show(T t)方法中的T与类上的T是一致的，为String
        nif.print("heiheihei"); //String
        nif.print(5);//Integer类型，自动装箱，将数字5装箱为Integer对象，就相当于 new Integer(5)
        NewInformation.speak("yoyoyo");
        NewInformation.speak(7);
    }
}
