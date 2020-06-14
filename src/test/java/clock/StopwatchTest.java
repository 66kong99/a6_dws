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
            assertTrue(value); // isPaused = true checking
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }
    }

    @Test
    void updateStopw() {
        Stopwatch st = new Stopwatch();
        Calendar cal = (Calendar) st.stopwTime.clone();
        st.SPressed(false); // increase stopw
        st.updateStopw(); //stopw millisecond + 10
        cal.add(Calendar.MILLISECOND, 10);
        assertEquals(st.stopwTime, cal); //stopwTime = millisecond 10 checking
    }

    @Test
    void resetStopw() {
        Stopwatch st = new Stopwatch();
        Calendar cal = (Calendar) st.stopwTime.clone();
        try{
            Field field = st.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            st.SPressed(false); //increase stopw
            st.updateStopw(); //stopw millisecond + 10
            st.SPressed(false); //pause stopw
            st.WPressed(false); //reset stopw
            boolean value = (boolean)field.get(st);
            assertTrue(value); //isPaused = true checking
            assertEquals(st.stopwTime, cal); //reset checking
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void increaseStopw() {
        Stopwatch st = new Stopwatch();
        Calendar cal = (Calendar) st.stopwTime.clone();
        try{
            Field field = st.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            st.SPressed(false); // increase stopw
            boolean value = (boolean)field.get(st);
            assertFalse(value); // isPaused = false checking
            st.updateStopw(); //stopw millisecond + 10
            cal.add(Calendar.MILLISECOND, 10);
            assertEquals(st.stopwTime, cal); // stopwTime = millisecond 10 checking
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }


    }

    @Test
    void pauseStopw() {
        Stopwatch st = new Stopwatch();
        Calendar cal = (Calendar) st.stopwTime.clone();
        try{
            Field field = st.getClass().getDeclaredField("isPaused");
            field.setAccessible(true);
            st.SPressed(false); // increase stopw
            st.updateStopw(); //stopw millisecond + 10
            st.SPressed(false); // pause stopw
            boolean value = (boolean)field.get(st);
            assertTrue(value); // isPaused = true checking
            cal.add(Calendar.MILLISECOND, 10);
            assertEquals(st.stopwTime, cal); // stopwTime = millisecond 10 checking
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }
    }

    @Test
    void requestSplit() {
        Stopwatch st = new Stopwatch();
        try{
            Field field1 = st.getClass().getDeclaredField("isPaused");
            field1.setAccessible(true);
            Field field2 = st.getClass().getDeclaredField("isSplit");
            field2.setAccessible(true);
            boolean isSplit1 = (boolean)field2.get(st);
            assertFalse(isSplit1); // isSplit = false checking
            st.SPressed(false); // increase stopw
            st.updateStopw(); //stopw millisecond + 10
            st.WPressed(false);
            String splitTime = st.requestSplit(); // stopw split
            boolean isPaused = (boolean)field1.get(st);
            assertFalse(isPaused); // isPaused = false checking
            boolean isSplit2 = (boolean)field2.get(st);
            assertTrue(isSplit2); // isSplit = true checking
            assertEquals(st.requestSplit(),splitTime); //splitTime = millisecond 10 checking

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e. printStackTrace();
        }
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