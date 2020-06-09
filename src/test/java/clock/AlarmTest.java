package clock;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlarmTest {

    @Test
    void setAlarm() {
        Alarm temp = new Alarm();
        try{
            Field field = temp.getClass().getDeclaredField("alarms");
            field.setAccessible(true);
            Calendar tempCal = ((LinkedList<Calendar>)field.get(temp)).get(0);

            temp.WPressed(true); // into setalarm
            for(int i = 0; i < 5; i ++)
                temp.SPressed(false); // increase 5 minutes
            temp.WPressed(false); // out setalarm

            List<Calendar> ret = (LinkedList<Calendar>)field.get(temp);

            assertNotEquals(tempCal, ret.get(0));
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    @Test
    void changeAlarmIndex() {
        Alarm temp = new Alarm();
        try{
            Field field = temp.getClass().getDeclaredField("index");
            field.setAccessible(true);
            int curIndex = (int)field.get(temp);

            temp.WPressed(false); // next alarm

            assertNotEquals(curIndex, (int)field.get(temp));

        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    @Test
    void changeAlarmToggle() {
        Alarm temp = new Alarm();
        try{
            Field field = temp.getClass().getDeclaredField("toggle");
            field.setAccessible(true);

            temp.SPressed(false); // alarm toggle

            LinkedList<Boolean> ret = (LinkedList<Boolean>)field.get(temp);

            assertEquals(false, ret.get(0));

        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    @Test
    void requestAlarm() {
        Alarm temp = new Alarm();
        try{
            String[] tempString = new String[3];
            tempString = temp.requestAlarm();

            temp.WPressed(true); // into setalarm
            for(int i = 0; i < 5; i ++)
                temp.SPressed(false); // increase 5 minutes
            temp.WPressed(false); // out setalarm

            assertNotEquals(tempString, temp.requestAlarm());
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

}