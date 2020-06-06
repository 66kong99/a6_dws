package clock;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Buzzer {
    private AudioClip beepSound;

    public Buzzer() {
        try {
            beepSound = Applet.newAudioClip(new URL("file", "", "data/beep.wav"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
    public void beep(int repeatTime) {
        for(int i = 0; i < repeatTime; i++){
            beepSound.play();
        }
    }

    public void stopBeep() {
        beepSound.stop();
    }

}