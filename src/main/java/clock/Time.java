package clock;

import java.time.DayOfWeek;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


public class Time implements Mode{
    private DayOfWeek[] dayOfWeeks = DayOfWeek.values();
    private int timeUnit = 1;
    private static boolean isSetTime;
    private Buzzer beep;
    public Calendar curTime;


    public Time() {
        this.curTime = Calendar.getInstance();
        this.isSetTime = false;
        beep = new Buzzer();
    }



    public String[] requestCurTime() {
        String[] timeBufferArr = new String[3];

        Calendar tempTime = (Calendar) this.curTime.clone();


        StringBuffer dateBuffer = new StringBuffer();
        StringBuffer timeBuffer = new StringBuffer();
        StringBuffer secBuffer = new StringBuffer();

        dateBuffer.append(tempTime.get(Calendar.YEAR));
        dateBuffer.append(" ");
        dateBuffer.append(tempTime.get(Calendar.MONTH) + 1 < 10 ? "0" : "");
        dateBuffer.append(tempTime.get(Calendar.MONTH)+1);
        dateBuffer.append(" ");
        dateBuffer.append(tempTime.get(Calendar.DATE) < 10 ? "0" : "");
        dateBuffer.append(tempTime.get(Calendar.DATE));
        dateBuffer.append(" ");
        dateBuffer.append(dayOfWeeks[((tempTime.get(Calendar.DAY_OF_WEEK))+5)%7]);


        timeBufferArr[0] = dateBuffer.toString();

        timeBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "");
        timeBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY));
        timeBuffer.append(":");
        timeBuffer.append(tempTime.get(Calendar.MINUTE) < 10 ? "0" : "");
        timeBuffer.append(tempTime.get(Calendar.MINUTE));

        timeBufferArr[1] = timeBuffer.toString();

        secBuffer.append(":");
        secBuffer.append(tempTime.get(Calendar.SECOND) < 10 ? "0" : "");
        secBuffer.append(tempTime.get(Calendar.SECOND));

        timeBufferArr[2] = secBuffer.toString();


        if(!isSetTime)
            this.curTime.add(Calendar.MILLISECOND, 10);

        return timeBufferArr;
    }

    public void updateTime(){

    }




    public void increaseTimeValue() {
        switch(this.timeUnit){

            case 1:

                if(this.curTime.get(Calendar.SECOND) == 0)
                    this.curTime.add(Calendar.MINUTE, -1);

                this.curTime.add(Calendar.SECOND, 1);

                break;
            case 2 :
                if(this.curTime.get(Calendar.MINUTE) == 0)
                    this.curTime.add(Calendar.HOUR_OF_DAY, -1);

                this.curTime.add(Calendar.MINUTE, 1);

                break;

            case 3:
                if(this.curTime.get(Calendar.HOUR_OF_DAY) == 0)
                    this.curTime.add(Calendar.DATE, -1);

                this.curTime.add(Calendar.HOUR_OF_DAY, 1);

                break;

            default:
                break;
        }


    }


    public void changeTimeUnit(){
        timeUnit++;
        if(timeUnit >= 4)
            timeUnit = 1;
    }
    @Override
    public void QPressed(boolean Longpress) {
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }

    }

    @Override
    public void APressed() {
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }
    }

    @Override
    public void WPressed(boolean Longpress) {
        System.out.println("W Pressed");
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }

        if(Longpress && !isSetTime){ //set Time
            System.out.println("W Longpressed");
            isSetTime = true;
            return;
        }

        isSetTime = false;
    }

    @Override
    public void SPressed(boolean Longpress) {
        System.out.println("S Pressed");
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }
        if(!Longpress && isSetTime){
            increaseTimeValue();
            return;
        }

        if (Longpress && isSetTime) {
            System.out.println("S Longpressed");
            changeTimeUnit();
            return;
        }

    }


    public void beepSignalTime(){
        if(this.curTime.get(Calendar.MINUTE) == 0)
            beep.beep(3);
    }
}