import javax.swing.*;
import java.awt.*;

import clock.WatchSystem;

public class Watch extends JFrame{
    public static final int SCREEN_WIDTH = 735;
    public static final int SCREEN_HEIGHT = 935;

    private final WatchSystem watchSystem;

    public Watch(){
        super("Watch");
        Dimension dim = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(dim);
        setResizable(false);

        watchSystem = new WatchSystem();

        addKeyListener(watchSystem);
        addMouseListener(watchSystem);
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
