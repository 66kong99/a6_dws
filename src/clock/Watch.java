package clock;

import javax.swing.JFrame;

public class Watch extends JFrame{
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;

    private WatchSystem watchSystem;

    public Watch(){
        super("Watch");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocation(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        watchSystem = new WatchSystem();

        addKeyListener(watchSystem);
        add(watchSystem);

    }

    public void start(){
        setVisible(true);
        watchSystem.start();
    }

    public static void main() {(new Watch()).start();}
}
