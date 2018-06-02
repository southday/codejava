package southday.java.pattern.proxy.virtual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ImageProxyTestDrive {
    ImageComponent imgComp;
    JFrame frame = new JFrame("CD Cover Viewer");
    JMenuBar menuBar;
    JMenu menu;
    Hashtable<String, String> cds = new Hashtable<String, String>();
    
    public static void main(String[] args) throws Exception {
        ImageProxyTestDrive testDrive = new ImageProxyTestDrive();
    }
    
    public ImageProxyTestDrive() throws Exception {
        cds.put("Ambient: Music for Airports", "http://images.amazon.com/images/P/B000002S2K.01.LZZZZZZZ.jpg");
        cds.put("Buddha Bar", "http://images.amazon.com/images/P/B00009XBYK.01.LZZZZZZZ.jpg");
        cds.put("Ima", "http://images.amazon.com/images/P/B000005IRM.01.LZZZZZZZ.jpg");
        cds.put("Karma", "http://images.amazon.com/images/P/B000005DCB.01.LZZZZZZZ.jpg");
        cds.put("MCMXC A.D", "http://images.amazon.com/images/P/B000002URV.01.LZZZZZZZ.jpg");
        
        URL initialURL = new URL((String) cds.get("Ima"));
        menuBar = new JMenuBar();
        menu = new JMenu("Favorite CDs");
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        
        for (Enumeration e = cds.keys(); e.hasMoreElements();) {
            String name = (String) e.nextElement();
            JMenuItem menuItem = new JMenuItem(name);
            menu.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    imgComp.setIcon(new ImageProxy(getCDUrl(event.getActionCommand())));
                    // imgComp注册到了frame中，所以每次frame.repaint()会在内部调用imgComp.paintComponent()
                    // 进而调用到icon.paintIcon()
                    frame.repaint();
                }
            });
        }
        
        // 建立框架和菜单
        Icon icon = new ImageProxy(initialURL);
        imgComp = new ImageComponent(icon);
        frame.getContentPane().add(imgComp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    
    private URL getCDUrl(String name) {
        try {
            return new URL((String) cds.get(name));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
