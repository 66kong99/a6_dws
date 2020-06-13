package clock;

import sun.util.resources.cldr.es.CalendarData_es_AR;

import java.time.DayOfWeek;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;



public class Time implements Mode{

    private DayOfWeek[] dayOfWeeks = DayOfWeek.values();
    private int timeUnit = 1;
    private String[] dayOfWeek = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};


    private static boolean isSetTime;
    private int beep;
    public Calendar curTime;


    public Time() {
        this.curTime = Calendar.getInstance();
        this.isSetTime = false;
        beep = 0;
    }


    public String[] requestCurTime() {
        String[] timeBufferArr = new String[4];

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
        dateBuffer.append(dayOfWeek[(tempTime.get(Calendar.DAY_OF_WEEK)-1)%7]);


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


        if(!isSetTime) {
            timeBufferArr[3] = "X";
            this.curTime.add(Calendar.MILLISECOND, 10);
        }else
            timeBufferArr[3] = String.format("%d", timeUnit);

        return timeBufferArr;
    }

    public int updateTime(){
        return 0;
    }


    public void increaseTimeValue() {
        switch(this.timeUnit){

            case 1:

                this.curTime.add(Calendar.SECOND, 1);

                if(this.curTime.get(Calendar.SECOND) == 0)
                    this.curTime.add(Calendar.MINUTE, -1);

                break;
            case 2 :

                this.curTime.add(Calendar.MINUTE, 1);

                if(this.curTime.get(Calendar.MINUTE) == 0)
                    this.curTime.add(Calendar.HOUR_OF_DAY, -1);

                break;

            case 3:

                this.curTime.add(Calendar.HOUR_OF_DAY, 1);

                if(this.curTime.get(Calendar.HOUR_OF_DAY) == 0)
                    this.curTime.add(Calendar.DATE, -1);

                break;

            case 4:

                this.curTime.add(Calendar.DATE, 1);

                if(this.curTime.get(Calendar.DATE) == 1)
                    this.curTime.add(Calendar.MONTH, -1);

                break;

            case 5:

                this.curTime.add(Calendar.MONTH, 1);

                if(this.curTime.get(Calendar.MONTH) == Calendar.JANUARY)
                    this.curTime.add(Calendar.YEAR, -1);

                break;

            case 6:

                this.curTime.add(Calendar.YEAR, 1);

                if(this.curTime.get(Calendar.YEAR) == 2100)
                    this.curTime.set(Calendar.YEAR, 1970);
                break;

            default:
                break;
        }


    }

    public void changeTimeUnit(){
        timeUnit++;
        if(timeUnit >= 7)
            timeUnit = 1;
    }


    @Override
    public void QPressed(boolean Longpress) {
    }

    @Override
    public void APressed() {
    }

    @Override
    public void WPressed(boolean Longpress) {
//        System.out.println("W Pressed");

        if(Longpress && !isSetTime){ //set Time
//            System.out.println("W Longpressed");
            isSetTime = true;
            return;
        }

        isSetTime = false;
    }

    @Override
    public void SPressed(boolean Longpress) {
//        System.out.println("S Pressed");
        if(!Longpress && isSetTime){
            increaseTimeValue();
            return;
        }

        if (Longpress && isSetTime) {
//            System.out.println("S Longpressed");
            changeTimeUnit();
            return;
        }

    }

}