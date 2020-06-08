package clock;

import java.util.*;


public class Timer implements Mode{
    public Calendar timerTime;
    private static int timerUnit;
    private Buzzer beep;

    private static boolean isPaused;
    private static boolean isSetTimer;


    public Timer() {
        this.timerTime = Calendar.getInstance();
        this.timerTime.clear();

        this.isPaused = true;
        this.isSetTimer = false;
        this.timerUnit = 1;
        this.beep = new Buzzer();
    }

    public String[] requestTimerTime() {
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

        if(this.isPaused == false && (this.timerTime.get(Calendar.SECOND)!=0 || this.timerTime.get(Calendar.MINUTE)!=0 || this.timerTime.get(Calendar.HOUR_OF_DAY)!=0))
            this.timerTime.add(Calendar.MILLISECOND, -10);

        return timerBufferArr;
    }

    public void increaseTimerValue() {
        switch(this.timerUnit){

            case 1:

                if(this.timerTime.get(Calendar.SECOND) == 0)
                    this.timerTime.add(Calendar.MINUTE, -1);

                this.timerTime.add(Calendar.SECOND, 1);

                break;
            case 2 :
                if(this.timerTime.get(Calendar.MINUTE) == 0)
                    this.timerTime.add(Calendar.HOUR_OF_DAY, -1);

                this.timerTime.add(Calendar.MINUTE, 1);

                break;

            case 3:
                if(this.timerTime.get(Calendar.HOUR_OF_DAY) == 0)
                    this.timerTime.add(Calendar.DATE, -1);

                this.timerTime.add(Calendar.HOUR_OF_DAY, 1);

                break;

            default:
                break;
        }
    }



    public void changeTimerUnit(){
        this.timerUnit++;
        if(timerUnit >= 4)
            this.timerUnit = 1;
    }

    public void decreaseTimer() {
        // TODO implement here
        this.isPaused = false;
    }

    public void pauseTimer() {
        // TODO implement here
        this.isPaused = true;
    }

    public void resetTimer() {
        // TODO implement here
        this.isPaused = true;
        this.timerTime.clear();
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

        }

        if(Longpress == false && isPaused == false){
            resetTimer();
        }

        if(Longpress == true){
            if(isSetTimer == false){
                isSetTimer = true;
            }
            else
                isSetTimer = false;
        }

    }

    @Override
    public void SPressed(boolean Longpress) {
        if(beep.isBeep == true) {
            beep.stopBeep();

        }
        if(Longpress == false && isSetTimer == false){
            if(isPaused == true)
                decreaseTimer();
            else
                pauseTimer();
        }

        if(isSetTimer == true){
            if(Longpress == false)
                increaseTimerValue();
            else
                changeTimerUnit();
        }

    }

    public void beepTimer(){
        if(isPaused == false && (this.timerTime.get(Calendar.SECOND) ==0 && this.timerTime.get(Calendar.MINUTE) ==0 && this.timerTime.get(Calendar.HOUR_OF_DAY) == 0)) {
            beep.beep(10);
            isPaused = true;
        }
    }

}