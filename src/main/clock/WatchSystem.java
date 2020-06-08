package clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;


public class WatchSystem extends JPanel implements KeyListener, Runnable {
    private ModeManager Watch;

    private long KeyPressedTime;
    private long KeyReleasedTime;
    private long delay = 1000;

    private Thread thread;

    public WatchSystem() {
        KeyPressedTime = 0;
        KeyReleasedTime = 0;
        Watch = new ModeManager();
    }

    // Q(81) W(87)
    // A(65) S(83)
    // Longpress == Q, W, S Only

    @Override
    public void keyTyped(KeyEvent e) {
        // 안씀
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    public void keyPressed(KeyEvent e){
        KeyPressedTime = e.getWhen();
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        KeyReleasedTime = e.getWhen();
        boolean Longpress = (KeyPressedTime - KeyReleasedTime) != 0;
        if (keycode == KeyEvent.VK_Q)
            Watch.QPressed(Longpress);
        else if (keycode == KeyEvent.VK_A)
            this.APressed();
        else if (keycode == KeyEvent.VK_W)
            Watch.getCurMode().WPressed(Longpress);
        else if (keycode == KeyEvent.VK_S)
            Watch.getCurMode().SPressed(Longpress);
    }

    private void APressed(){
        Watch.changeToMode((char)(Watch.getIntCurMode() + 1));
        Watch.getCurMode().APressed();
        // 이후 curMode가 실행되어야함
    }

    public void paint(Graphics g){
        g.setColor(Color.decode("#F7F7F7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        Watch.paint(g);
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
            elapsed = (lastTime + msPerFrame - System.nanoTime()); // 진행된 시간
            timeout = TimeUnit.SECONDS.convert(elapsed, TimeUnit.NANOSECONDS);
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