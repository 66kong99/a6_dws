package clock;

import util.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WatchSystem extends JPanel implements MouseListener, KeyListener, Runnable, Serializable {
    public ModeManager Watch;

    private static final Logger logger = Logger.getLogger(WatchSystem.class.getName());

    private final transient BufferedImage backgroundRender;
    private long isLongpress;
    private long timeOut;

    private final Font clockFont;

    private transient Thread thread;

    public WatchSystem() {
        isLongpress = 0;
        timeOut = 0;

        Watch = new ModeManager();

        backgroundRender = Resource.getResourceImage("resources/clock.png");


        clockFont = Resource.getFont(32);

    }

    // Q(81) W(87)
    // A(65) S(83)
    // Longpress == Q, W, S Only
    //

    @Override
    public void keyTyped(KeyEvent e) {
        // NOT using
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void keyPressed(KeyEvent e){
        timeOut = 0;
        isLongpress++;
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();

        boolean Longpress = (isLongpress >= 10);
        if (keycode == KeyEvent.VK_Q)
            Watch.QPressed(Longpress);
        else if (keycode == KeyEvent.VK_A) {
            if (Watch.getIntCurMode() != 5 && Watch.game.gameState != 1)
                this.APressed();
        }
        else if (keycode == KeyEvent.VK_W)
            Watch.WPressed(Longpress);
        else if (keycode == KeyEvent.VK_S)
            Watch.SPressed(Longpress);
        isLongpress = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // NOT using
    }

    @Override
    public void mousePressed(MouseEvent e) {
        timeOut = 0;
        isLongpress = System.nanoTime();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int getX = e.getX();
        int getY = e.getY();

        boolean Longpress = (System.nanoTime() - isLongpress) >= 500000000;
        if(getX < 60) {
            if(348 < getY && getY < 428){ // TOP-LEFT Button
                Watch.QPressed(Longpress);
            }
            else if(548 < getY && getY < 628){ // DOWN-LEFT Button
                this.APressed();
            }
        }
        else if(getX > 665) {
            if(348 < getY && getY < 428){ // TOP-RIGHT Button
                Watch.WPressed(Longpress);
            }
            else if(548 < getY && getY < 628){ // DOWN-RIGHT Button
                Watch.SPressed(Longpress);
            }
        }
        isLongpress = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // NOT using
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // NOT using
    }


    private void APressed(){
        Watch.changeToMode(Watch.nextMode());
        Watch.APressed();
    }

    public void paint(Graphics g){
        g.setColor(Color.decode("#F7F7F7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        g.setFont(clockFont);
        Watch.paint(g);
        setVisible(true);
        g.drawImage(backgroundRender, 0, 0, null);
    }

    @Override
    public void run() {
        while(true){
            Watch.update();
            repaint();
            timeOut += 10; // plus 10 milliseconds
            if (timeOut == 10000){ // TimeOut
                timeOut = 0;
                Watch.returnTimeMode();
            }
            try{
                Thread.sleep(10);
            }catch (InterruptedException e){
                logger.log(Level.WARNING, "WatchSystem Thread Interrupted", e);
                Thread.currentThread().interrupt();
            }
        }
    }

}