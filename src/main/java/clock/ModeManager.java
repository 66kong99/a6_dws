package clock;

import util.Resource;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import javax.swing.*;

public class ModeManager {
    private Alarm alarm; // 3
    public Game game; // 5
    public Time time; // 0
    private Timer timer; // 2
    private Worldtime worldtime; // 4
    private Stopwatch stopwatch; // 1
    private Buzzer beep;

    private char curMode;
    private boolean activated[];
    private int deactivate[];
    private int count;

    private static int beepCount;
    private boolean isGray;
    private boolean isSet;

    private boolean isSwapMode;

    private Font top, main, sub;


//    public JLabel top, main, sub;

    public ModeManager() {
        time = new Time();
        stopwatch = new Stopwatch();
        timer = new Timer();
        alarm = new Alarm();
        worldtime = new Worldtime(time.curTime);
        game = new Game();

        beep = new Buzzer();
        beepCount = 0;

//        top = new JLabel(time.requestCurTime()[0], SwingConstants.LEFT);
//        main = new JLabel(time.requestCurTime()[1], SwingConstants.LEFT);
//        sub = new JLabel(time.requestCurTime()[2], SwingConstants.LEFT);

        try {
            top = Resource.getFont("resources/scoreboard.ttf", 60);
            main = Resource.getFont("resources/scoreboard.ttf", 170);
            sub = Resource.getFont("resources/scoreboard.ttf", 60);
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

        count = 0;
        isGray = false;
        isSwapMode = false;

    }
    public char getIntCurMode(){ return curMode; }

    public Mode getCurMode(){
        switch(curMode){
            case 0:
                return time;
            case 1:
                return stopwatch;
            case 2:
                return timer;
            case 3:
                return alarm;
            case 4:
                return worldtime;
            case 5:
                return game;
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
        if(game.gameState == 1)
            return;
        if(isSet)
            return;
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
//        System.out.println(deactivate[0] + " " + deactivate[1]);
        return temp; // time didn't deactivated(time's number is 0)
    }

    public String getModeName(int Mode){
        switch(Mode){
            case 0:
                return "Time";
            case 1:
                return "Stopwatch";
            case 2:
                return "Timer";
            case 3:
                return "Alarm";
            case 4:
                return "Worldtime";
            case 5:
                return "Game";
        }
        return null;
    }

    public void update(){
        int tempCount = 0;
        tempCount = Math.max(alarm.updateAlarm(time.curTime), timer.updateTimer());
        tempCount = Math.max(tempCount, time.updateTime());
        stopwatch.updateStopw();

        switch(curMode){
            case 4:
                worldtime.update(time.curTime);
                break;
            case 5:
                game.update();
                break;
            default:
                break;
        }
        if(beepCount == 0)
            beepCount = tempCount;
        else {
            if (beepCount % 100 == 0)
                beep.beep();
            beepCount--;
        }
//        System.out.println(tempCount);
    }

    public void paint(Graphics g){ // "Mode Name", "Hour, Minute", "sec", "O" or "X"
        count ++;
        String[] data = new String[4];
        data = time.requestCurTime();
        if (isSwapMode) {
            data = deactivateMode();
            g.setFont(top);
            g.drawString(data[0], 100, 388);
            g.drawString(data[1], 100, 588);
            g.drawString("->", 570, 388);
            g.drawString("->", 570, 588);
        }
        else if (curMode == 5)// game
            game.paint(g);
        else {
            switch (curMode) {
                case 0:
//                    data = time.requestCurTime(); // Time always update and draw
                    break;
                case 1:
                    data = stopwatch.requestStopwTime();
                    break;
                case 2:
                    data = timer.requestTimerTime();
                    break;
                case 3:
                    data = alarm.requestAlarm();
                    break;
                case 4:
                    data = worldtime.requestWorldtime(time.curTime);
                    break;
                default:
                    break;
            }
//            System.out.println(data[0] + " " + data[1] + " " + data[2] + " " + data[3]);
//            System.out.println(data[3] == "1");

            if (data[3].equals("X"))
                isSet = false;

            if (isGray && !data[3].equals("X")){
                isSet = true;
                g.setColor(Color.GRAY);
            }else
                g.setColor(Color.BLACK);

            g.setFont(top);
            if(curMode == 0){//time keeping mode **** ** ** *** 14
                g.drawString(data[0], 100, 388);
                if(isGray) {
                    if (data[3].equals("6")) {
                        g.drawString(data[0].substring(0, 5), 100, 388);
                        g.setColor(Color.BLACK);
                        g.drawString(String.format("%14s",data[0].substring(5, 14)), 100 , 388);
                    }
                    else if (data[3].equals("5")) {
                        g.drawString(String.format("%8s",data[0].substring(5, 8)), 100 , 388);
                        g.setColor(Color.BLACK);
                        g.drawString(String.format("%5s",data[0].substring(0, 5)), 100 , 388);
                        g.drawString(String.format("%14s",data[0].substring(8, 14)), 100 , 388);
                    }
                    else if (data[3].equals("4")) {
                        g.drawString(String.format("%14s",data[0].substring(8, 14)), 100 , 388);
                        g.setColor(Color.BLACK);
                        g.drawString(String.format("%8s",data[0].substring(0, 8)), 100 , 388);
                    }
                }
            }
            else // other mode
                g.drawString(data[0], 100, 388);

            if (isGray && data[3].equals("3")){
                isSet = true;
                g.setColor(Color.GRAY);
            } else
                g.setColor(Color.BLACK);

            g.setFont(main);
            g.drawString(data[1].substring(0, 2), 95, 600);

            g.setColor(Color.BLACK);
            g.drawString(data[1].substring(2, 3), 265, 600); // :

            if (isGray && data[3].equals("2")){
                isSet = true;
                g.setColor(Color.GRAY);
            } else
                g.setColor(Color.BLACK);

            g.drawString(data[1].substring(3, 5), 340, 600);

            if (isGray && data[3].equals("1")){
                isSet = true;
                g.setColor(Color.GRAY);
            } else
                g.setColor(Color.BLACK);

            g.setFont(sub);
            g.drawString(data[2], 520, 600);

            if (count / 100 == 1) {
                isGray = !isGray;
                count = 0;
            }
        }
    }
    public void APressed(){
        if(beepCount != 0){
            beepCount = 0;
            return;
        }
        getCurMode().APressed();
    }
    public void QPressed(boolean Longpress){
        if(beepCount != 0){
            beepCount = 0;
            return;
        }
        if(isSet)
            return;

        if(isSwapMode == true){
            isSwapMode = false;
        }
        if (Longpress == true && curMode != 0){
            isSwapMode = true;
            deactivateMode();
        }
        else
            getCurMode().QPressed(Longpress);
    }

    public void WPressed(boolean Longpress){
        if(beepCount != 0){
            beepCount = 0;
            return;
        }
        if (isSwapMode){
            activated[curMode] = false;

            curMode = (char)deactivate[0];
            activated[deactivate[0]] = true;
            isSwapMode = false;
        }else
            getCurMode().WPressed(Longpress);
    }

    public void SPressed(boolean Longpress){
        if(beepCount != 0){
            beepCount = 0;
            return;
        }
        if (isSwapMode) {
            activated[curMode] = false;

            curMode = (char)deactivate[1];
            activated[deactivate[1]] = true;
            isSwapMode = false;
        } else
            getCurMode().SPressed(Longpress);
    }

    public void returnTimeMode() {
        if (stopwatch.isPaused == false)
            return;
        if (game.gameState == 1)
            return;
        if (isSet)
            return;
        curMode = 0;
    }
}

