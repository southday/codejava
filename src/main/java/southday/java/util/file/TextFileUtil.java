package southday.java.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.UUID;

/**
 * 文本文件处理工具
 * @author southday
 * @date 2017年3月19日
 */
public class TextFileUtil extends FileUtil {
    public TextFileUtil() {}
    
    /**
     * 将源文件中的oldStr转换为newStr，最后生成目标文件
     * @param source 源文件路径
     * @param dest   目标文件路径
     * @param oldStr 待替换字符串
     * @param newStr 替换字符串
     */
    public static boolean replace(String source, String dest, String oldStr, String newStr) {
        if (!checkParams(source, dest)) {
            System.out.println("[file-failure]: " + source);
            return false;
        }
        boolean eq = false;
        String tempDIR = null;
        if (source.equals(dest)) {
            eq = true;
            tempDIR = createTempDIR(source);
            if (!checkParams(tempDIR)) {
                System.out.println("[file-failure]: " + source);
                return false;
            }
            dest = getFullPath(tempDIR, parseFileName(dest));
        }
        BufferedReader bfr = null;
        BufferedWriter bfw = null;
        try {
            bfr = new BufferedReader(new FileReader(new File(source)));
            bfw = new BufferedWriter(new FileWriter(new File(dest)));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                line = line.replace(oldStr, newStr);
                bfw.write(line);
                bfw.newLine();
                bfw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSource(bfr);
            closeSource(bfw);
        }
        if (eq) {
            moveFile(dest, getParentPath(source));
            deleteFile(tempDIR);
        }
        System.out.println("[file-success]: " + source);
        return true;
    }
    
    /**
     * 将目录下所有文本中的oldStr替换为newStr
     * @param directory 目录路径
     * @param oldStr    待替换字符串
     * @param newStr    替换字符串
     * @return
     */
    public static boolean replace(String directory, String oldStr, String newStr) {
        if (!checkParams(directory)) {
            System.out.println("[directory-failure]: " + directory);
            return false;
        }
        String tempDIR = convertPath(createTempDIR(directory));
        if (!checkParams(tempDIR)) {
            System.out.println("[directory-failure]: " + directory);
            return false;
        }
        for (File file : new File(directory).listFiles()) {
            String filePath = file.getAbsolutePath();
            if (tempDIR.equals(filePath)) {
                continue;
            } else if (file.isDirectory()) {
                replace(filePath, oldStr, newStr);
            } else {
                String dest = getFullPath(tempDIR, parseFileName(filePath));
                replace(filePath, dest, oldStr, newStr);
            }
        }
        moveFile(listFilePath(tempDIR), directory);
        deleteFile(tempDIR);
        System.out.println("[directory-success]: " + directory);
        return true;
    }
    
    /**
     * 在当前文件目录下创建一个临时目录<br>
     * 如果当前文件就是目录，那么就在此目录下创建临时目录
     * @param filePath 某文件路径
     * @return
     */
    public static String createTempDIR(String filePath) {
        String parentPath = new File(filePath).isDirectory() ? filePath : getParentPath(filePath);
        String tempDIR = getFullPath(parentPath, UUID.randomUUID().toString());
        if (!createDirectory(tempDIR)) {
            return null;
        }
        return tempDIR;
    }
    
    public static void main(String[] args) {
        String oldStr = "\t";
        String newStr = "    ";
        String directory = "D:/GitHub/Repos/codejava/code/src/southday/java/util/voter";
        replace(directory, oldStr, newStr);
    }
}
