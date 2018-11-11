package southday.java.basic.io;
import java.io.FileWriter;
import java.io.IOException;

public class IOexception {
    public static void main(String[] args) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("d:\\demo.txt");//JAVA中在写路径时，要用双斜杠，因为第一个\是转义符
            fw.write("tronadoghost");
        } catch(IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if(fw!=null)
                    fw.close();
            } catch(IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
/*错误点：
（1）：最基本的，才创建FileWriter对象，调用FlieWriter函数时，是需要对异常进行处理的，要么抛,要么try。

（2）：fw.close();是必须的步骤，因为try{}中的fw与finally{}中的fw不是同一个，所以会出现finally{}中的fw解析异常。

（3）：为了使finally{}中的fw可以解析，则把fw的创建提到try{}外——FileWriter fw = null,然后在try{}内进行覆盖。
   此外，fw.close()虽然在finally{}中，但其同样需要进行异常处理——try{fw.close();}catch(IOException e){...}。
   
（4）：现在，看似不错。但是注意：当try{}中的fw 被覆盖如：fw = new FileWriter("k:\\dome.txt")，因为不存在K盘，
所以会报出：（系统找不到指定路径）的错误，这样一来fw就覆盖失败，则finally{}的fw就为null，所以接着就会报出   空指针异常。

（5）：为了避免（4）中的现象，则需要对finally{}中try{}fw进行判断，判断其是否为null,否则调用close()。

（6）：不过以后可能会有更对的流被创建，这样的话采用这种形式的判断会显得很笨拙，需要再做改进。

*/
