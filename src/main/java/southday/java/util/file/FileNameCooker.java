package southday.java.util.file;

import java.io.File;

/**
 * 文件名称修改者<br>
 * The 'cooker' is come from Breaking Bad.
 * @author southday
 * @date 2017年5月12日
 */
public class FileNameCooker extends FileUtil {
    
    public static void proc(File file, String oldStr, String newStr) {
        if (file == null || !file.exists()) {
            return;
        }
        String name = null;
        if (file.isFile()) {
            name = cookName(file, oldStr, newStr);
            System.out.println("[file] -> " + name);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                if (subFile.isDirectory()) {
                    proc(subFile, oldStr, newStr);
                } else {
                    name = cookName(subFile, oldStr, newStr);
                    System.out.println("[file] -> " + name);
                }
            }
            name = cookName(file, oldStr, newStr);
            System.out.println("[directory] -> " + name);
        }
    }
    
    private static String cookName(File file, String oldStr, String newStr) {
        String newName = getFullPath(file.getParent(), file.getName().replace(oldStr, newStr));
        file.renameTo(new File(newName));
        return newName;
    }
    
    public static void main(String[] args) {
        String filePath = convertPath("E:/2018新东方考研数学/高等数学强化班");
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
//        String oldStr = "【加QQ741347620免费赠送18课程】";
//        String newStr = "";
//        String newName = getFullPath(file.getParent(), file.getName().replace(oldStr, newStr));
//        System.out.println(newName);
        
//        String oldStr = "【加QQ741347620免费赠送18课程】";
        String oldStr = "加QQ741347620赠课";
        String newStr = "";
        proc(file, oldStr, newStr);
    }
}

/* 
 * Read files recursively, and set a hook into the function to do something I wanted.
 * Like this:
 * public void procFiles(..., Hook hook) {
 *     ...
 *     hook.proc();
 *     ...
 * }
 * 
 */
