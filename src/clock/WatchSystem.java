package clock;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class WatchSystem extends JPanel implements KeyListener, Runnable {
    private ModeManager Watch;

    private long KeyPressedTime;
    private long KeyReleasedTime;
    private long delay = 1000;

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

    public void keyPressed(KeyEvent e){
        KeyPressedTime = e.getWhen();
    }

    public void keyReleased(KeyEvent e){
        int keycode = e.getKeyCode();
        KeyReleasedTime = e.getWhen();
        boolean Longpress = (KeyPressedTime - KeyReleasedTime) != 0;
        if (keycode == KeyEvent.VK_Q)
            Watch.getCurMode().QPressed(Longpress);
        else if (keycode == KeyEvent.VK_A)
            this.APressed();
        else if (keycode == KeyEvent.VK_W)
            Watch.getCurMode().WPressed(Longpress);
        else if (keycode == KeyEvent.VK_S)
            Watch.getCurMode().SPressed(Longpress);
    }

    private void APressed(){
        Watch.changeToMode((char)(Watch.getIntCurMode() + 1));
        // 이후 curMode가 실행되어야함
    }

    @Override
    public void run() {

    }
}