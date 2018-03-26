package southday.java.basic.serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* 该类主要放置 package 中各.java共用的方法
 */

public class SerializeUtil {
    public static <E> void serializeObject(E obj, String filepath) {
        try {
            FileOutputStream fos = new FileOutputStream(filepath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            fos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("serialize object over");
    }
    
    public static Object deserializeObject(String filepath) {
        Object obj = null;
        try {
            FileInputStream fis = new FileInputStream(filepath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            obj = ois.readObject();
            fis.close();
            ois.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("deserialize object over");
        return obj;
    }
}
