package clock;

import util.Resource;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Buzzer {
    private AudioClip beepSound;

    public boolean isBeep;

    public Buzzer() {
        try {
            beepSound = Applet.newAudioClip(Resource.class.getClassLoader().getResource("resources/beep.wav"));
        }catch (Exception e){
            e.printStackTrace();
        }
        isBeep = false;
    }
    public void beep(int repeatTime) {
        isBeep = true;
        beepSound.loop();
        try {
            Thread.sleep(200 * repeatTime);
        } catch (Exception e){
            e.printStackTrace();
        }
        beepSound.stop();
    }

    public void stopBeep() {
        beepSound.stop();
        isBeep = false;
    }
}