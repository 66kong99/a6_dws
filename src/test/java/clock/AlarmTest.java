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

            assertEquals(true, ret.get(0));

        }catch (Exception e){
            e.printStackTrace();
            return;
        }
    }

    @Test
    void changeAlarmUnit(){
        Alarm alarm = new Alarm();
        try {
            Field field = alarm.getClass().getDeclaredField("Unit");
            field.setAccessible(true);
            for(int i = 0; i < 3; i++){ // test 3 times
                int temp = (int) field.get(alarm);
                alarm.changeAlarmUnit();
                if(temp == 1){
                    assertEquals((int) field.get(alarm), 0); // check: when alarm unit is hour, change to minute
                }else{
                    assertEquals((int) field.get(alarm), temp+1); // check: when alarm unit is not hour, change to next unit
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void increaseAlarmValue(){
        Alarm alarm = new Alarm();
        try{
            Field field = alarm.getClass().getDeclaredField("timeUnit");
            field.setAccessible(true);
            for(int i = 0; i < 59; i++)
                alarm.increaseAlarmValue(); // set minute to 59
            int fieldList[] = (int[]) field.get(alarm);
            assertEquals(fieldList[0], 59); // check: minute is 59
            assertEquals(fieldList[1], 0); // check: hour is 0
            alarm.increaseAlarmValue(); // increase minute one more time from 59
            fieldList = (int[]) field.get(alarm);
            assertEquals(fieldList[0], 0); // check: minute is 0
            assertEquals(fieldList[1], 0); // check: hour is 0
            alarm.changeAlarmUnit(); // changing hour
            for(int i = 0; i < 23; i++)
                alarm.increaseAlarmValue(); // set hour to 23
            fieldList = (int[]) field.get(alarm);
            assertEquals(fieldList[0], 0); // check: minute is 0
            assertEquals(fieldList[1], 23); // check: hour is 23
            alarm.increaseAlarmValue(); // increase hour one more time from 23
            fieldList = (int[]) field.get(alarm);
            assertEquals(fieldList[0], 0); // check: minute is 0
            assertEquals(fieldList[1], 0); // check: hour is 0
            alarm.changeAlarmUnit(); // changing minute again
            for(int i = 0; i < 59; i++)
                alarm.increaseAlarmValue(); // set minute to 59
            fieldList = (int[]) field.get(alarm);
            assertEquals(fieldList[0], 59); // check: minute is 59
            assertEquals(fieldList[1], 0); // check: hour is 0
            alarm.increaseAlarmValue(); // increase minute one more time from 59
            fieldList = (int[]) field.get(alarm);
            assertEquals(fieldList[0], 0); // check: minute is 0
            assertEquals(fieldList[1], 0); // check: hour is 0
        }catch(NoSuchFieldException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){

        }
    }

    @Test
    void updateAlarm(){
        Calendar time = Calendar.getInstance();
        Alarm alarm = new Alarm();
        for(int i = 1; i <= 4; i++){
            alarm.WPressed(true);
            for(int j = 0; j < i*10; j++)
                alarm.increaseAlarmValue(); // set minute to i*10
            alarm.changeAlarmUnit();
            for(int j = 0; j < i; j++)
                alarm.increaseAlarmValue(); // set hour to i
            alarm.WPressed(false);
            if(i == 1 || i == 4)
                alarm.changeAlarmToggle(); // alarm 1,4 on, alarm 2,3 off
            alarm.changeAlarmIndex();
        }
        for(int i = 1; i <= 4; i++){
            time.set(Calendar.HOUR_OF_DAY, i); // hour: 1, 2, 3, 4
            time.set(Calendar.MINUTE, i*10); // minute: 10, 20, 30, 40
            time.set(Calendar.SECOND, 0);
            if(i == 1 || i == 4)
                assertEquals(alarm.updateAlarm(time), 3000); // alarm 1,4 ring
            else
                assertEquals(alarm.updateAlarm(time), 0); // alarm 2,3 don't ring
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