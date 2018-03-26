package southday.java.basic.string;
/*基本数据类型对象包装类
     数据类型             引用对象
  int       Integer
  float     Float
  double    Double
  boolean   Boolean
  char      Character
  short     Short
  long      Long
  byte      Byte
 
 基本数据类型对象包装类最常用的作用：
 就是用于   基本数据类型   和   字符串类型  之间的转换
————基本数据类型    转成     字符串
         （1） 基本数据类型+""
         （2） 基本数据类型.toString(基本数据类型值)：
     如:   Integer.toString(35)——把 35 转换成  字符串   "35"
————字符串      转成     基本数据类型
    （1）parseInt(String str)：将字符串参数作为有符号的     对应的基本数据类型   进行解析。
    （2）parseInt(String str,int radix)：使用第二个参数指定的   基数(进制），将字符串参数解析为    对应的基本数据类型。
 ——————基本数据类型包装类.parseXXX(str);  XXX是基本数据类型,如 :
       Double.parseDouble(str)   Float.parseFloat(str)
        除了Character类
    
十进制→其他进制→十进制：
     （1）toBinaryString();     toOctalString();    toHexString();
     （2）parseInt(String str,int radix)
 */

public class DataObj {
    private static void sop(String str) {
        System.out.println(str);
    }
    public static void main(String[] args) {
        Integer x = 7; //JDK 1.5以后才可以这样写，  将7自动装箱为Integer对象
        x = x+2;//自动拆箱(x.intValue())，将x变成int型，进行加法运算后，再进行装箱赋给x。
        
        Integer m = 129;//结果为 false，因为数值超过了byte范围
        Integer n = 129;
        sop("m==n : "+(m==n));
        
        Integer a = 127; //结果为true, 原因：   在新特性下，如果数值在byte范围内，且该数值已经存在，则不会开辟新的空间。
        Integer b = 127;//即：  a,b 指向了同一个Integer对象。   byte: -128~127       （节省内存）
        sop("a==b : "+(a==b));
        
        method();
    }
    public static void method() {
        //这里以 Integer为例
    
    //    sop("Integer MAX = "+Integer.MAX_VALUE);
        
        //将一个字符串转化为int型
        //sop("Integer = "+Integer.parseInt("23")*5);
        sop("x = "+Integer.parseInt("110",10)+"----(10)进制");
        for(int j=2; j<=16;j+= 6) {
            int x = Integer.parseInt("110",j);//  j 代表  j进制
            sop("x = "+x+"----("+j+")进制");
        }
        sop("x = 3c(H) = "+Integer.parseInt("3c",16)+"(D)");
        
        Integer m = new Integer(12);//构造一个新分配的 Integer 对象，它表示指定的 int 值
        Integer n = new Integer("12");//构造一个新分配的 Integer 对象，它表示 String 参数所指示的 int 值。
                                      //使用与 parseInt 方法（对基数为 10 的值）相同的方式将该字符串转换成 int 值。 
        sop("m == n :"+(m==n));
        sop("m.equal(n) :"+m.equals(n)); //Integer类对equal()方法进行了复写，只要对象里的参数相同，则为true
    }
}
