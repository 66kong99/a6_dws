package clock;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class StopwatchTest {

    @Test
    void requestStopwTime() {
        Stopwatch st = new Stopwatch();
        try{
            Field field = st.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            boolean value = (boolean)field.get(st);
            assertTrue(value); // isPaused = true 확인
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }
    }

    @Test
    void resetStopw() {
        Stopwatch st = new Stopwatch();
        Calendar cal = st.stopwTime;
        try{
            Field field = st.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            st.SPressed(false); //increase stopw
            st.SPressed(false); //pause stopw
            st.WPressed(false); //reset stopw
            boolean value = (boolean)field.get(st);
            assertTrue(value); //isPaused = true 확인
            assertEquals(st.stopwTime, cal); //reset 확인
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void increaseStopw() {
        Stopwatch st = new Stopwatch();
        Calendar cal = st.stopwTime;
        try{
            Field field = st.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            st.SPressed(false); // increase stopw
            boolean value = (boolean)field.get(st);
            assertFalse(value); // isPaused = false 확인
            try {
                Thread.sleep(5000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }//stopw 5초
            cal.add(Calendar.SECOND, 5);
            assertEquals(st.stopwTime, cal); // stopw 5초 확인
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }


    }

    @Test
    void pauseStopw() {
        Stopwatch st = new Stopwatch();
        Calendar cal = st.stopwTime;
        try{
            Field field = st.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            st.SPressed(false); // increase stopw
            try {
                Thread.sleep(5000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }//stopw 5초
            st.SPressed(false); // pause stopw
            boolean value = (boolean)field.get(st);
            assertTrue(value); // isPaused = true 확인
            cal.add(Calendar.SECOND, 5);
            assertEquals(st.stopwTime, cal); // stopw 5초 확인
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }
    }

    @Test
    void requestSplit() {
    }

    @Test
    void QPressed() {
    }

    @Test
    void APressed() {
    }

    @Test
    void WPressed() {
    }

    @Test
    void SPressed() {
    }
}