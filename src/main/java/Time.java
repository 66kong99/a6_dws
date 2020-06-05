
import java.time.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;

public class Time extends JFrame{
    private Thread timeThread;
    private JLabel timeLabel;

    public LocalDateTime curTime = LocalDateTime.now();


    public Time() {


        Dimension dim = new Dimension(300,150);

        JFrame frame = new JFrame("Time");
        frame.setLocation(150,150);
        frame.setPreferredSize(dim);

        JPanel timePanel = new JPanel();
        timeLabel = new JLabel();

        timeLabel.setFont(new Font("Gothic", Font.PLAIN, 35));

        timeLabel.setText("<html>"+curTime.format(DateTimeFormatter.ofPattern("yyyy MM dd E"))+"<br/>"+curTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"</html>");

        timeLabel.setVerticalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);



        timePanel.add(timeLabel);
        frame.add(timePanel);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        timeThread = new Thread();
        timeThread.start();
    }


    public void requestCurTime() {
        // TODO implement here

    }

    public void updateTime() {


        // TODO implement here
        while(true)
        {
            curTime = LocalDateTime.now();
            timeLabel.setText("<html>"+curTime.format(DateTimeFormatter.ofPattern("yyyy MM dd E"))+"<br/>"+curTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))+"</html>");

            try{
                timeThread.sleep(1000);
            }catch(InterruptedException e){
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
}

