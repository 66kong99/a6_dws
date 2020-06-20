package clock;

import util.Resource;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Arrays;

public class ModeManager {
    private transient final Alarm alarm; // 3
    public transient final Game game; // 5
    private transient final Time time; // 0
    private transient final Timer timer; // 2
    private transient final Worldtime worldtime; // 4
    private transient final Stopwatch stopwatch; // 1
    private transient final Buzzer beep;

    private transient char curMode;
    private transient final boolean[] activated;
    private transient final int[] deactivate;
    private transient int count;

    private transient int beepCount;
    private transient boolean isGray;
    private transient boolean isSet;

    private transient boolean isSwapMode;

    private transient final Font top;
    private transient final Font main;
    private transient final Font sub;

    private transient static final int BEEP_CYCLE = 100;

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


        top = Resource.getFont(60);
        main = Resource.getFont(170);
        sub = Resource.getFont(60);


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
            if (activated[i]) {
                return (char)i;
            }
            i++;
        }

    }

    public void changeToMode(char Mode) {
        if(game.getGameState() == 1) {
            return;
        }
        if(isSet) {
            return;
        }
        this.curMode = Mode;
    }

    // calling deactivating mode
    public String[] deactivateMode() {
        String temp[] = new String[3];
        for (int i = 0; i < 6; i++) {
            if (activated[i] == false) {
                if (temp[0] == null) {
                    temp[0] = getModeName(i);
                    deactivate[0] = i;
                } else {
                    temp[1] = getModeName(i);
                    deactivate[1] = i;
                    break;
                }
            }
        }
//        System.out.println(deactivate[0] + " " + deactivate[1]);
        return temp; // time didn't deactivated(time's number is 0)
    }

    public String getModeName(int Mode){
        String temp;
        switch(Mode){
            case 0:
                temp = "Time";
                break;
            case 1:
                temp = "Stopwatch";
                break;
            case 2:
                temp = "Timer";
                break;
            case 3:
                temp = "Alarm";
                break;
            case 4:
                temp = "Worldtime";
                break;
            case 5:
                temp = "Game";
                break;
            default:
                temp = "ERROR";
                break;
        }
        return temp;
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

        beepCount = Math.max(tempCount, beepCount);

        if(beepCount != 0){
            if (beepCount % BEEP_CYCLE == 0) {
                beep.beep();
            }
            beepCount--;
        }

        //isGray 1 second update
        if (count / 100 == 1) {
            isGray = !isGray;
            count = 0;
        }
    }

    public String[] requestData(String[] data){
        // request data
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
        return data;
    }

    public void paintBlink(Graphics graphics, String[] data){
        if (data[3].equals("6")) {
            graphics.drawString(data[0].substring(0, 5), 100, 388);
            graphics.setColor(Color.BLACK);
            graphics.drawString(String.format("%14s",data[0].substring(5, 14)), 100 , 388);
        }
        else if (data[3].equals("5")) {
            graphics.drawString(String.format("%8s",data[0].substring(5, 8)), 100 , 388);
            graphics.setColor(Color.BLACK);
            graphics.drawString(String.format("%5s",data[0].substring(0, 5)), 100 , 388);
            graphics.drawString(String.format("%14s",data[0].substring(8, 14)), 100 , 388);
        }
        else if (data[3].equals("4")) {
            graphics.drawString(String.format("%14s",data[0].substring(8, 14)), 100 , 388);
            graphics.setColor(Color.BLACK);
            graphics.drawString(String.format("%8s",data[0].substring(0, 8)), 100 , 388);
        }
    }

    public void paintGray(Graphics graphics, boolean grayCond){
        if(grayCond == true){
            isSet = true;
            graphics.setColor(Color.GRAY);
        }
        else {
            graphics.setColor(Color.BLACK);
        }
    }


    // "Mode Name", "Hour, Minute", "sec", "O" or "X"
    public void paint(Graphics graphics){
        count ++;
        String[] data = new String[4];
        data = time.requestCurTime();
        if (isSwapMode) {
            data = deactivateMode();
            graphics.setFont(top);
            graphics.drawString(data[0], 100, 388);
            graphics.drawString(data[1], 100, 588);
            graphics.drawString("->", 570, 388);
            graphics.drawString("->", 570, 588);
            return;
        }

        data = requestData(data);
        if(curMode == 5){
            game.paint(graphics);
            return;
        }
        // start of top
        if (data[3].equals("X")) {
            isSet = false;
        }

        paintGray(graphics, isGray && !data[3].equals("X"));

        graphics.setFont(top);
        if(curMode == 0){//time keeping mode **** ** ** *** 14
            graphics.drawString(data[0], 100, 388);
            if(isGray) {
                paintBlink(graphics, data);
            }
        }
        else { // other mode
            graphics.drawString(data[0], 100, 388);
        }// end of Top

        // start of main

        paintGray(graphics,isGray && data[3].equals("3"));
        graphics.setFont(main);
        graphics.drawString(data[1].substring(0, 2), 95, 600);

        graphics.setColor(Color.BLACK);
        graphics.drawString(data[1].substring(2, 3), 265, 600); // :


        paintGray(graphics, isGray && data[3].equals("2"));
        graphics.drawString(data[1].substring(3, 5), 340, 600);


        paintGray(graphics, isGray && data[3].equals("1"));
        // end of main

        // sub
        graphics.setFont(sub);
        graphics.drawString(data[2], 520, 600);

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
        if(isSet || game.getGameState() == 1)
            return;

        if(isSwapMode == true){
            isSwapMode = false;
        }
        if (Longpress == true && curMode != 0){
            isSwapMode = true;
            deactivateMode();
        }
        else {
            getCurMode().QPressed(Longpress);
        }
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
        }else {
            getCurMode().WPressed(Longpress);
        }
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
        } else {
            getCurMode().SPressed(Longpress);
        }
    }

    public void returnTimeMode() {
        if (!stopwatch.isPaused) {
            return;
        }
        if (game.getGameState() == 1) {
            return;
        }
        if (isSet) {
            return;
        }
        curMode = 0;
    }
}

