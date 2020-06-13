package clock;

//import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;

class TimeTest {

    @Test
    void requestCurTime() {
        Time t = new Time();

        Calendar tempTime = (Calendar) t.curTime.clone();
        String[] dayOfWeek = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

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
        dateBuffer.append(dayOfWeek[(tempTime.get(Calendar.DAY_OF_WEEK)-1)%7]);

        timeBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "");
        timeBuffer.append(tempTime.get(Calendar.HOUR_OF_DAY));
        timeBuffer.append(":");
        timeBuffer.append(tempTime.get(Calendar.MINUTE) < 10 ? "0" : "");
        timeBuffer.append(tempTime.get(Calendar.MINUTE));

        secBuffer.append(":");
        secBuffer.append(tempTime.get(Calendar.SECOND) < 10 ? "0" : "");
        secBuffer.append(tempTime.get(Calendar.SECOND));


        String temp[] = t.requestCurTime();
        assertEquals(dateBuffer.toString(), temp[0]); //compare between datebuffer and actual datebuffer
        assertEquals(timeBuffer.toString(), temp[1]); //compare between timebuffer and actual timebuffer
        assertEquals(secBuffer.toString(), temp[2]); //compare between secbuffer and actual secbuffer
        assertEquals("X", temp[3]); //compare between "X" and fourth index of returned array(without setting time)

        t.WPressed(true);
        String temp2[] = t.requestCurTime();
        try {
            Field field = t.getClass().getDeclaredField("timeUnit");
            field.setAccessible(true);
            int temptimeUnit = (int)(field.get(t));
            assertEquals(String.valueOf(temptimeUnit), temp2[3]);  //compare between "timeunit" and fourth index of returned array(with setting time)
        }
        catch(NoSuchFieldException e){
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateTime(){
        Time t = new Time();
        Calendar temp = (Calendar) t.curTime.clone(); // temporary valuable for curTime
        t.updateTime(); // 10ms increasing
        assertNotEquals(temp, t.curTime);// check curTime and previousTime

        Calendar temp2 = (Calendar) t.curTime.clone(); // temporary valuable for curTime
        t.WPressed(true);// set time
        t.updateTime();// 10ms increasing
        assertNotEquals(temp2, t.curTime);// check curTime and previousTime
    }

    @Test
    void increaseTimeValue() {
        Time t = new Time();//isSetTime = false;
        t.WPressed(true);//setTime

        //test set second(59->0)
        t.curTime.set(Calendar.SECOND, 59);
        int temp1 = t.curTime.get(Calendar.MINUTE);
        t.increaseTimeValue();
        assertEquals(0, t.curTime.get(Calendar.SECOND));
        assertEquals(temp1, t.curTime.get(Calendar.MINUTE));

        //test set minute(59->0)
        t.curTime.set(Calendar.MINUTE, 59);
        int temp2 = t.curTime.get(Calendar.HOUR_OF_DAY);
        t.changeTimeUnit();
        t.increaseTimeValue();
        assertEquals(0, t.curTime.get(Calendar.MINUTE));
        assertEquals(temp2, t.curTime.get(Calendar.HOUR_OF_DAY));

        //test set hour(23->0)
        t.curTime.set(Calendar.HOUR_OF_DAY, 23);
        int temp3 = t.curTime.get(Calendar.DATE);
        t.changeTimeUnit();
        t.increaseTimeValue();
        assertEquals(0, t.curTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(temp3, t.curTime.get(Calendar.DATE));

        //test set date(getLeastMaximum ->1)
        t.curTime.set(Calendar.DATE, t.curTime.getActualMaximum(Calendar.DATE));
        int temp4 = t.curTime.get(Calendar.MONTH);
        t.changeTimeUnit();
        t.increaseTimeValue();
        assertEquals(1, t.curTime.get(Calendar.DATE));
        assertEquals(temp4, t.curTime.get(Calendar.MONTH));

        //test set month(DECEMBER -> JANUARY)
        t.curTime.set(Calendar.MONTH, Calendar.DECEMBER);
        int temp5 = t.curTime.get(Calendar.YEAR);
        t.changeTimeUnit();
        t.increaseTimeValue();
        assertEquals(Calendar.JANUARY, t.curTime.get(Calendar.MONTH));
        assertEquals(temp5, t.curTime.get(Calendar.YEAR));

        //test set year(2099 -> 1970)
        t.curTime.set(Calendar.YEAR, 2099);
        t.changeTimeUnit();
        t.increaseTimeValue();
        assertEquals(1970, t.curTime.get(Calendar.YEAR));

    }

    @Test
    void changeTimeUnit() throws NoSuchFieldException {

        Time t = new Time();
        try {
            Field field = t.getClass().getDeclaredField("timeUnit");
            field.setAccessible(true);

            t.changeTimeUnit();
            int temp =  (int)(field.get(t));
            assertEquals(1, temp);

            t.WPressed(true);
            for(int i = 1 ; i<=5;i++) {
                t.changeTimeUnit(); // add timeunit by 1
                int temp2 = (int)(field.get(t));
                assertEquals(i+1, temp2); //compare timeunit and expected value
            }
            t.changeTimeUnit();
            int temp3 = (int)(field.get(t));
            assertEquals(1, temp3);


        }
        catch(NoSuchFieldException e){
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}