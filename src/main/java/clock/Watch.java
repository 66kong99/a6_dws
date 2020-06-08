package clock;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.Buffer;

import util.Resource;

public class Watch extends JFrame{
    public static final int SCREEN_WIDTH = 735;
    public static final int SCREEN_HEIGHT = 1135;

    private Thread timeThread;

    private WatchSystem watchSystem;

    public Watch(){
        super("Watch");
        Dimension dim = new Dimension(735, 1135);

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(dim);
        setResizable(false);

        watchSystem = new WatchSystem();

        addKeyListener(watchSystem);
        add(watchSystem);

        pack();

    }

    public void start(){
        System.out.println("START");
        setVisible(true);
        watchSystem.start();
    }

    public static void main(String[] args) {
        (new Watch()).start();
    }

}
