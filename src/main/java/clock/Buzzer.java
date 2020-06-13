package clock;

import util.Resource;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Buzzer {
    private AudioClip beepSound;

    public Buzzer() {
        try {
            beepSound = Applet.newAudioClip(Resource.class.getClassLoader().getResource("resources/beep.wav"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void beep() {
        beepSound.play();
    }
}