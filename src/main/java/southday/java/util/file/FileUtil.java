package southday.java.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @author southday
 * @date 2017年3月7日
 */
public class FileUtil {
    private static final int MAX_BUFFER_SIZE = 20480; // 20KB
    public static final String ALL_FILE = "ALL_FILE";
    public static final String JUST_FILE = "JUST_FILE";
    public static final String JUST_DIRECTORY = "JUST_DIRECTORY";
    
    public FileUtil() {
        super();
    }
    
    /**
     * 将InputStream流保存成文件
     * @param ins       要写入的文件流
     * @param filePath  目标文件路径
     * @return
     */
     public static boolean writeFile(InputStream ins, String filePath) {
         BufferedOutputStream bos = null;
         byte[] buf = new byte[MAX_BUFFER_SIZE];
         int len = 0;
         try {
             bos = new BufferedOutputStream(new FileOutputStream(filePath));
             while ((len = ins.read(buf)) != -1) {
                 bos.write(buf, 0, len);
             }
         } catch (Exception e) {
             e.printStackTrace();
             return false;
         } finally {
             FileUtil.closeSource(bos);
             FileUtil.closeSource(ins);
         }
         return true;
     }
    
    /**
     * 写入文件(拷贝)
     * @param source  源文件路径
     * @param dest    目标文件路径
     * @return
     */
    public static boolean writeFile(String source, String dest) {
        if (!checkParams(source, dest)) {
            return false;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        byte[] buf = new byte[MAX_BUFFER_SIZE];
        int len = 0;
        try {
            bis = new BufferedInputStream(new FileInputStream(source));
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            FileUtil.closeSource(bis);
            FileUtil.closeSource(bos);
        }
        return true;
    }
    
    /**
     * 对参数校验，一般对文件的操作都不允许传进来的参数为空或者空字符串等
     * @param args  文件路径/目录路径等
     * @return      校验通过返回true，否则返回false
     */
    public static boolean checkParams(String... args) {
        for (String arg : args) {
            if (arg == null || arg.trim().length() < 1) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 创建目录
     * @param dirParh
     * @return
     */
    public static boolean createDirectory(String dirParh) {
        File file = new File(dirParh);
        return file.exists() ? true : file.mkdirs();
    }
    
    /**
     * 判断是否所有的文件都已存在
     * @param filePathList
     * @return
     */
    public static boolean allExists(List<String> filePathList) {
        for (String filePath : filePathList) {
            if (!fileExists(filePath)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 判断文件是否存在
     * @param path
     * @return
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * 批量文件移动
     * @param sourceList  源文件路径(List) 
     * @param destDIR     目标目录
     * @return
     */
    public static boolean moveFile(List<String> sourceList, String destDIR) {
        for (String source : sourceList) {
            if (false == moveFile(source, destDIR)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 文件移动
     * @param source   源文件路径
     * @param destDIR  目标目录
     * @return
     */
    public static boolean moveFile(String source, String destDIR) {
        boolean copySuccess = copyFile(source, destDIR);
        if (copySuccess) {
            File sourceFile = new File(source);
            return sourceFile.delete();
        }
        return false;
    }
    
    /**
     * 文件拷贝
     * @param sourceList 源文件路径(List)
     * @param destDIR    目标目录
     * @return
     */
    public static boolean copyFile(List<String> sourceList, String destDIR) {
        for (String source : sourceList) {
            if (false == copyFile(source, destDIR)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 文件拷贝
     * @param source   源文件路径
     * @param destDIR  目标目录
     * @return
     */
    public static boolean copyFile(String source, String destDIR) {
        if (!checkParams(source, destDIR)) {
            return false;
        }
        createDirectory(destDIR);
        String fileName = parseFileName(source);
        String destFilePath = FileUtil.getFullPath(destDIR, fileName);
        return writeFile(source, destFilePath);
    }
    
    public static String parseFileName(String source){
        File file = new File(source);
        return file.getName();
    }
    
    /**
     * 获取完整路径
     * @param part1
     * @param part2
     * @return
     */
    public static String getFullPath(String part1, String part2){
        if (!checkParams(part1, part2)) {
            return null;
        }
        String spliter = System.getProperty("file.separator");
        if (!part1.endsWith("/") && !part1.endsWith("\\")) {
            part1 += spliter;
        }
        if (part2.startsWith("/") || part2.startsWith("\\")) {
            part2 = part2.substring(1);
        }
        return part1 + part2;
    }
    
    /**
     * 路径转换，用于处理不同操作系统目录分隔符不一致的情况
     * @param source
     * @return
     */
    public static String convertPath(String source) {
        if (!checkParams(source)) {
            return null;
        }
        String spliter = System.getProperty("file.separator");
        /*
         * 对于replaceAll：
         * 1) "\" 在java字符串中表示转义，所以要表示"\"需要写成"\\"
         * 2) "\" 在java正则中也表示转义，所以要表示"\"也需要写成"\\"
         * 这样以来，在java正则里要匹配字符串中的"\"，就需要写成"\\\\"
         */
        if ("/".equals(spliter)) {
            return source.replaceAll("[/|\\\\]", "/");
        } else if ("\\".equals(spliter)) {
            return source.replaceAll("[/|\\\\]", "\\\\");
        }
        return null;
    }
    
    /**
     * 批量删除文件
     * @param filePathList
     */
    public static void deleteFiles(List<String> filePathList) {
        for (String filePath : filePathList) {
            deleteFile(filePath);
        }
    }
    
    /**
     * 删除整个目录
     * @param file  文件目录/文件路径
     * @return
     */
    public static void deleteFile(String file) {
        deleteFile(new File(file));
    }
    
    /**
     * 删除整个目录
     * @param file  目录文件/文件
     * @return
     */
    public static void deleteFile(File file) {
        if (file.exists() && file.isFile()) {
            file.delete();
        } else if (file.exists()) {
            File[] fileList = file.listFiles();
            for (File subFile : fileList) {
                deleteFile(subFile);
            }
            file.delete();
        }
    }
    
    /**
     * 关闭资源
     * @param source
     */
    public static void closeSource(Closeable source) {
        if (source != null) {
            try {
                source.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取当前文件第1层父目录
     * @param fullPath
     * @return
     */
    public static String getParentPath(String fullPath) {
        return getParentPath(fullPath, 1);
    }
    
    /**
     * 获取向前第level层父目录
     * @param fullPath 文件路径
     * @param level    0表示当前文件路径，从i(i>=1)开始表示当前文件(向前搜索)的第i层父目录
     * @return
     */
    public static String getParentPath(String fullPath, int level) {
        String parentPath = fullPath;
        while ((level--) > 0 && parentPath != null) {
            parentPath = new File(parentPath).getParent();
        }
        return parentPath;
    }
    
    /**
     * 列出目录下所有文件的绝对路径
     * @param directory
     * @return
     */
    public static List<String> listFilePath(String directory) {
        return listFilePath(new File(directory));
    }
    
    /**
     * 列出目录下要选择的文件的绝对路径
     * @param directory 目录路径
     * @param select    文件(JUST_FILE)/目录(JUST_DIRECTORY)/所有文件(ALL_FILE)
     * @return
     */
    public static List<String> listFilePath(String directory, String select) {
        return listFilePath(new File(directory), select);
    }
    
    /**
     * 列出目录下所有文件的绝对路径
     * @param dirFile
     * @return
     */
    public static List<String> listFilePath(File dirFile) {
       return listFilePath(dirFile, ALL_FILE); 
    }
    
    /**
     * 列出目录下要选择的文件的绝对路径
     * @param dirFile 目录文件
     * @Param select  文件(JUST_FILE)/目录(JUST_DIRECTORY)/所有文件(ALL_FILE)
     * @return
     */
    public static List<String> listFilePath(File dirFile, String select) {
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        List<String> pathList = new ArrayList<String>();
        for (File file : dirFile.listFiles()) {
            if (file.exists()) {
                if (JUST_FILE.equals(select) && file.isFile()) {
                    pathList.add(file.getAbsolutePath());
                } else if (JUST_DIRECTORY.equals(select) && file.isDirectory()) {
                    pathList.add(file.getAbsolutePath());
                } else if (ALL_FILE.equals(select)) {
                    pathList.add(file.getAbsolutePath());
                }
            }
        }
        return pathList;
    }
}
