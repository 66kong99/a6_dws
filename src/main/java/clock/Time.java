package clock;

import java.time.DayOfWeek;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


public class Time extends JFrame implements Mode{
    private DayOfWeek[] dayOfWeeks = DayOfWeek.values();
    private JLabel timeLabel, timeLabel2;
    private int timeUnit = 1;
    public Calendar curTime;


    public Time() {
        this.curTime = Calendar.getInstance();
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

        this.curTime.add(Calendar.MILLISECOND, 5);

        return timeBufferArr;
    }


    public void updateTime() {
        requestCurTime();
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

    }

    @Override
    public void APressed() {

    }

    @Override
    public void WPressed(boolean Longpress) {
        if(Longpress == true){

        }
    }

    @Override
    public void SPressed(boolean Longpress) {
        if(Longpress == false)
            increaseTimeValue();
        else
            updateTime();
    }
}