package clock;

import org.junit.jupiter.api.Test;

import java.util.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class WorldtimeTest {

    @Test
    void requestWorldtime() {
        Time time = new Time();
        Worldtime worldtime = new Worldtime(time.curTime);
        try{
            Field field1 = worldtime.getClass().getDeclaredField("GMT9");
            Field field2 = worldtime.getClass().getDeclaredField("worldClock");
            Field field3 = worldtime.getClass().getDeclaredField("isSummerTime");
            Field field4 = worldtime.getClass().getDeclaredField("city");
            Field field5 = worldtime.getClass().getDeclaredField("curCity");
            worldtime.calWorldTime();
            Calendar temp1 = (Calendar) field1.get(worldtime);
            Calendar temp2 = (Calendar) field2.get(worldtime);
            String[] temp3 = (String[]) field4.get(worldtime);
            assertEquals(temp3[(int)field5.get(worldtime)], "TOKYO");
            assertEquals((boolean)field3.get(worldtime), false);
            assertEquals(temp2.get(Calendar.HOUR_OF_DAY), temp1.get(Calendar.HOUR_OF_DAY));
            assertEquals(temp2.get(Calendar.MINUTE), temp1.get(Calendar.MINUTE));
            assertEquals(temp2.get(Calendar.SECOND), temp1.get(Calendar.SECOND));
            worldtime.changeIsSummertime();
            worldtime.calWorldTime();
            assertEquals(temp3[(int)field5.get(worldtime)], "TOKYO");
            assertEquals((boolean)field3.get(worldtime), true);
            assertEquals(temp2.get(Calendar.HOUR_OF_DAY) - 1, temp1.get(Calendar.HOUR_OF_DAY));
            assertEquals(temp2.get(Calendar.MINUTE), temp1.get(Calendar.MINUTE));
            assertEquals(temp2.get(Calendar.SECOND), temp1.get(Calendar.SECOND));
            worldtime.changeIsSummertime();
            worldtime.changeCity();
            worldtime.calWorldTime();
            assertEquals(temp3[(int)field5.get(worldtime)], "SYDNEY");
            assertEquals((boolean)field3.get(worldtime), false);
            assertEquals(temp2.get(Calendar.HOUR_OF_DAY) - 1, temp1.get(Calendar.HOUR_OF_DAY));
            assertEquals(temp2.get(Calendar.MINUTE), temp1.get(Calendar.MINUTE));
            assertEquals(temp2.get(Calendar.SECOND), temp1.get(Calendar.SECOND));
            worldtime.changeIsSummertime();
            worldtime.calWorldTime();
            assertEquals(temp3[(int)field5.get(worldtime)], "SYDNEY");
            assertEquals((boolean)field3.get(worldtime), true);
            assertEquals(temp2.get(Calendar.HOUR_OF_DAY) - 2, temp1.get(Calendar.HOUR_OF_DAY));
            assertEquals(temp2.get(Calendar.MINUTE), temp1.get(Calendar.MINUTE));
            assertEquals(temp2.get(Calendar.SECOND), temp1.get(Calendar.SECOND));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void changeCity() {
        Time time = new Time();
        Worldtime worldtime = new Worldtime(time.curTime);
        try {
            Field field1 = worldtime.getClass().getDeclaredField("curCity");
            Field field2 = worldtime.getClass().getDeclaredField("timeDiff");
            Field field3 = worldtime.getClass().getDeclaredField("city");
            field1.setAccessible(true);
            field2.setAccessible(true);
            field3.setAccessible(true);
            int field2List[] = (int[]) field2.get(worldtime);
            String field3List[] = (String[]) field3.get(worldtime);
            worldtime.changeCity();
            worldtime.changeCity();
            worldtime.changeCity();
            assertEquals((int) field1.get(worldtime), 23);
            assertEquals(field2List[(int) field1.get(worldtime)], 3);
            assertEquals(field3List[(int) field1.get(worldtime)], "WELLINGTON");
            worldtime.changeCity();
            assertEquals((int) field1.get(worldtime), 0);
            assertEquals(field2List[(int) field1.get(worldtime)], -20);
            assertEquals(field3List[(int) field1.get(worldtime)], "PAGO PAGO");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void changeIsSummertime() {
        boolean temp;
        Time time = new Time();
        Worldtime worldtime = new Worldtime(time.curTime);
        try {
            Field field = worldtime.getClass().getDeclaredField("isSummerTime");
            field.setAccessible(true);
            temp = (boolean) field.get(worldtime);
            worldtime.changeIsSummertime();
            assertNotEquals((boolean) field.get(worldtime), temp);
            worldtime.changeIsSummertime();
            assertEquals((boolean) field.get(worldtime), temp);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void calWorldTime() {
        Time time = new Time();
        Worldtime worldtime = new Worldtime(time.curTime);
        try {
            Field field1 = worldtime.getClass().getDeclaredField("GMT9");
            Field field2 = worldtime.getClass().getDeclaredField("worldClock");
            worldtime.calWorldTime();
            Calendar temp1 = (Calendar) field1.get(worldtime);
            Calendar temp2 = (Calendar) field2.get(worldtime);
            assertEquals(temp1, temp2);
            worldtime.changeCity();
            worldtime.changeCity();
            worldtime.changeCity();
            worldtime.calWorldTime();
            temp2 = (Calendar) field2.get(worldtime);
            assertEquals((temp2.getTimeInMillis() - temp1.getTimeInMillis()) / (60 * 60 * 1000), 3);
            worldtime.changeIsSummertime();
            worldtime.calWorldTime();
            temp2 = (Calendar) field2.get(worldtime);
            assertEquals((temp2.getTimeInMillis() - temp1.getTimeInMillis()) / (60 * 60 * 1000), 4);
            worldtime.changeIsSummertime();
            worldtime.changeCity();
            worldtime.calWorldTime();
            temp2 = (Calendar) field2.get(worldtime);
            assertEquals((temp2.getTimeInMillis() - temp1.getTimeInMillis()) / (60 * 60 * 1000), -20);
            worldtime.changeIsSummertime();
            worldtime.calWorldTime();
            temp2 = (Calendar) field2.get(worldtime);
            assertEquals((temp2.getTimeInMillis() - temp1.getTimeInMillis()) / (60 * 60 * 1000), -19);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}