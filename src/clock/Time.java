package clock;

import java.time.DayOfWeek;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


public class Time extends JFrame{
    private DayOfWeek[] dayOfWeeks = DayOfWeek.values();
    private Thread timeThread;
    private JLabel timeLabel, timeLabel2;
    private int timeUnit = 1;
    public Calendar curTime;


    public Time() {
        this.curTime = Calendar.getInstance();

        Dimension dim = new Dimension(300,150);

        JFrame timeFrame = new JFrame("Time");
        timeFrame.setLocation(300,150);
        timeFrame.setPreferredSize(dim);

        JPanel timePanel = new JPanel();
        timeLabel = new JLabel();
        timeLabel2 = new JLabel();

        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel2.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        timePanel.add(timeLabel);
        timePanel.add(timeLabel2);


        try{
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 25);
            Font font2 = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("data/scoreboard.ttf"))).deriveFont(Font.PLAIN, 40);

            timeLabel.setFont(font);
            timeLabel2.setFont(font2);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Font error");
        }

        timeFrame.add(timePanel);
        timeFrame.pack();
        timeFrame.setVisible(true);
        timeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timeThread = new Thread();
        timeThread.start();
    }



    public void requestCurTime() {
        Calendar tempTime = (Calendar) this.curTime.clone();


        StringBuffer dateBuffer = new StringBuffer();
        StringBuffer timeBuffer = new StringBuffer();

        dateBuffer.append(tempTime.get(Calendar.YEAR));
        dateBuffer.append(" ");
        dateBuffer.append(tempTime.get(Calendar.MONTH) + 1 < 10 ? "0" : "");
        dateBuffer.append(tempTime.get(Calendar.MONTH)+1);
        dateBuffer.append(" ");
        dateBuffer.append(tempTime.get(Calendar.DATE) < 10 ? "0" : "");
        dateBuffer.append(tempTime.get(Calendar.DATE));
        dateBuffer.append(" ");
        dateBuffer.append(dayOfWeeks[((tempTime.get(Calendar.DAY_OF_WEEK))+5)%7]);


        timeLabel.setText(dateBuffer.toString());

        timeBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "");
        timeBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY));
        timeBuffer.append(":");
        timeBuffer.append(tempTime.get(Calendar.MINUTE) < 10 ? "0" : "");
        timeBuffer.append(tempTime.get(Calendar.MINUTE));
        timeBuffer.append(":");
        timeBuffer.append(tempTime.get(Calendar.SECOND) < 10 ? "0" : "");
        timeBuffer.append(tempTime.get(Calendar.SECOND));

        timeLabel2.setText(timeBuffer.toString());

        this.curTime.add(Calendar.SECOND, 1);
    }


    public void updateTime() {
        while(true){


            requestCurTime();

            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
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

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Time time = new Time();
        time.changeTimeUnit();
        time.changeTimeUnit();
        time.increaseTimeValue();
        time.updateTime();

    }
    /*
    @Override
    public void QPressed(boolean Longpress) {

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

    */

}