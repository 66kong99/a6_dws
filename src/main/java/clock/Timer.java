package clock;

import java.util.*;


public class Timer implements Mode{
    public Calendar timerTime;
    private Buzzer beep;

    private static boolean isPaused;
    private static boolean isSetTimer;
    private static int timerUnit[];
    private int Unit = 0;


    public Timer() {
        this.timerTime = Calendar.getInstance();
        this.timerTime.clear();

        this.isPaused = true;
        this.isSetTimer = false;

        this.timerUnit = new int[3];
        this.beep = new Buzzer();
    }

    public String[] requestTimerTime() {
        if (!isSetTimer) {
            String[] timerBufferArr = new String[3];

            Calendar tempTime = (Calendar) this.timerTime.clone();


            StringBuffer nameBuffer = new StringBuffer();
            StringBuffer timerBuffer = new StringBuffer();
            StringBuffer secBuffer = new StringBuffer();

            nameBuffer.append("Timer");

            timerBufferArr[0] = nameBuffer.toString();

            timerBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "");
            timerBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY));
            timerBuffer.append(":");
            timerBuffer.append(tempTime.get(Calendar.MINUTE) < 10 ? "0" : "");
            timerBuffer.append(tempTime.get(Calendar.MINUTE));

            timerBufferArr[1] = timerBuffer.toString();

            secBuffer.append(":");
            secBuffer.append(tempTime.get(Calendar.SECOND) < 10 ? "0" : "");
            secBuffer.append(tempTime.get(Calendar.SECOND));

            timerBufferArr[2] = secBuffer.toString();

            if (this.isPaused == false && (this.timerTime.get(Calendar.SECOND) != 0 || this.timerTime.get(Calendar.MINUTE) != 0 || this.timerTime.get(Calendar.HOUR_OF_DAY) != 0))
                this.timerTime.add(Calendar.MILLISECOND, -10);

            return timerBufferArr;
        }
        System.out.println("down");
        if (timerUnit[0] == 61){
            timerUnit[1]++;
            timerUnit[0] = 0;
        }
        if (timerUnit[1] == 61){
            timerUnit[1] = 0;
        }
        if (timerUnit[2] == 25){
            timerUnit[2] = 0;
        }
        return new String[]{"Timer", String.format("%02d", timerUnit[2]) + ":" + String.format("%02d", timerUnit[1]), ":" + String.format("%02d", timerUnit[0])};
    }

    public void decreaseTimer() {
        this.isPaused = false;
    }

    public void pauseTimer() {
        this.isPaused = true;
    }

    public void resetTimer() {
        this.isPaused = true;
        this.timerTime.clear();
    }

    public void setTimer(){
        timerTime.set(0, 0, 0, timerUnit[2], timerUnit[1], timerUnit[0]);
        timerUnit[0] = 0;
        timerUnit[1] = 0;
        timerUnit[2] = 0;
    }

    @Override
    public void QPressed(boolean Longpress) {
        if(beep.isBeep == true) {
            beep.stopBeep();

        }

    }

    @Override
    public void APressed() {

    }

    @Override
    public void WPressed(boolean Longpress) {
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }

        if(isSetTimer){
            isSetTimer = false;
            setTimer();
            return;
        }
        resetTimer();

    }

    @Override
    public void SPressed(boolean Longpress) {
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }
        if (Longpress && !isSetTimer){
            isSetTimer = true;
            Unit = 0;
            return;
        }
        if (isSetTimer) {
            if (Longpress)
                Unit++;
            timerUnit[Unit]++;
            return;
        }
        if (isPaused)
            decreaseTimer();
        else
            pauseTimer();
    }

    public void beepTimer(){
        if(isPaused == false && (this.timerTime.get(Calendar.SECOND) ==0 && this.timerTime.get(Calendar.MINUTE) ==0 && this.timerTime.get(Calendar.HOUR_OF_DAY) == 0)) {
            beep.beep(10);
            isPaused = true;
        }
    }

}