package southday.java.basic.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * 通过创建配置文件来记录软件使用的次数(程序运行的次数)
 * 应用： 给你一个软件，让你免费使用30天，之后向你收费...
 * 
 * 通过Properties加载配置文件来实现计数
 */

public class RunCountor {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        File file = new File("e:\\count.properties.ini");
        if (!file.exists())
            file.createNewFile();
        FileInputStream fis = new FileInputStream(file);
        
        prop.load(fis); // 加载数据
        String value = prop.getProperty("time");
        int count = 0;
        count = (value != null) ? Integer.parseInt(value) + 1 : count + 1;
        if (count >= 5)
            System.out.println("您好！使用次数已到，请拿钱！！");
        
        prop.setProperty("time", Integer.valueOf(count).toString());
        FileOutputStream fos = new FileOutputStream(file);
        prop.store(fos, "");
        
        fis.close();
        fos.close();
    }
}
