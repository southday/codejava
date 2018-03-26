package southday.java.basic.string;
/*
4.替换
  String replace(char oldChar, char newChar);
  String replace(CharSequence target, CharSequence replacement); 
  
5.切割
  String[] split(regex);用 regex 来切割字符串，返回多个字符串
  
6.子串——获取字符串中的一部分
  String substring(int beginIndex);
  String substring(int beginIndex,int endIndex);
  
7.转换  去除空格   比较
  7.1将字符串转换成大写，或者小写
    String toUpperCase();
    String toUpperCase(Locale locale);使用给定 Locale 的规则将此 String 中的所有字符都转换为大写。
    
    String toLowerCase();
    String toLowerCase(Locale locale);
    
  7.2将字符串两端的多个空格进行去除
    String trim();
  
  7.3对两个字符串进行自然顺序的比较
    int compareTo(String anotherString);
*/

class StringDemo2
{
    public static void method_replace(){
        String s = "haha JAVA";
        //String s1 = s.replace('a','R');//区分大小写
        //String s2 = s.replace('m', 'k');//若替换的是原字符串中没有的字符，则返回原字符串
        String s1 = s.replace("JAVA", "world");
        sop("s = "+s);
        sop("s1 = "+s1);
        //sop("s2 = "+s2);// haha JAVA
    }
    
    public static void method_split(){
        String s = "zhangsang,lisi,wangwu";
        String[] arr = s.split(",");//用,将字符串分割
        for(int x=0;x<arr.length;x++){
            sop("String arr["+x+"] = "+arr[x]);
        }
    }
    
    public static void method_zstr(){
        String s = "abcdef";
        sop(s.substring(2));//从begin位置开始，到结束。 如果角标不存在，会出现角标越界异常。
        sop(s.substring(2,4));//包含头，不包含尾——输出从  begin 到 end-1
        sop(s.substring(2,s.length()));
    }
    
    public static void method_7(){
        String s = "   Hello Java  ";
        sop("s = " + s);
        sop(s.toLowerCase());
        sop(s.toUpperCase());
        sop(s.trim());//去除两端空格很有用：比如登陆邮箱或者其他网站，
                      //用户输入用户名时可能不小心多输入了空格，这时我们得让TA登陆，就需要trim();
        
        String str_1 = "a1c";//按字典顺序比较两个字符串
        String str_2 = "aaa";       //如果参数字符串等于此字符串，则返回值 0；
        sop(str_1.compareTo(str_2) + " [1(ASCII为49) - a(ASCII为97)]");//如果此字符串按字典顺序小于字符串参数，则返回一个小于 0 的值；
                                    //如果此字符串按字典顺序大于字符串参数，则返回一个大于 0 的值。
        //-48的由来：（1）比较后一组字符的前提是前一组字符相同，如a = a,所以比较 1和a；而因为1!=a，所以不比较c 和  a
        //1 的 ASCII码为49，a 的ASCII码为 97.  -48 = 49-97;
        
    }
    public static void main(String[] args){
        method_7();
        //method_zstr();
        //method_split();
        //method_replace();
    }
    
    public static void sop(Object obj){
        System.out.println(obj);
    }
}
