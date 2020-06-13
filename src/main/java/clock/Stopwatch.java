package clock;

import java.io.File;
import java.io.FileInputStream;
import java.time.DayOfWeek;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Stopwatch implements Mode {
    public Calendar stopwTime;
    public Calendar splitstopwTime;

    public static boolean isPaused;
    private boolean isSplit;

    public Stopwatch() {
        this.stopwTime = Calendar.getInstance();
        stopwTime.clear();

        isPaused = true;
        isSplit = false;
    }


    public String[] requestStopwTime() {
        String[] stopwBufferArr = new String[4];
        if(this.stopwTime.get(Calendar.HOUR_OF_DAY)==0) {

            Calendar tempStopwTime = (Calendar) stopwTime.clone();

            StringBuffer nameBuffer = new StringBuffer(); // stopwatch
            StringBuffer stopwBuffer = new StringBuffer(); //  minute:sec
            StringBuffer csecBuffer = new StringBuffer(); //:csec

            nameBuffer.append("Stopwatch");
            stopwBufferArr[0] = nameBuffer.toString();

            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE));
            stopwBuffer.append(":");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND));

            stopwBufferArr[1] = stopwBuffer.toString();

            csecBuffer.append(":");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) < 100 ? "0" : "");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) / 10);

            stopwBufferArr[2] = csecBuffer.toString();

            if (this.isPaused == false)
                this.stopwTime.add(Calendar.MILLISECOND, 10);

            stopwBufferArr[3] = "X";

        }
        return stopwBufferArr;
    }

    public void updateStopw(){

    }


    public void resetStopw() {
        // TODO implement here
        if(this.isPaused == true)
            this.stopwTime.clear();
    }

    public void increaseStopw() {
        // TODO implement here
        this.isPaused = false;
        this.isSplit = false;

    }

    public void pauseStopw() {
        // TODO implement here
        this.isPaused = true;

    }

    public String[] requestSplit() {
        String[] splitBufferArr = new String[4];
        this.isSplit = true;

        if(this.isPaused == false && this.isSplit == true) {

            splitstopwTime.set(Calendar.MINUTE, stopwTime.get(Calendar.MINUTE));
            splitstopwTime.set(Calendar.SECOND, stopwTime.get(Calendar.SECOND));
            splitstopwTime.set(Calendar.MILLISECOND, stopwTime.get(Calendar.MILLISECOND));

            Calendar tempStopwTime = (Calendar) splitstopwTime.clone();

            StringBuffer nameBuffer = new StringBuffer();
            StringBuffer stopwBuffer = new StringBuffer(); //  minute:sec
            StringBuffer csecBuffer = new StringBuffer(); //:csec

            nameBuffer.append("Split");
            splitBufferArr[0] = nameBuffer.toString();


            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE));
            stopwBuffer.append(":");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND));

            splitBufferArr[1] = stopwBuffer.toString();

            csecBuffer.append(":");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) < 100 ? "0" : "");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) / 10);

            splitBufferArr[2] = csecBuffer.toString();

        }
        splitBufferArr[3] = "X";
        return splitBufferArr;
    }


    @Override
    public void QPressed(boolean Longpress) {
    }

    @Override
    public void APressed() {

    }

    @Override
    public void WPressed(boolean Longpress) {
        if(isPaused == true)
            resetStopw();
        else
            requestSplit();
    }
    @Override
    public void SPressed(boolean Longpress) { // D
        if(Longpress == false&&isPaused == true) {
            increaseStopw();
            return;
        }
        if(Longpress == false&&isPaused == false) {
            pauseStopw();
            return;
        }
    }
}