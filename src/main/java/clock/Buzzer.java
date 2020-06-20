package clock;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buzzer {
    private transient AudioClip beepSound;
    private static final Logger LOGGER = Logger.getLogger(Buzzer.class.getName());

    public Buzzer() {
        try {
            beepSound = Applet.newAudioClip(Thread.currentThread().getContextClassLoader().getResource("resources/beep.wav"));
        }catch (NullPointerException e){
            LOGGER.log(Level.WARNING, "Buzzer Thread Interrupted", e);
        }
    }
    public void beep() {
        beepSound.play();
    }
}