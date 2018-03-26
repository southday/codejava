package southday.java.util.voter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * 亚洲制霸AUDC投票
 * @author southday
 * @date 2016年12月10日
 */
public class Voter {
    // 投票地址
    private String url = "http://api.tiaooo.com/m/audc/vote_insert";
    // 投票参数
    private String param = "id=9";
    // 代理ip地址
    private List<String> ips = new ArrayList<String>();
    
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String result = null;
    
    // 成功和失败次数
    private int successNum = 1;
    private int failureNum = 1;
    
    public Voter() {}
    
    public void readIps(String ipsFilePath) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(new File(ipsFilePath)));
        String line = null;
        while ((line = bf.readLine()) != null) {
            this.ips.add(line);
        }
        bf.close();
    }
    
    public void vote() {
        if (this.ips == null) {
            System.out.println("ips = null");
            return;
        }
        
        for (String ipPort: this.ips) {
            String[] ipp = ipPort.split(":");
            String ip = ipp[0];
            int port = Integer.parseInt(ipp[1]);
            
            vote(ip, port);
        }
        System.out.println("投票总次数:" + (successNum + failureNum) + ", 成功次数:" + successNum + ", 失败次数:" + failureNum);
    }
    
    public void vote(String ip, int port) {
        try {
            SocketAddress socket = new InetSocketAddress(ip, port);
            Proxy proxy =  new Proxy(Proxy.Type.HTTP, socket);      
            URL realUrl = new URL(this.url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection(proxy);
            conn.setConnectTimeout(3000); // 设置连接超时时间为3s
            conn.setReadTimeout(3000); // 设置从主机读取数据的超时时间为3s
            // 设置通用的请求属性
            conn.setRequestProperty("Host", "api.tiaooo.com");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:50.0) Gecko/20100101 Firefox/50.0");
            conn.setRequestProperty("Accept", "application/json, text/plain, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Referer", "http://m.tiaooo.com/?from=timeline&isappinstalled=0");
            conn.setRequestProperty("Content-Length", "4");
            conn.setRequestProperty("Origin", "http://m.tiaooo.com");
            conn.setRequestProperty("Connection", "keep-alive");
            
            // 发送post请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            // 判断是否成功
            if (result != null && result.contains("\"status\":1")) {
                System.out.println(ip + ":" + port + ": 投票成功，目前已成功 " + (successNum++) + "次");
            } else {
                failureNum++;
            }
        } catch (Exception e) {
            failureNum++;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public int getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(int successNum) {
        this.successNum = successNum;
    }

    public int getFailureNum() {
        return failureNum;
    }

    public void setFailureNum(int failureNum) {
        this.failureNum = failureNum;
    }
}
