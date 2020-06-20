package clock;

import java.applet.Applet;
import java.applet.AudioClip;

public class Buzzer {
    private transient final AudioClip beepSound;

    public Buzzer() {

        beepSound = Applet.newAudioClip(Thread.currentThread().getContextClassLoader().
                getResource("resources/beep.wav"));

    }
    public void beep() {
        beepSound.play();
    }
}