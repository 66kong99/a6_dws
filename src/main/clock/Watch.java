package clock;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.nio.Buffer;

import util.Resource;

public class Watch extends JFrame{
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;

    BufferedImage background;
    private WatchSystem watchSystem;

    public Watch(){
        super("Watch");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocation(1234, 1905);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        background = Resource.getResourceImage("data/clock.png");
        myPanel bg = new myPanel();
        bg.setSize(1234, 1905);

        add(bg);

        watchSystem = new WatchSystem();

        addKeyListener(watchSystem);
        add(watchSystem);

    }
    class myPanel extends JPanel{
        public void paint(Graphics g){
            g.drawImage(background, 0, 0, null);
        }
    }

    public void start(){
        setVisible(true);
        watchSystem.start();
    }

    public static void main() {(new Watch()).start();}
}
