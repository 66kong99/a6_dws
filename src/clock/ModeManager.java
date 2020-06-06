package clock;

import java.awt.*;
import java.util.*;

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

    public ModeManager() {
        alarm = new Alarm();
        game = new Game();
        time = new Time();
        timer = new Timer();
        worldtime = new Worldtime();
        stopwatch = new Stopwatch();

        beep = new Buzzer();

        activated = new boolean[6];
        Arrays.fill(activated, true);
        activated[4] = false;
        activated[5] = false;

        curMode = 0; // Timer

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
        return (first * 10) + second;
    }


    public void activateMode(char Mode) {
        activated[Mode] = true;
    }


    public void returnTimeMode() {
// timeout
    }
}

