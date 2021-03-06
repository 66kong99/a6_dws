package clock;

import java.util.Calendar;

public class Timer implements Mode{
    public transient Calendar timerTime;

    private transient boolean isPaused;
    private transient boolean isSetTimer;
    private transient int timerUnit = 1;


    public Timer() {
        this.timerTime = Calendar.getInstance();
        this.timerTime.clear();

        isPaused = true;
        isSetTimer = false;

    }

    public String[] requestTimerTime() {

        String[] timerBufferArr = new String[4];

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

        if(isSetTimer)
            timerBufferArr[3] = String.format("%d", timerUnit);
        else
            timerBufferArr[3] = "X";
        return timerBufferArr;
    }

    public void decreaseTimer() {
        if(!(this.timerTime.get(Calendar.SECOND) ==0 && this.timerTime.get(Calendar.MINUTE) ==0 && this.timerTime.get(Calendar.HOUR_OF_DAY) == 0))
          this.isPaused = false;
    }

    public void pauseTimer() {
        this.isPaused = true;
    }

    public void resetTimer() {
        this.isPaused = true;
        this.timerTime.clear();
    }

    // W(LP) : setting mode
    // S = Start
    // S(LP) = changeUnit
    // W = reset

    public void changeTimerUnit(){
        timerUnit++;
        if(timerUnit >= 4)
            timerUnit = 1;
    }

    public void increaseTimerValue(){
       switch(this.timerUnit){
           case 1:

               this.timerTime.add(Calendar.SECOND, 1);

               if(this.timerTime.get(Calendar.SECOND) == 0)
                   this.timerTime.add(Calendar.MINUTE, -1);
               break;

           case 2 :

               this.timerTime.add(Calendar.MINUTE, 1);

               if(this.timerTime.get(Calendar.MINUTE) == 0)
                   this.timerTime.add(Calendar.HOUR_OF_DAY, -1);

               break;

           case 3:

               this.timerTime.add(Calendar.HOUR_OF_DAY, 1);

               if(this.timerTime.get(Calendar.HOUR_OF_DAY) == 0)
                   this.timerTime.add(Calendar.DATE, -1);

               break;

           default:
               break;
       }
    }
    @Override
    public void QPressed(boolean Longpress) {
    }

    @Override
    public void APressed() {
    }

    @Override
    public void WPressed(boolean Longpress) { // C
        if(Longpress){
            if(!isSetTimer) {
                if((this.timerTime.get(Calendar.SECOND) ==0 && this.timerTime.get(Calendar.MINUTE) ==0 && this.timerTime.get(Calendar.HOUR_OF_DAY) == 0)&&this.isPaused == true) {
                    isSetTimer = true;
                    timerUnit = 1;
                    return;
                }
            }
        }else{
            if(isSetTimer) {
                isSetTimer = false;
                return;
            }

            if(isPaused)
                resetTimer();
        }
    }

    @Override
    public void SPressed(boolean Longpress) { // D
        if(Longpress){
            if(isSetTimer){
                changeTimerUnit();
                return;
            }
        }else{
            if(isSetTimer){
                increaseTimerValue();
                return;
            }

            if(isPaused)
                decreaseTimer();
            else
                pauseTimer();
        }
    }

    public int updateTimer(){
        if(isPaused == false && (this.timerTime.get(Calendar.SECOND) ==0 && this.timerTime.get(Calendar.MINUTE) ==0 && this.timerTime.get(Calendar.HOUR_OF_DAY) == 0)) {
            isPaused = true;
            return 1000;
        }
        if (this.isPaused == false && (this.timerTime.get(Calendar.SECOND) != 0 || this.timerTime.get(Calendar.MINUTE) != 0 || this.timerTime.get(Calendar.HOUR_OF_DAY) != 0))
            this.timerTime.add(Calendar.MILLISECOND, -10);
        return 0;
    }

}