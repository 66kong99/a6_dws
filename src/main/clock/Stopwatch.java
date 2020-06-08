package clock;

import java.io.File;
import java.io.FileInputStream;
import java.time.DayOfWeek;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Stopwatch extends JFrame implements Mode {
    private JLabel stopwLabel, stopwLabel2,stopwLabel3;
    private Thread stopwThread;
    public Calendar stopwTime;
    public Calendar splitstopwTime;

    private boolean isPaused;
    private boolean isSplit;
    private boolean buttoncount = true; //button switch for starting or pausing stopwatch


    public Stopwatch() {
        this.stopwTime = Calendar.getInstance();
        stopwTime.clear();

        Dimension dim = new Dimension(300,150);

        JFrame stopwFrame = new JFrame("Stopwatch");
        stopwFrame.setLocation(300,150);
        stopwFrame.setPreferredSize(dim);

        JPanel timePanel = new JPanel();
        stopwLabel = new JLabel();
        stopwLabel2 = new JLabel();
        stopwLabel3 = new JLabel();
        stopwLabel.setVerticalAlignment(SwingConstants.CENTER);
        stopwLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stopwLabel2.setVerticalAlignment(SwingConstants.CENTER);
        stopwLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        stopwLabel3.setVerticalAlignment(SwingConstants.CENTER);
        stopwLabel3.setHorizontalAlignment(SwingConstants.CENTER);

        timePanel.add(stopwLabel);
        timePanel.add(stopwLabel2);
        timePanel.add(stopwLabel3);


        try{
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 35);
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 40);
            Font font3 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 10);

            stopwLabel.setFont(font);
            stopwLabel2.setFont(font2);
            stopwLabel3.setFont(font3);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Font error");
        }

        stopwFrame.add(timePanel);
        stopwFrame.pack();
        stopwFrame.setVisible(true);
        stopwFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        isPaused = true;
        isSplit = false;
        stopwThread = new Thread();
        stopwThread.start();

    }


    public void requestStopwTime() {
        if(this.stopwTime.get(Calendar.HOUR_OF_DAY)==0) {
            Calendar tempStopwTime = (Calendar) stopwTime.clone();

            StringBuffer nameBuffer = new StringBuffer(); // stopwatch
            StringBuffer stopwBuffer = new StringBuffer(); //  minute:sec
            StringBuffer csecBuffer = new StringBuffer(); //:csec

            nameBuffer.append("Stopwatch");
            stopwLabel.setText(nameBuffer.toString());

            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE));
            stopwBuffer.append(":");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND));

            stopwLabel2.setText(stopwBuffer.toString());

            csecBuffer.append(":");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) < 100 ? "0" : "");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) / 10);

            stopwLabel3.setText(csecBuffer.toString());

            if (this.isPaused == false)
                this.stopwTime.add(Calendar.MILLISECOND, 10);

        }
    }

    public void updateStopw(){
        while(true){


            /*
            여기서 increase, reset,pause,split 제어가 필요함
             */
            requestStopwTime();// or requestSplit




            try{
                Thread.sleep(10);
            }
            catch(InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
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

    public void requestSplit() {
        this.isSplit = true;
        if(this.isPaused == false && this.isSplit == true) {
            splitstopwTime.set(Calendar.MINUTE, stopwTime.get(Calendar.MINUTE));
            splitstopwTime.set(Calendar.SECOND, stopwTime.get(Calendar.SECOND));
            splitstopwTime.set(Calendar.MILLISECOND, stopwTime.get(Calendar.MILLISECOND));

            Calendar tempStopwTime = (Calendar) splitstopwTime.clone();

            StringBuffer stopwBuffer = new StringBuffer(); //  minute:sec
            StringBuffer csecBuffer = new StringBuffer(); //:csec

            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.MINUTE));
            stopwBuffer.append(":");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND) < 10 ? "0" : "");
            stopwBuffer.append(tempStopwTime.get(Calendar.SECOND));

            stopwLabel2.setText(stopwBuffer.toString());

            csecBuffer.append(":");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) < 100 ? "0" : "");
            csecBuffer.append(tempStopwTime.get(Calendar.MILLISECOND) / 10);

            stopwLabel3.setText(csecBuffer.toString());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.updateStopw();

    }

    @Override
    public void QPressed(boolean Longpress) {

    }

    @Override
    public void APressed() {

    }

    @Override
    public void WPressed(boolean Longpress) {

    }

    @Override
    public void SPressed(boolean Longpress) {

    }
}

/*
    public String[] paint(){
    }
    */

    /*
    @Override
    public void QPressed(boolean Longpress) {
    }
    @Override
    public void WPressed(boolean Longpress) {
        if(Longpress == true)
            resetStopw();
        else
            requestSplit();
    }
    @Override
    public void SPressed(boolean Longpress) {
        if(Longpress == false||buttoncount == true)
            increaseStopw();
        if(Longpress == false||buttoncount == false)
            pauseStopw();
        buttoncount != buttoncount;
    }
    */