package southday.java.basic.serializable; 

import java.io.Serializable;

/* 本例主要涵盖以下几个知识点：
 *  1) 当一个类可以被序列化时，它的所有子类也可以被序列化 (想想都知道，就不用代码验证了)
 *  2) 当一个对象被序列化时，只保存对象的非静态成员，不能保存任何成员方法或静态的成员变量
 *  3) 当一个对象的成员变量是一个对象时，那么这个这个成员变量(对象)的相应数据也会被序列化(保存)
 *     如果该成员变量(对象)是不可序列化的，则整个序列化过程会失败，并且抛出：NotSerializableException
 *  4) 反序列化时不会调用对象的任何构造函数，仅仅根据所报存的对象状态信息，在内存中重新构建对象
 * 对于(4)，请看FatherNoSerialSonSerial.java 最下面，有相关猜测
 * 
 * 直接运行程序就ok，(3)中抛出NotSerializableException 我没演示
 */

class SerialCar implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public static int price = 11;
    public SerialTyre tyre;
    
    public SerialCar() {
        System.out.println("SerialCar constructor is called -- default.");
    }
    
    public SerialCar(String name, SerialTyre tyre) {
        this.name = name;
        this.tyre = tyre;
        System.out.println("SerialCar constructor is called.");
    }
    
    @Override
    public String toString() {
        String info = "name: " + name + " | price: " + price + " | tyre.price: " + tyre.price;
        return info;
    }
}

class SerialTyre implements Serializable {
    private static final long serialVersionUID = 2L;
    public int price;
    
    public SerialTyre(int price) {
        this.price = price;
        System.out.println("SerialTyre constructor is called.");
    }
}

public class SerializableDemo0 {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/SerialCar.out";
        SerialCar car = new SerialCar("奥迪", new SerialTyre(3));
        
        System.out.println("序列化之前car的信息:");
        System.out.println(car.toString());
        SerializeUtil.serializeObject(car, filepath);
        
        System.out.println("\n序列化后，修改car的非静态成员变量name和静态成员变量price, \n"
                + "car.serName(\"奔驰\"); SerialCar.price = 100;");
        car.name = "奔驰";
        SerialCar.price = 100;
        System.out.println(car.toString());
        
        System.out.println("\n反序列化之后，返回的对象的信息:");
        SerialCar decar = (SerialCar) SerializeUtil.deserializeObject(filepath);
        System.out.println(decar.toString());
    }
}
