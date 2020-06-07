package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import util.Resource;

public class Game extends JPanel implements Runnable, Mode {
    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;

    private Background background;
    private Dinosaur dinosaur;
    private Hurdle hurdleManager;
    private Thread thread;
    private boolean buttenPressed;

    private int gameState = START_GAME_STATE;

    private BufferedImage replayButtonImage;
    private BufferedImage gameOverButtonImage;

    public Game(){
        dinosaur = new Dinosaur();
        background = new Background(GameWindow.SCREEN_WIDTH, dinosaur);
        dinosaur.setSpeedX(4);
        replayButtonImage = Resource.getResourceImage("data/replay_button.png");
        gameOverButtonImage = Resource.getResourceImage("data/gameover_text.png");
        hurdleManager = new Hurdle(dinosaur);
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void update(){
        if (gameState == GAME_PLAYING_STATE){
            background.update();
            dinosaur.update();
            hurdleManager.update();
            if(hurdleManager.isCollision()){
                dinosaur.dead(true);
                gameState = GAME_OVER_STATE;
            }
        }
    }

    public void paint(Graphics g){

        switch(gameState){
            case START_GAME_STATE:
                dinosaur.draw(g);
                break;
            case GAME_PLAYING_STATE:
            case GAME_OVER_STATE:
                background.draw(g);
                hurdleManager.draw(g);
                dinosaur.draw(g);
                g.setColor(Color.BLACK);
                g.drawString("HI " + dinosaur.score, 500, 20);
                if (gameState == GAME_OVER_STATE){
                    g.drawImage(gameOverButtonImage, 200, 30, null);
                    g.drawImage(replayButtonImage, 283, 50, null);
                }
                break;
        }
    }

    @Override
    public void run(){
        int fps = 100;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = 0;
        long elapsed;

        int msSleep;
        int nanoSleep;

        long endProcessGame;
        long lag = 0;

        while(true){
            update();
            repaint();
            endProcessGame = System.nanoTime();
            elapsed = (lastTime + msPerFrame - System.nanoTime());
            msSleep = (int)(elapsed / 1000000);
            nanoSleep = (int)(elapsed % 1000000);
            if (msSleep <= 0){
                lastTime = System.nanoTime();
                continue;
            }
            try{
                Thread.sleep(msSleep, nanoSleep);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            lastTime = System.nanoTime();
        }
    }

    public void buttonPressed(){
        switch (gameState) {
            case START_GAME_STATE:
                gameState = GAME_PLAYING_STATE;
                break;
            case GAME_PLAYING_STATE:
                dinosaur.jump();
                break;
            case GAME_OVER_STATE:
                gameState = GAME_PLAYING_STATE;
                resetGame();
                break;
        }
    }

    private void resetGame(){
        hurdleManager.reset();
        dinosaur.dead(false);
        dinosaur.reset();
    }

    @Override
    public void QPressed(boolean Longpress) {
        buttonPressed();
    }

    @Override
    public void WPressed(boolean Longpress) {
        buttonPressed();
    }

    @Override
    public void SPressed(boolean Longpress) {
        buttonPressed();
    }
}