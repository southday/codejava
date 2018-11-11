package southday.java.basic.serializable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* 本例旨在讲解：对于实现 Externalizable接口的子类，父类对其的影响
 * 
 * [ExternalizableWithFatherDemo0.java] 中描述 父类没有实现 Externalizable 接口的情况
 * [ExternalizableWithFatherDemo1.java] 中描述 父类实现了 Externalizable 接口的情况
 * 
 * 当 父类中没有实现 Externalizable接口时:
 * 1) 我们得在子类中实现对 父类成员变量 的序列化
 * 
 * 2) 子类中也没有实现对 父类成员变量 的序列化的情况时，会出现什么问题呢？
 * []: 不会有什么问题，只要你的 writeExternal() 和 readExternal() 操作是一致的就OK，
 * 此外，你会发现反序列化后得到的子类对象中的 父类成员变量 的值是 父类无参构造函数对 该变量 作用下的值，
 * 因为在反序列化时，不仅调用了子类无参构造函数，还调用了父类无参构造函数
 * 
 * @autor southday
 * @Date 2016-04-08
 */

class ExternalFather0 {
    public String cname = "冬瓜先生"; // 中文名
    
    public ExternalFather0() {
        cname = "南瓜先生";
        System.out.println("ExternalFather0() constructor is called.");
    }
    
    public ExternalFather0(String cname) {
        this.cname = cname;
        System.out.println("ExternalFather0(String cname) constructor is called.");
    }
}

class ExternalSon0 extends ExternalFather0 implements Externalizable {
    private static final long serialVersionUID = 1L;
    public String ename = "coco"; // 英文名
    
    public ExternalSon0() {
        System.out.println("ExternalSon0() constructor is called.");
    }
    
    public ExternalSon0(String cname, String ename) {
        this.cname = cname;
        this.ename = ename;
        System.out.println("ExternalSon0(String cname, String ename) constructor is called.");
    }
    
    @Override
    public void writeExternal(ObjectOutput outs) throws IOException {
        // 父类没有实现Externalizable接口时，子类中需要实现对 父类中的成员变量 的序列化
//        outs.writeUTF(cname);
        outs.writeUTF(ename);
    }
    
    @Override
    public void readExternal(ObjectInput ins) throws IOException, ClassNotFoundException {
        // 父类没有实现Externalizable接口时，子类中需要实现对 父类中的成员变量 的反序列化（这个操作应该是可选的）
//        cname = ins.readUTF();
        ename = ins.readUTF();
    }
    
    @Override
    public String toString() {
        return "cname = " + cname + ", ename = " + ename;
    }
}

public class ExternalizableWithFatherDemo0 {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/ExternalizableWithFatherDemo0.out";
        ExternalSon0 es = new ExternalSon0("李朝喜", "LiChaoxi");
        System.out.println("before serialize: " + es.toString());
        // write
        SerializeUtil.serializeObject(es, filepath);
        // read
        ExternalSon0 es_copy = (ExternalSon0) SerializeUtil.deserializeObject(filepath);
        System.out.println("after deserialize: " + es_copy.toString());
    }
}
