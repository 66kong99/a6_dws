package clock;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import javax.swing.*;

public class ModeManager {
    private Alarm alarm; // 1
    private Game game; // 2
    private Time time; // 0
    private Timer timer; // 3
    private Worldtime worldtime; // 4
    private Stopwatch stopwatch; // 5
    private Buzzer beep;

    private char tempMode;
    private char curMode;
    private boolean activated[];

    private boolean isSwapMode;

    private JLabel top, main, sub;

    public ModeManager() {
        alarm = new Alarm();
        game = new Game();
        time = new Time();
        timer = new Timer();
        worldtime = new Worldtime();
        stopwatch = new Stopwatch();

        beep = new Buzzer();

        top = new JLabel(time.requestCurTime()[0], SwingConstants.LEFT);
        main = new JLabel(time.requestCurTime()[1], SwingConstants.LEFT);
        sub = new JLabel(time.requestCurTime()[2], SwingConstants.LEFT);

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 32);
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 85);
            Font font3 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 28);
            top.setFont(font);
            main.setFont(font2);
            sub.setFont(font3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        activated = new boolean[6];
        Arrays.fill(activated, true);
        activated[4] = false;
        activated[5] = false;

        curMode = 0; // Timer

        isSwapMode = false;

    }
    public char getIntCurMode(){ return curMode; }

    public Mode getCurMode(){
        switch(curMode){
            case 0:
                return time;
            case 1:
                return alarm;
            case 2:
                return game;
            case 3:
                return timer;
            case 4:
                return worldtime;
            case 5:
                return stopwatch;
            default:
                break;
        }
        return null;
    }

    public void changeToMode(char Mode) {
        this.curMode = Mode;
    }

    // Swap Mode 실행시 deactivate되어있는 Mode들을 불러와준다
    public int deactivateMode() {
        int first = 0, second = 0;
        for(int i = 0; i < 6; i++)
            if (activated[i] == false)
                if(first == 0)
                    first = i;
                else {
                    second = i;
                    break;
                }
        return (first * 10) + second; // 0인 Time은 deactivate되지 않으므로 안전하다
    }


    public void activateMode(char Mode) {
        activated[Mode] = true;
    }

    public void update(){
        alarm.compare(time.curTime);
        switch(curMode){
            case 0:
                time.update();
                break;
            case 1:
                alarm.update();
                break;
            case 2:
                game.update();
                break;
            case 3:
                return timer;
            case 4:
                return worldtime;
            case 5:
                return stopwatch;
            default:
                break;
        }
        // getCurMode().update();
        // method 이름이 통일되면 이거로 하면 된다
    }

    public void paint(Graphics g){
        String[] data = new String[3];
        if (curMode == 2) // game
            game.paint(g);
        else
            switch(curMode) {
                case 0:
                    data = time.requestCurTime();
                    break;
                case 1:
                    data = alarm.requestAlarm();
                    break;
                case 3:
                    data = timer.paint();
                    break;
                case 4:
                    data = worldtime.paint();
                    break;
                case 5:
                    data = stopwatch.paint();
                    break;
                default:
                    break;
            }
        // getCurMode().paint();
        // method 이름이 통일되면 이거로 하면 된다

        top.setText(data[0]);
        main.setText(data[1]);
        sub.setText(data[2]);
    }

    public void QPressed(boolean Longpress){
        if(isSwapMode == true){

        }
        if (Longpress == true){
            deactivateMode();
        }
        else
            getCurMode().QPressed(Longpress);
    }

    public void returnTimeMode() {
// timeout
    }
}

