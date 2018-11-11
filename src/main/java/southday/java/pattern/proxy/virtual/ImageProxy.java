package southday.java.pattern.proxy.virtual;

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageProxy implements Icon {
    private ImageIcon imgIcon;
    URL imgURL;
    Thread retrievalThread;
    boolean retrieving = false;
    
    public ImageProxy(URL url) {
        this.imgURL = url;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) { 
        System.out.println("hhhhhh");
        if (imgIcon != null) {
            imgIcon.paintIcon(c, g, x, y);
        } else {
            g.drawString("Loading CD cover, please wait...", x + 300, y + 190);
            if (!retrieving) {
                retrieving = true;
                // 不希望在等待图像时整个应用程序被挂起，所以创建新线程加载图像
                retrievalThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            imgIcon = new ImageIcon(imgURL, "CD Cover");
                            c.repaint();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                retrievalThread.start();
            }
        }
    }

    @Override
    public int getIconWidth() {
        if (imgIcon != null) {
            return imgIcon.getIconWidth();
        } else {
            return 800;
        }
    }

    @Override
    public int getIconHeight() {
        if (imgIcon != null) {
            return imgIcon.getIconHeight();
        } else {
            return 600;
        }
    }

}
