package southday.java.basic.serializable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/* 本例主要介绍：
 *  当子类和父类都实现了 Externalizable 接口时，父类中需实现 对其成员变量的序列化和反序列化
 * 
 * 若父类中没有实现 对其成员变量的 序列化和反序列化 会怎么样？
 * []:不会怎么样，你也可以在子类中实现; 若子类中不实现的话，无非时 父类成员变量 不会被持久化到本地保存起来，
 * 在反序列化时，得到的值也是 系统默认值 或 父类成员变量默认值 或 无参构造函数下对该变量的赋值
 * 
 * [需要注意的是]:
 *   在子类的 writeExternal() 和 readExternal() 中要分别在方法的第一行中（非注释）写上
 *     super.writeExternal(outs);
 *     super.readExternal(ins);
 *  表示调用父类的 序列化 和 反序列化 操作
 *  
 * @author southday
 * @Date 2016-04-08
 */
class ExternalFather1 implements Externalizable {
    private static final long serialVersionUID = 1L;
    public String cname = "冬瓜先生"; // 中文名
    
    public ExternalFather1() {
        cname = "南瓜先生";
        System.out.println("ExternalFather1() constructor is called.");
    }
    
    public ExternalFather1(String cname) {
        this.cname = cname;
        System.out.println("ExternalFather1(String cname) constructor is called.");
    }
    
    @Override
    public void writeExternal(ObjectOutput outs) throws IOException {
        // 父类需要实现对其成员变量的序列化
        outs.writeUTF(cname);
        System.out.println("[Father] writeExternal() is called.");
    }
    
    @Override
    public void readExternal(ObjectInput ins) throws IOException, ClassNotFoundException {
        // 父类需要实现对其成员变量的反序列化
        cname = ins.readUTF();
        System.out.println("[Father] readExternal() is called.");
    }
}

class ExternalSon1 extends ExternalFather1 {
    private static final long serialVersionUID = 1L;
    public String ename = "coco"; // 英文名
    
    public ExternalSon1() {
        System.out.println("ExternalSon1() constructor is called.");
    }
    
    public ExternalSon1(String cname, String ename) {
        this.cname = cname;
        this.ename = ename;
        System.out.println("ExternalSon1(String cname, String ename) constructor is called.");
    }
    
    @Override
    public void writeExternal(ObjectOutput outs) throws IOException {
        super.writeExternal(outs);
        outs.writeUTF(ename);
        System.out.println("[Son] writeExternal() is called.");
    }
    
    @Override
    public void readExternal(ObjectInput ins) throws IOException, ClassNotFoundException {
        super.readExternal(ins);
        ename = ins.readUTF();
        System.out.println("[Son] readExternal() is called.");
    }
    
    @Override
    public String toString() {
        return "cname = " + cname + ", ename = " + ename;
    }
}

public class ExternalizableWithFatherDemo1 {
    public static void main(String[] args) {
        String filepath = "/home/coco/Desktop/Java/ExternalizableWithFatherDemo1.out";
        ExternalSon1 es = new ExternalSon1("李朝喜", "LiChaoxi");
        System.out.println("before serialize: " + es.toString());
        // write
        SerializeUtil.serializeObject(es, filepath);
        // read
        ExternalSon1 es_copy = (ExternalSon1) SerializeUtil.deserializeObject(filepath);
        System.out.println("after deserialize: " + es_copy.toString());
    }
}
