package clock;

import java.util.Calendar;

public class Worldtime implements Mode{

    private transient int curCity;
    private transient final int[] timeDiff;
    private transient final String[] city;
    private transient boolean isSummerTime;
    private transient Calendar worldClock;
    private transient Calendar GMT9;

    public Worldtime(Calendar curTime) {
        timeDiff = new int[] {-20, -19, -18, -17, -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3};
        city = new String[] {"PAGO PAGO", "HAWAII", "ALASKA", "LA", "DENVER", "CHICAGO", "NEW YORK", "CARACAS", "SAO PAULO", "FERNANDO", "AZORES", "LONDON", "PARIS", "ATHENS", "MOSCOW", "DUBAI", "ISLAMABAD", "DHAKA", "JAKARTA", "BEIJING", "TOKYO", "SYDNEY", "NOUMEA", "WELLINGTON"};
        curCity = 20;
        isSummerTime = false;

        GMT9 = curTime;
        worldClock = (Calendar)GMT9.clone();
        worldClock.add(Calendar.HOUR_OF_DAY, timeDiff[curCity]);
        worldClock.add(Calendar.HOUR_OF_DAY, 1);
    }

    public String[] requestWorldtime(Calendar curTime) {
        update(curTime);
        calWorldTime();
        if(isSummerTime)
            return new String[] {"WORLD-" + city[curCity] + "(S)", String.format("%02d", worldClock.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", worldClock.get(Calendar.MINUTE)), ":" + String.format("%02d", worldClock.get(Calendar.SECOND)), "X"};
        else
            return new String[] {"WORLD-" + city[curCity], String.format("%02d", worldClock.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", worldClock.get(Calendar.MINUTE)), ":" + String.format("%02d", worldClock.get(Calendar.SECOND)), "X"};
    }


    public void changeCity() {
        if(curCity >= 23)
            curCity = 0;
        else
            curCity++;
    }

    public void changeIsSummertime() {
        if(isSummerTime)
            isSummerTime = false;
        else
            isSummerTime = true;
    }

    public void calWorldTime() {
        worldClock = (Calendar)GMT9.clone();
        worldClock.add(Calendar.HOUR_OF_DAY, timeDiff[curCity]);
        if(isSummerTime)
            worldClock.add(Calendar.HOUR_OF_DAY, 1);
    }

    public void update(Calendar curTime){ // get korea time
        GMT9 = (Calendar)curTime.clone();
    } // updated by modemanager

    @Override
    public void QPressed(boolean Longpress) {

    }

    @Override
    public void APressed() {

    }

    @Override
    public void WPressed(boolean Longpress) {
        if(!Longpress) {
            changeIsSummertime();
            calWorldTime();
        }
    }

    @Override
    public void SPressed(boolean Longpress) {
        if(!Longpress) {
            changeCity();
            calWorldTime();
        }
    }
}