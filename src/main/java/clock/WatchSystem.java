package clock;

import util.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.Buffer;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import util.Resource;


public class WatchSystem extends JPanel implements MouseListener, KeyListener, Runnable {
    public ModeManager Watch;

    private BufferedImage background;
    private long isLongpress;
    private long timeOut;

    private Font font;

    private Thread thread;

    public WatchSystem() {
        isLongpress = 0;
        timeOut = 0;

        Watch = new ModeManager();

        background = Resource.getResourceImage("resources/clock.png");

        try {
            font = Resource.getFont("resources/scoreboard.ttf", 32);
        }catch (Exception e){
            e.printStackTrace();
        }

//        add(Watch.top);
//        add(Watch.main);
//        add(Watch.sub);
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
//        System.out.println(isLongpress);
        isLongpress++;
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();

        boolean Longpress = (isLongpress >= 10);
//        System.out.println(Longpress);
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
//        System.out.println(e.getX() + ":" +  e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        timeOut = 0;
//        System.out.println(System.nanoTime());
        isLongpress = System.nanoTime();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println(System.nanoTime() - isLongpress);
        int getX = e.getX();
        int getY = e.getY();

        boolean Longpress = (System.nanoTime() - isLongpress) >= 500000000;
//        System.out.println(Longpress);
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    private void APressed(){
        Watch.changeToMode(Watch.nextMode());
        Watch.APressed();
    }

    public void paint(Graphics g){
        g.setColor(Color.decode("#F7F7F7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        g.setFont(font);
        Watch.paint(g);
        setVisible(true);
        g.drawImage(background, 0, 0, null);
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
                e.printStackTrace();
            }
        }
    }

}