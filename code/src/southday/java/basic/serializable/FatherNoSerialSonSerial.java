package southday.java.basic.serializable;

import java.io.Serializable;

/* 本例旨在研究：父类没有实现Serializable，而子类实现Serializable的情况下，
 * 子类对象进行序列化和反序列化，子类中继承自父类的成员变量会怎么样？
 * 
 * 1) 父类中有 无参数的构造函数,即： public SerialFather() {...} 
 * 输出结果：
        SerialFather constructor is called.
        before serialize: cname = 李朝喜, ename = Lichaoxi
        serialize object over
   -->  SerialFather constructor is called.
        deserialize object over
        after deserialize: cname = null, ename = Lichaoxi
         发现的问题：
    a) 反序列化得到的cname为null，因为它是父类的成员变量，而父类没有实现Serializable，这种解释有点牵强
    b) 反序列化时会调用 父类的无参数的构造函数，之前看到文章不是说反序列化不会调用对象的任何构造函数吗？
    c) 如果父类中的 cname 有默认值（假设为 cname = 冬瓜先生），则反序列化后的 cname = 冬瓜先生
    d) 若在父类无参数的构造函数中有对 cname 赋值的操作（假设是 cname = "南瓜先生"），则反序列化后得到的cname = 南瓜先生
 * 
 * 2) 父类中无 无参数的构造函数
 * 输出结果：
      SerialFather constructor is called.
      before serialize: cname = 李朝喜, ename = Lichaoxi
      serialize object over
      java.io.InvalidClassException: serializable.SerialSon; no valid constructor
      ...
    发现的问题：
    a) 当父类中没有 无参数的构造函数时，就会报出 java.io.InvalidClassException 异常，不是说好了不调用的吗？
    这让我想到了 SerialVersionUIDWorking.java 中的报类似错误的情况，但是二者之间也有不同，
      SerialVersionUIDWorking.java 中报错是因为：字节流中的class和本地class 的UID不一致;
      FatherNoSerialSonSerial.java 中报错是因为：no valid constructor
    也就是说 程序在反序列化时，没有找到有效的构造函数，这个有效的构造函数是指 SerialFather类的无参数的构造函数
    
 * [请看最下面，有我的猜测]
 * @author  southday
 * @Date    2016-04-06
 */

class SerialFather {
    public String cname; // = "冬瓜先生"; // 中文名
    
    // 父类无参构造函数
//    public SerialFather() {
////        cname = "南瓜先生";
//        System.out.println("SerialFather constructor is called.");
//    }
    
    public SerialFather(String cname) {
        this.cname = cname;
        System.out.println("SerialFather constructor is called.");
    }
}

class SerialSon extends SerialFather implements Serializable {
    private static final long serialVersionUID = 1L;
    public String ename = "coco"; // 英文名
    
    public SerialSon(String cname) {
        super(cname);
        System.out.println("SerialSon constructor is called.");
    }
    
    public SerialSon(String cname, String ename) {
        super(cname);
        this.ename = ename;
        System.out.println("SerialSon constructor is called.");
    }
    
    @Override
    public String toString() {
        return "cname = " + cname + ", ename = " + ename;
    }
}

public class FatherNoSerialSonSerial {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/FatherNoSerialSonSerial.out";
        SerialSon son = new SerialSon("李朝喜", "Lichaoxi");
        System.out.println("before serialize: " + son.toString());
        // write
        SerializeUtil.serializeObject(son, filepath);
        // read
        SerialSon son_copy = (SerialSon) SerializeUtil.deserializeObject(filepath);
        System.out.println("after deserialize: " + son_copy.toString());
    }
}

/* 1) 当父类没有实现Serializabale接口，并且没有实现无参数的构造函数时，
 * 在反序列化时，"java内部程序"(我也不知道该怎么描述)会想到:
 *   可能我们正在反序列化的这个对象中，会有来自父类的成员变量，而这些成员变量没有被序列化到本地保存。
 * 那怎么办呢？
 *  只能赋给这些来自父类的成员变量系统的默认值了，可问题又出现了：
 * 本地文件中并没有关于 父类成员变量的记录，反序列时我们如何获得这些变量？？
 *  对的，我们只有通过父类的构造函数才能获得这些变量，正好java中会为类提供默认的构造函数（即：无参的构造函数），
 *  我们就可以通过这个 无参的父类构造函数 来获得父类成员变量，进而作为子类对象的成员变量
 * 所以，当我们没有为父类提供 无参的构造函数时，反序列化过程就会报错
 * 
 * 2) 在之前的总结中说道，反序列化不会调用对象任何的构造函数，这里的“对象任何的构造函数"指的是本类对象的任何构造函数，
 * 并不包含父类对象的构造函数
 *    想想看，之前的例子，只有一个类 A ，实现了Serializable接口，确确实实，在反序列化时没有调用类A中任何构造函数，
 *  但是，Object类是任何类的父类，这个还记得吧。所以，我们猜测在反序列化A类对象时，其实之间调用了Object中无参的构造函数，
 *  由于我不能修改Object类中的源码，所以这还只是猜测，但这个猜测符合我上面的猜想
 * [这里补充一点：若 B extends A, C extends B, 在序列化和反序列化C时，会进行级联操作，即 B 和 A都会被作用，直到Object类]
 * 
 * 3) 当父类中实现了 Serializable 接口时，情况就变得不一样了，
 *  因为父类中的成员变量可以被序列化，那么本地文件中就已经记录了父类中的成员变量，就不用再通过父类的构造函数new一个父类
 *  对象才能获得这些成员变量了
 *  所以，此时反序列化 子类对象时，不会调用 父类和子类任何的构造函数 （这点已经过测试）
 * 
 * 以上纯属个人猜想，有些没有经过实验验证，只是符合一定客观事实
 */
