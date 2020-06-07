package clock;

// 지워질것
// Modemanager에 병합되면 지울것

import javax.swing.JFrame;

public class GameWindow extends JFrame {
    public static final int SCREEN_WIDTH = 600;
    private Game game;

    public GameWindow() {
        super("Dinosaur Game");
        setSize(SCREEN_WIDTH, 175);
        setLocation(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        game = new Game();
        //addKeyListener(game);
        add(game);
    }

    public void startGame() {
        setVisible(true);
        game.start();
    }
}
