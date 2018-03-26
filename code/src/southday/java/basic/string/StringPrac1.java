package southday.java.basic.string;
/*练习1：
1.模拟一个trim方法，除去字符串两端的空格
  (1)判断字符串的第一个位置是否为空格，如果是则继续向下判断，直到不是空格为止；结尾处判断空格也是如此。
  (2)当开头和结尾处都判断不是空格时，则获取该字符串。
2.将一个字符串进行反转。将字符串指定部分进行反转，如：“abcdefg"→"abfedcg"将 cdef 反转为fedc
  (1)字符串变成数组
  (2)数组反转
  (3)数组变字符串
3.获取一个字符串在另一个字符串中出现的次数
  (1)定义一个计数器
  (2)获取 某字符串（xx) 第一次出现的位置
  (3)从第一次出现的位置后从剩余的字符串中继续去 xx 的位置,且每获取一次就计数一次
  (4)当获取不到时，计数完成（返回-1）
4.获取两个字符串中最大相同字符串。
定义一个字符串，其长度为N，则它的所用分解情况（子串）长度为：N,N-1,N-2,...,1||这里长度为0即子串为Null
      每个长度又可取排列不同的字符串。
    （1）本算法采用  首尾  两个“指针”的方法来确定子串的长度
    （2）通过尾指针的递减来改变字符串的长度
    （3）通过两个指针同时地移动，来获取长度相同，包含字符不同的字符串。
    （4）当某个长度的字串取满后，尾指针减1，然后LOOP。直到N=1。
    该题获取的是相同最大字符串，所以只要一找到满足的字符串，便可停止循环，然后输出满足条件的第一个字符串。
    相反，如果要获取相同最小字符串(一般都是一个字符，没多大意思），则两个指针应该从最近（重合）位置开始定义，再一点一点扩展。思路类似。
5.去掉字符串中的所有空格：

 */
public class StringPrac1 {
    public static void main(String[] args){
        StringPrac1 prac = new StringPrac1();
        String str = "  JAVA lichaoxi JAVA haha JAVA  ";
        String s = "JAVA li abxc haha";
        String nstr = "xi a ";
        //prac.Prac_1(str);
        //prac.Prac_2(str);
        //prac.Prac_3(str);
        //String temp = prac.Prac_4(str,s);
        //System.out.println("str1 和 str2中相同的最大字符串为："+temp);
        prac.Prac_5(str);
    }
//****************************************************************
    public void Prac_5(String s){
        int i=0,j=0;
        String str = "";
        char[] arr = new char[s.length()];
        str = RmoveBlank(arr,i,j,s,str);
        System.out.printf("去掉空格后的字符串：\n*%s*\n",str);
    }
    private String RmoveBlank(char[] arr,int i,int j,String s,String str){
        char ch = 0;
        while (i < s.length()) {
            ch = s.charAt(i);
            if (ch != ' ') {
                arr[j] = ch;
                j++;
            }
            i++;
        }
        if (i != 0) {            
            return new String(arr,0,j);
        }
        return s;
    }
//****************************************************************
    public String Prac_4(String s1,String s2){
        String MaxStr,MinStr;
        MaxStr = (s1.length()>s2.length())?s1: s2;
        MinStr = (MaxStr == s1)?s2: s1;
        System.out.println("MAX = "+MaxStr+"-----MIN = "+MinStr);
        
        for(int x=0;x<MinStr.length();x++){
            for(int y=0, z=MinStr.length()-x; z!=MinStr.length()+1;y++,z++){
                String temp = MinStr.substring(y,z);
                if(MaxStr.contains(temp))
                    return temp;    //这TM不是返回语句是毛线？
            }
        }
        return "";//这里的字符串随便写，除非s1,s2中没有相同的字符串，否则执行不到这条语句；没有相同字符串时返回""
    }
        
//*******************************************************    
    public void Prac_3(String str){
        int index = 0;
        int count = 0;
        //count = method_1(str,index,count);
        count = method_2(str,index,count);
        System.out.println(" JAVA 在str中出现的次数是："+count+"次.");
    }/*
    private int method_1(String str,int index,int count){
        while((index=str.indexOf("JAVA"))!=-1){
            System.out.println("count_"+count+"=="+str);
            str = str.substring(index+"JAVA".length());
            count ++;
        }
        return count;
    }*/
    private int method_2(String str,int index,int count){
        while((index = str.indexOf("JAVA",index))!=-1){
            index = index +"JAVA".length();
            System.out.println("index = "+index);
            count ++;
        }
        return count;
    }
/* 用切割的不可行性：假设切割符为字符串A：
（1）如果A在被切割字符串的首断，则切割后，前面会产生一个  空字符串 ———— ""
          而如果A在末端则不会出现空字符串；
（2）对于A是否在首末端，可以用 boolean startsWith(str) 和 boolean endsWith(str)来判断，
但是被切割的字符串str中如果出现 ---AA----形式，则切割后两个A之间也会产生  空字符串，这种情况就很难判断。
————所以用split来判断某个字符串在该字符串中出现的次数不可行（或者说：存在巧合性）
 */
//********************************************************
    private void fanZ(String s,char[] arr){
        char ch;
        int m = s.length();
        for(int x=0;x<=(m-1)/2;x++){
            ch = arr[x];
            arr[x] = arr[m-1-x];
            arr[m-1-x] = ch;
        }
    }
    private String StringChoice(String s,int a,char[] arr){
        if(a==1)
            fanZ(s,arr);
        else{
            String str = s.substring(0,10);//获取要部分反转的字符串
            fanZ(str,arr);
        }
        return new String(arr,0,s.length());
    }
    private void outPut(String s,char[] arr,String str){
        //String str = new String(arr,0,s.length());
        System.out.printf("反转前**%s**\n反转后**%s**\n",s, str);
    }
    public void Prac_2(String s){
        char[] arr = s.toCharArray();    
        String str = StringChoice(s,0,arr);//如果想要全部反转，则输入的Int a = 1;否则部分反转；
        outPut(s,arr,str);
    }
//*********************************************************    
    public void Prac_1(String str){
        int x=0;
        int y=str.length()-1;
        while(x<=y&&str.charAt(x) == ' ')
            x++;
        
        while(y>=x&&str.charAt(y) == ' ')
            y--;
        
        String str_1 = str.substring(x,y+1);
        System.out.println("**"+str_1+"**");
    }    
}
