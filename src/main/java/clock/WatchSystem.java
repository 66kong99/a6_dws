package clock;

import util.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.Buffer;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class WatchSystem extends JPanel implements KeyListener, Runnable {
    public ModeManager Watch;

    private BufferedImage background;
    private int isLongpress;

    private Font font;

    private Thread thread;

    public WatchSystem() {
        isLongpress = 0;

        Watch = new ModeManager();

        background = Resource.getResourceImage("data/clock.png");

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 32);
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
        System.out.println(isLongpress);
        isLongpress++;
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();

        boolean Longpress = (isLongpress >= 10);
        System.out.println(Longpress);
        if (keycode == KeyEvent.VK_Q)
            Watch.QPressed(Longpress);
        else if (keycode == KeyEvent.VK_A)
            this.APressed();
        else if (keycode == KeyEvent.VK_W)
            Watch.WPressed(Longpress);
        else if (keycode == KeyEvent.VK_S)
            Watch.SPressed(Longpress);
        isLongpress = 0;
    }

    private void APressed(){
        System.out.println("APressed");
        Watch.changeToMode(Watch.nextMode());
        Watch.getCurMode().APressed();
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
        int fps = 100;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = 0;
        long elapsed;
        long timeout = 0;

        int msSleep;
        int nanoSleep;
        
        long endProcess;
        long lag = 0;

        while(true){
            Watch.update();
            repaint();
            endProcess = System.nanoTime();
            elapsed = (lastTime + msPerFrame - System.nanoTime());
            timeout = TimeUnit.SECONDS.convert(elapsed, TimeUnit.NANOSECONDS);
//            System.out.println(timeout + "\n" + elapsed);
            if (timeout == 10){ // TimeOut
                timeout = 0;
                Watch.returnTimeMode();
            }
            msSleep = (int)(elapsed / 1000000);
            if(msSleep <= 0){
                lastTime = System.nanoTime();
                continue;
            }
            try{
                Thread.sleep(msSleep);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            lastTime = System.nanoTime();

        }

    }
}