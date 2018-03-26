package southday.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import southday.java.util.file.FileUtil;

/**
 * 命令行工具
 * @author southday
 * @date 2017年3月7日
 */
public class Cmder {
    public Cmder() {
        super();
    }

    private static class StreamReaderThread implements Runnable {
        InputStream ins = null;
        
        public StreamReaderThread(InputStream ins) {
            this.ins = ins;
        }
        
        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            try {
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                FileUtil.closeSource(ins);
                FileUtil.closeSource(br);
            }
        }
    }
    
    /**
     * 执行系统命令
     * @param cmd        完整命令，如："tar -xvf sql_scripts.tar -C ."
     * @param directory  命令在指定目录下执行
     * @return
     */
    public static boolean exec(String cmd, String directory) {
        List<String> cmds = Arrays.asList(cmd.split("\\s+"));
        return exec(cmds, directory);
    }
    
    /**
     * 执行系统命令
     * @param cmds       完整命令以空格分割得到List
     * @param directory  命令在指定目录下执行
     * @return
     */
    public static boolean exec(List<String> cmds, String directory) {
        printCurrentDIR(directory);
        printCmd(cmds);
        try {
            ProcessBuilder procBuilder = new ProcessBuilder(cmds);
            if (directory != null && directory.trim().length() > 0) {
                procBuilder.directory(new File(directory));
            }
            procBuilder.redirectErrorStream(true);
            Process proc = procBuilder.start();
            new Thread(new StreamReaderThread(proc.getInputStream())).start();
            new Thread(new StreamReaderThread(proc.getErrorStream())).start();
            proc.waitFor();
            proc.destroy();
            if (proc.exitValue() != 0) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * 打印命令信息
     * @param cmds
     */
    public static void printCmd(List<String> cmds) {
        StringBuilder sb = new StringBuilder();
        for (String s : cmds) {
            sb.append(s + " ");
        }
        System.out.println("[cmd] -> " + sb.toString());
    }
    
    /**
     * 打印命令执行的当前目录
     * @param directory
     */
    private static void printCurrentDIR(String directory) {
        if (directory == null || directory.trim().length() < 1) {
            directory = System.getProperty("user.dir");
        }
        System.out.println("[directory] -> " + directory);
    }
}
