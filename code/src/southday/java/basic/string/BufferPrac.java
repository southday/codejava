package southday.java.basic.string;
/*
StringBuffer 是字符串缓冲区，是一个容器  ***（1）长度是可变化的  （2）可以操作多个数据类型   （3）最终会通过toString()变成字符串
—— —— 功能：
CURD —————— Create  Update  Read  Delete
（1）存储:
   StringBuffer append(数据)： 将指定数据作为参数添加到已有数据结尾处；
   StringBuffer insert(index,数据):可以将数据插入到指定index位置 ;
（2）删除
   StringBuffer delete(int start,int end)：移除指定序列：包含start 不包含end；
   StringBuffer deleteCharAt(int index)：删除指定位置的字符；
（3）获取
   char charAt(int index)：返回此序列中指定索引处的 char 值
    int indexOf(String str)：返回第一次出现的指定子字符串在该字符串中的索引
    int indexOf(String str, int fromIndex)：从指定的索引处开始，返回第一次出现的指定子字符串在该字符串中的索引
    int length();
    String substring(int start, int end)：返回一个新的 String，它包含此序列当前所包含的字符子序列。 
（4）修改
   StringBuffer replace(int start,int end,String str)：
         包含start,不包含end————使用给定 String 中的字符替换此序列的子字符串中的字符。
   void setCharAt(int index, char ch)： 将给定索引处的字符设置为 ch。 
（5）反转
   StringBuffer reverse()：将次字符序列用其反转形式取代
（6）将缓冲区指定数据存到指定字符数组中
   void getChars(int srcBegin,int srcEnd,char[] dst,int dstBegin)
 (7)在JDK1.5    之后出现了StringBuilder————不同的是：
 StringBuffer：线程同步    （多线程建议使用）
 StringBuilder：线程不同步  （一般使用StringBuilder,可以提高效率）
 
 升级的三个因素：（1）提高效率，（2）简化书写，（3）提高安全性
 
 */
public class BufferPrac {
    public static void main(String[] args){
        BufferPrac Bpra = new BufferPrac();
        StringBuilder sb = new StringBuilder();
        
        Bpra.Store_1(sb);
        //Bpra.Delete_2(sb);
        //Bpra.Replace_4(sb);
        //Bpra.Reverse_5(sb);
        //Bpra.BufToArr_6(sb);
    }
    public void Store_1(StringBuilder sb){
        sb.append("li").append("chao").append("xi");//只要返回的是源缓冲区对象，就可以在后面直接再调用函数
        sb.insert(2,"hame");//将"hame"插入到原字符串中第2位上，后面的跟上。即h-2 a-3 m-4 e-5 c-6
        sop(sb.toString());//append()方法返回的是原缓冲区对象sb，所以可以继续调用
    }
    
    public void Delete_2(StringBuilder sb){
        sb.append("Don't you know I am a god");
        sop("原串 ="+sb.toString());
        sb.delete(0, 6);//删除 原字符串中 0~5（包含角标5）位的子字符串"Don't "
        sop("删除角标为 0~5 后的字符串 （甲）="+sb.toString());
        //如果想单独删除一个字符，可以sb.delete(删除字符的角标x,x+1);——意思是：包含x,但不包含x+1，那就是x了
        //如果写成 sb.delete(x,x)——意思是：包含x,且不包含x，则计算机默认为不包含x，此操作下字符串不变。但是end < start则会报错
        sb.deleteCharAt(12);//删除 前一个字符串的 角标为 12 的字符
        sop("删除字符串（甲）的角标为12后的字符串（乙）="+sb.toString());
        sb.delete(0,sb.length());//清空sb缓冲区
        sop("清空sb缓冲区  ="+sb.toString());
    }
    
    public void Replace_4(StringBuilder sb){
        sb.append("I like JAVA");
        sop("原串 ="+sb.toString());
        sb.replace(2, 6, "love");//l-2 e-5
        sop("替换后 ="+sb.toString());
        sb.setCharAt(9,'v');//V-9
        sop("用v替换V后 ="+sb.toString());
    }
    
    public void Reverse_5(StringBuilder sb){
        sb.append("I like movic movic");
        sop("原串 ="+sb.toString());
        sop("反转后的字符串 ="+sb.reverse().toString());
    }
    
    public void BufToArr_6(StringBuilder sb){
        sb.append("abcdefg");
        char[] chs = new char[6];
        sb.getChars(1, 4, chs, 1);//b-1,d-3,从字符数组的第chs[1]开始，把字符串中的角标0~3的字符对应复制到字符数组中
    
        for(int x=0;x<chs.length;x++)
            sop("ch["+x+"] = "+chs[x]+" ;");
    }
    private static void sop(String str){
        System.out.println(str);
    }
}
