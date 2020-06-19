package clock;

import java.applet.Applet;
import java.applet.AudioClip;

public class Buzzer {
    private AudioClip beepSound;

    public Buzzer() {
        try {
            beepSound = Applet.newAudioClip(Thread.currentThread().getContextClassLoader().getResource("resources/beep.wav"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void beep() {
        beepSound.play();
    }
}