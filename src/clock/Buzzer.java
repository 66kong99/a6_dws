package clock;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Buzzer extends Mode{
    private AudioClip beepSound;

    public boolean isBeep;

    public Buzzer() {
        try {
            beepSound = Applet.newAudioClip(new URL("file", "", "data/beep.wav"));
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        isBeep = false;
    }
    public void beep(int repeatTime) {
        isBeep = true;
        for(int i = 0; i < repeatTime; i++){
            beepSound.play();
        }
    }

    public void stopBeep() {
        beepSound.stop();
        isBeep = false;
    }

    @Override
    public void QPressed(boolean Longpress) {

    }

    @Override
    public void WPressed(boolean Longpress) {

    }

    @Override
    public void SPressed(boolean Longpress) {

    }
}