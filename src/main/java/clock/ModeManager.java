package clock;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import javax.swing.*;

public class ModeManager {
    private Alarm alarm; // 1
    private Game game; // 2
    public Time time; // 0
    private Timer timer; // 3
    private Worldtime worldtime; // 4
    private Stopwatch stopwatch; // 5
    private Buzzer beep;

    private char tempMode;
    private char curMode;
    private boolean activated[];
    private int deactivate[];

    private boolean isSwapMode;

    private Font top, main, sub;


//    public JLabel top, main, sub;

    public ModeManager() {
        alarm = new Alarm();
        game = new Game();
        time = new Time();
        timer = new Timer();
        worldtime = new Worldtime(time.curTime);
        stopwatch = new Stopwatch();

        beep = new Buzzer();

//        top = new JLabel(time.requestCurTime()[0], SwingConstants.LEFT);
//        main = new JLabel(time.requestCurTime()[1], SwingConstants.LEFT);
//        sub = new JLabel(time.requestCurTime()[2], SwingConstants.LEFT);

        try {
            top = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 60);
            main = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 170);
            sub = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }

        activated = new boolean[6];
        Arrays.fill(activated, true);
        activated[4] = false;
        activated[5] = false;
        deactivate = new int[2];
        deactivate[0] = 4;
        deactivate[1] = 5;

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

    public char nextMode(){
        for(int i = curMode+1;;) {
            i = i % 6;
            System.out.println(i);
            if (activated[i] == true) {
                System.out.println(i + " : true");
                return (char)i;
            }
            i++;
        }

    }

    public void changeToMode(char Mode) {
        this.curMode = Mode;
    }

    // calling deactivating mode
    public String[] deactivateMode() {
        String temp[] = new String[3];
        for (int i = 0; i < 6; i++)
            if (activated[i] == false)
                if (temp[0] == null) {
                    temp[0] = getModeName(i);
                    deactivate[0] = i;
                } else {
                    temp[1] = getModeName(i);
                    deactivate[1] = i;
                    break;
                }
        System.out.println(deactivate[0] + " " + deactivate[1]);
        return temp; // time didn't deactivated(time's number is 0)
    }

    public String getModeName(int Mode){
        switch(Mode){
            case 0:
                return "Time";
            case 1:
                return "Alarm";
            case 2:
                return "Game";
            case 3:
                return "Timer";
            case 4:
                return "Worldtime";
            case 5:
                return "Stopwatch";
        }
        return null;
    }

    public void activateMode(char Mode) {
        activated[Mode] = true;
    }

    public void update(){
        alarm.compare(time.curTime);
        switch(curMode){
            case 0:
                time.updateTime();
                break;
            case 1:
                alarm.update();
                break;
            case 2:
                game.update();
                break;
            case 3:
                break;
            case 4:
                worldtime.update(time.curTime);
                break;
            case 5:
                stopwatch.updateStopw();
            default:
                break;
        }
    }

    public void paint(Graphics g){
        String[] data = new String[3];
        data = time.requestCurTime();
        if (isSwapMode) {
            data = deactivateMode();
            g.setFont(top);
            g.drawString(data[0], 100, 488);
            g.drawString(data[1], 100, 688);
            g.drawString("->", 570, 488);
            g.drawString("->", 570, 688);
        }
        else if (curMode == 2)// game
            game.paint(g);
        else {
            switch (curMode) {
                case 0:
//                    data = time.requestCurTime(); // Time always update and draw
                    break;
                case 1:
                    data = alarm.requestAlarm();
                    break;
                case 3:
                    data = timer.requestTimerTime();
                    break;
                case 4:
                    data = worldtime.requestWorldtime(time.curTime);
                    break;
                case 5:
                    data = stopwatch.requestStopwTime();
                    break;
                default:
                    break;
            }
//            System.out.println(data[0] + "\n" + data[1] + "\n" + data[2]);
            g.setFont(top);
            g.drawString(data[0], 100, 488);
            g.setFont(main);
            g.drawString(data[1], 95, 700);
            g.setFont(sub);
            g.drawString(data[2], 540, 700);
        }
    }

    public void QPressed(boolean Longpress){
        if(isSwapMode == true){

        }
        if (Longpress == true && curMode != 0){
            isSwapMode = true;
            deactivateMode();
        }
        else
            getCurMode().QPressed(Longpress);
    }

    public void WPressed(boolean Longpress){
        if (isSwapMode){
            activated[curMode] = false;

            curMode = (char)deactivate[0];
            activated[deactivate[0]] = true;
            isSwapMode = false;
        }else
            getCurMode().WPressed(Longpress);
    }

    public void SPressed(boolean Longpress){
        if (isSwapMode) {
            activated[curMode] = false;

            curMode = (char)deactivate[1];
            activated[deactivate[1]] = true;
            isSwapMode = false;
        } else
            getCurMode().SPressed(Longpress);
    }

    public void returnTimeMode() {
        curMode = 0;
    }
}

