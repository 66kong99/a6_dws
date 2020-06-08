package clock;

import java.util.*;


public class Worldtime implements Mode{
    private List<Calendar> City;
    private List<Boolean> isSummerTime;


    public Worldtime() {
        City = new LinkedList<Calendar>();
        isSummerTime = new LinkedList<Boolean>();


    }

    public void requestWorldtime() {
    }

    public void changeCity() {
    }

    public void changeIsSummertime() {
    }

    public void calWorldTime() {

    }

    @Override
    public void QPressed(boolean Longpress) {

    }

    @Override
    public void WPressed(boolean Longpress) {

    }

    @Override
    public void SPressed(boolean Longpress) {

    }
}