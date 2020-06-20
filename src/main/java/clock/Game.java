package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import util.Resource;

public class Game extends JPanel implements Mode, Runnable, Serializable {
    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;

    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private final Background backgroundRender;
    private final Dinosaur dinosaur;
    private final Hurdle hurdleManager;
    private transient Thread thread;
    float floatSpeed = 0.0f;

    public int gameState = START_GAME_STATE;

    private final transient BufferedImage replayButtonImage;
    private final transient BufferedImage gameOverButtonImage;

    public Game(){
        dinosaur = new Dinosaur();
        backgroundRender = new Background(550, dinosaur);
        dinosaur.setSpeedX(0);
        replayButtonImage = Resource.getResourceImage("resources/replay_button.png");
        gameOverButtonImage = Resource.getResourceImage("resources/gameover_text.png");
        hurdleManager = new Hurdle(dinosaur);
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void update(){
        if (gameState == GAME_PLAYING_STATE){
            floatSpeed += 0.001f;
            backgroundRender.update();
            dinosaur.update();
            hurdleManager.update(floatSpeed);
            if(hurdleManager.isCollision()){
                dinosaur.dead(true);
                gameState = GAME_OVER_STATE;
            }
            dinosaur.setSpeedX(Math.min((int) (floatSpeed * floatSpeed), (int)floatSpeed));
        }

    }

    public void paint(Graphics g){

        switch(gameState){
            case START_GAME_STATE:
                dinosaur.draw(g);
                break;
            case GAME_PLAYING_STATE:
            case GAME_OVER_STATE:
                backgroundRender.draw(g);
                hurdleManager.draw(g);
                dinosaur.draw(g);
                g.setColor(Color.BLACK);
                g.drawString("SCORE : " + dinosaur.score, 450, 320);
                if (gameState == GAME_OVER_STATE){
                    g.drawImage(gameOverButtonImage, 275, 430, null);
                    g.drawImage(replayButtonImage, 357, 450, null);
                }
                break;
            default:
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


        while(true){
            update();
            repaint();
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
                logger.log(Level.WARNING, "Game Thread Interrupted", e);
                Thread.currentThread().interrupt();
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
            default:
                break;
        }
    }

    private void resetGame(){
        hurdleManager.reset();
        dinosaur.dead(false);
        dinosaur.reset();
        floatSpeed = 0.0f;
    }

    @Override
    public void APressed() {

    }

    @Override
    public void QPressed(boolean Longpress) {

    }

    @Override
    public void WPressed(boolean Longpress) {
    }

    @Override
    public void SPressed(boolean Longpress) {
        buttonPressed();
    }
}

