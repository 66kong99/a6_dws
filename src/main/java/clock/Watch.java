package clock;

import javax.swing.JFrame;
import java.awt.Dimension;

@SuppressWarnings("uncommentedmain")
public class Watch extends JFrame{
    public static final int SCREEN_WIDTH = 735;
    public static final int SCREEN_HEIGHT = 935;

    private transient final WatchSystem watchSystem;

    public Watch(){
        super("clock.Watch");
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
        setVisible(true);
        watchSystem.start();
    }

    public static void main(String[] args) {
        (new Watch()).start();
    }

}
