package clock;

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

}