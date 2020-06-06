package clock;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;
import java.awt.*;
import javax.swing.*;

public class Time extends JFrame implements Mode{
    private Thread timeThread;
    private JLabel timeLabel;
    public LocalDateTime curTime = LocalDateTime.now();

    public Time() {
        Dimension dim = new Dimension(300,150);

        JFrame timeFrame = new JFrame("Time");
        timeFrame.setLocation(300,150);
        timeFrame.setPreferredSize(dim);

        timeLabel = new JLabel();
        timeLabel.setText("<html>"+ curTime.format(DateTimeFormatter.ofPattern("yyyy MM dd E"))+"<br/>"+curTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"</html>");
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = new Font("data/scoreboard.ttf", Font.BOLD, 35);
        timeLabel.setFont(font);

        timeFrame.add(timeLabel);
        timeFrame.pack();
        timeFrame.setVisible(true);
        timeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        timeThread = new Thread();
        timeThread.start();
    }





    public void requestCurTime() {
        // TODO implement here

    }


    public void updateTime() {
        while(true){
            curTime = LocalDateTime.now();
            timeLabel.setText("<html>"+ curTime.format(DateTimeFormatter.ofPattern("yyyy MM dd E"))+"<br/>"+curTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"</html>");

            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    public void increaseTimeValue() {
        // TODO implement here

    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Time time = new Time();
        time.updateTime();
    }

    @Override
    public void QPressed(boolean Longpress) {

    }

    @Override
    public void WPressed(boolean Longpress) {

    }

    @Override
    public void SPressed(boolean Longpress) {

    }
}

