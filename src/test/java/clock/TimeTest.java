package clock;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.*;

class TimeTest {

    @Test
    void requestCurTime() {
        Time t = new Time();
        Calendar temp = (Calendar) t.curTime.clone(); // 현재시간을 저장한 임시 변수
        t.requestCurTime(); // 10ms 증가
        assertNotEquals(temp, t.curTime);// 10ms가 증가한 현재시간으로 이전의 시간과 다른지 확인
        t.WPressed(true);//setTime 할 때 현재시간은 증가하지 않음
        Calendar temp2 = (Calendar) t.curTime.clone(); // 현재시간을 저장한 임시 변수
        assertEquals(temp2, t.curTime);
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
            t.WPressed(true);
            for(int i =1 ; i<=5;i++) {
                t.changeTimeUnit();
                int temp = (int)(field.get(t));
                assertEquals(i+1, temp);
            }
            t.changeTimeUnit();
            int temp = (int)(field.get(t));
            assertEquals(1, temp);
        }
        catch(NoSuchFieldException e){
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}