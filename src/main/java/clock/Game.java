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

    private static final int WIDTH = 550;

    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    private transient final Background backgroundRender;
    private transient final Dinosaur dinosaur;
    private transient final Hurdle hurdleManager;
    private transient Thread thread;
    private transient float floatSpeed;

    public transient int gameState = START_GAME_STATE;

    private final transient BufferedImage replayImage;
    private final transient BufferedImage gameOverImage;

    public Game(){
        floatSpeed = 0;
        dinosaur = new Dinosaur();
        backgroundRender = new Background(WIDTH, dinosaur);
        dinosaur.setSpeedX(0);
        replayImage = Resource.getResourceImage("resources/replay_button.png");
        gameOverImage = Resource.getResourceImage("resources/gameover_text.png");
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
            dinosaur.setSpeedX((int)Math.min((double)(floatSpeed * floatSpeed), floatSpeed));
        }

    }
    @Override
    public void paint(Graphics g){
        if (gameState == START_GAME_STATE) {
            dinosaur.draw(g);
        }
        else {
            backgroundRender.draw(g);
            hurdleManager.draw(g);
            dinosaur.draw(g);
            g.setColor(Color.BLACK);
            g.drawString("SCORE : " + dinosaur.getGameScore(), 450, 320);
            if (gameState == GAME_OVER_STATE){
                g.drawImage(gameOverImage, 275, 430, null);
                g.drawImage(replayImage, 357, 450, null);
            }
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
                LOGGER.log(Level.WARNING, "Game Thread Interrupted", e);
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

