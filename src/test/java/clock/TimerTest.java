package clock;

import com.sun.java.swing.plaf.windows.WindowsInternalFrameTitlePane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {
    @Test
    void requestTimerTime() {
        Timer temp = new Timer();
        Calendar tempCal = (Calendar)temp.timerTime.clone();

        temp.WPressed(true); // into settimer
        for(int i = 0; i < 5; i++)
            temp.SPressed(false); // timer set 5second
        temp.WPressed(false); // out settimer

        assertNotEquals(tempCal, temp.timerTime);
    }

    @Test
    void decreaseTimer(){
        Timer temp = new Timer();
        try {
            Field field = temp.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            temp.WPressed(true); // into settimer
            for (int i = 0; i < 5; i++)
                temp.SPressed(false); // timer set 5second
            temp.WPressed(false); // out settimer
            temp.decreaseTimer(); // timer start => isPaused = false

            boolean ret = (boolean) field.get(temp);

            assertEquals(false, ret);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void pauseTimer() {
        Timer temp = new Timer();
        try{
            Field field = temp.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            temp.WPressed(true); // into settimer
            for(int i = 0; i < 5; i++)
                temp.SPressed(false); // timer set 5second
            temp.WPressed(false); // out settimer
            temp.decreaseTimer(); // timer start => isPaused = false

            boolean ret = (boolean) field.get(temp);

            assertEquals(false, ret);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void resetTimer() {
        Timer temp = new Timer();
        Calendar tempCal = temp.timerTime;

        temp.WPressed(true); // into settimer
        for(int i = 0; i < 5; i++)
            temp.SPressed(false); // timer set 5second
        temp.WPressed(false); // out settimer
        temp.resetTimer(); // reset timer

        assertEquals(tempCal, temp.timerTime);

    }

    @Test
    void changeTimerUnit(){
        Timer temp = new Timer();
        try {
            Field field = temp.getClass().getDeclaredField("timerUnit");
            field.setAccessible(true);

            int tempTU =  (int)(field.get(temp));
            assertEquals(1, tempTU);

            temp.WPressed(true);
            for(int i = 1 ; i<=2;i++) {
                temp.changeTimerUnit(); // add timerunit by 1
                int tempTU2 = (int)(field.get(temp));
                assertEquals(i+1, tempTU2); //compare timerunit and expected value
            }
            temp.changeTimerUnit();
            int tempTU3 = (int)(field.get(temp));
            assertEquals(1, tempTU3);


        }
        catch(NoSuchFieldException e){
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }


    @Test
    void increaseTimerValue(){
        Timer temp = new Timer();//isSetTime = false;
        temp.WPressed(true);//setTime

        //test set second(59->0)
        temp.timerTime.set(Calendar.SECOND, 59);
        int temp1 = temp.timerTime.get(Calendar.MINUTE);
        temp.increaseTimerValue();
        assertEquals(0, temp.timerTime.get(Calendar.SECOND));
        assertEquals(temp1, temp.timerTime.get(Calendar.MINUTE));

        //test set minute(59->0)
        temp.timerTime.set(Calendar.MINUTE, 59);
        int temp2 = temp.timerTime.get(Calendar.HOUR_OF_DAY);
        temp.changeTimerUnit();
        temp.increaseTimerValue();
        assertEquals(0, temp.timerTime.get(Calendar.MINUTE));
        assertEquals(temp2, temp.timerTime.get(Calendar.HOUR_OF_DAY));

        //test set hour(23->0)
        temp.timerTime.set(Calendar.HOUR_OF_DAY, 23);
        int temp3 = temp.timerTime.get(Calendar.DATE);
        temp.changeTimerUnit();
        temp.increaseTimerValue();
        assertEquals(0, temp.timerTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(temp3, temp.timerTime.get(Calendar.DATE));

    }

    @Test
    void updateTimer(){
        Timer temp = new Timer();
        temp.WPressed(true); // into settimer
        temp.SPressed(false); // timer set 1 second
        temp.WPressed(false); // exit set timer
        temp.SPressed(false); // start Timer
        for(int i =0;i <100; i++)
            temp.updateTimer(); //minus 1 second
        assertEquals(0, temp.timerTime.get(Calendar.SECOND)); //compare 0 second with timerTime's second
    }

}