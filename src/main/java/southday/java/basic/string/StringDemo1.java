package southday.java.basic.string;
/*
String 类使用于描述字符串事物：
那么他就提供了多个方法对字符串进行操作。

常见的操作有哪些？
"abcd"
1.获取
  1.1字符串中包含的字符数（字符串的长度）——int length();**获取长度**
  1.2根据位置获取位置上的某个字符——char charAt(int index)
  1.3根据字符获取该字符在字符串中的位置——
      ①int indexOf(int ch) （返回的是ch在字符串中第一次出现的位置，  接收的是ASCII码）
      ②int indexOf(int ch, int fromIndex) ：从fromIndex指定位置开始获取ch在字符串中出现的位置。
      ①int indexOf(String str) // int indexOf(String str, int fromIndex) 与int ch类似
      **lastIndexOf(int ch)反向索引，从右开始向坐查找，角标不变
2.判断
  2.1字符串中是否包含某一个字符串
     boolean contains(CharSequence s)
     特殊之处：indexOf(str):可以索引str第一次出现位置，如果返回-1，表示str不在字符串中存在。
     所以也可以用于对指定字符串的判断是否包含，contains 和 indexOf都可以做这样事情
     if(str.indexOf("haha")；————而且该方法既可以判断，又可以获取出现的位置；
  2.2字符串中是否有内容
     boolean isEmpty()——原理就是判断长度是否为 0；"" null 有区别的
  2.3字符串是否是以指定内容开头
     boolean startsWith(String prefix)
  2.4字符串是以什么东西结尾
     boolean endsWith(String prefix)
  2.5判断字符内容是否相同————覆写了Object中的equals方法
     boolean equals(String prefix)
  2.6判断内容是否相同，并且忽略大小写
     boolean equalsIgnoreCase() ————就像现在需要输入的各种  验证码（有些就忽略大小写）
     
3.转换
  3.1将字符数组转成字符串
             构造函数：String(char[])
           String(char[],offset,count)——将字符数组中的一部分转成字符串
            静态方法： static String copyValueOf(char[] data);
           static String copyValueOf(char[] data, int offset, int count) 
           ——————————返回指定数组中(数组的初始偏移量（offest），长度（count）0表示该字符序列的 String。 
           
           static String valueOf(char[] data)  （功能类似与上面的）
           static String valueOf(char[] data, int offset, int count)            
  3.2将字符串转成字 符 数组
     char[] toCharArray();
  3.3将字 节 数组转成字符串
     String(byte[])
     String(byte[],offset,count)——将字节数组中的一部分转成字符串
  3.4将字符串转成字 节 数组    
     byte[] getBytes();
  3.5将基本数据类型转换为字符串
     static String valueOf(int)
     static String valueOf(double)

 字符串和字节数组在转换过程中，是可以指定编码表的。
 */
class StringDemo1 {
    public static void method_str1(){
        String s1 = "abc";//s1是一个类类型变量，"abc"是一个对象
                            //字符串最大的特点：一旦被初始化就不能被改变
        s1 = "kk"; //输出 kk 是因为  s1刚才指向"abc" 对象，现在指向"kk"
    
        String s2 = new String("kk");
        
        System.out.println(s1==s2);
        System.out.println(s1.equals(s2));//String 类中覆写了Object类中的equals()方法，
                                          //该方法用来判断字符串是否相同；
        /*s1 和 s2有什么区别？
          s1 在内存中有一个对象；
          s2 在内存中有两个对象
        */
    
    }
    public static void method_str2(){
        String str = "abcdefgdhced";
    
        //获取字符串长度
        sop(str.length());
        
        //获取字符串中某个位置对应的字符
        sop(str.charAt(5));//如果()中的数字大于字符串长度，则抛出角标越界异常 StringIndexOutOfBoundsException
        
        //获取某个字符所在字符串中的位置
        sop(str.indexOf('l'));//如果没有找到，返回-1
        sop(str.indexOf('d', 4));
        
        sop(str.indexOf("cde"));
        System.out.println("/////////////////////////////////");
        
        sop(str.startsWith("abd"));
        sop(str.endsWith("gdh"));
        sop(str.contains("def"));
        System.out.println("/////////////////////////////////");
        
        sop(str.equals("abcdefGdh"));
        sop(str.equalsIgnoreCase("ABcdefgdh"));
    }
    public static void method_str3(){
        char[] arr = {'a','b','c','d','e','f','g'};
        String str = new String(arr);
        //String str = new String(arr,1,5);则输出  s=bcdef
        sop("s="+str);
        
        String chs = "kindom";
        char[] ch = chs.toCharArray();
        for(int x=0;x<chs.length();x++){
            sop("ch["+x+"] = "+ch[x]);
        }
    }
    public static void main(String[] args){
        //method_str1();
        method_str2();
        //method_str3();

    }
    
    public static void sop(Object obj)
    {
        System.out.println(obj);
    }
}
