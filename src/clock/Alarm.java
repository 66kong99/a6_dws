package clock;

import java.util.*;

public class Alarm extends Mode{
    private static final int NORMAL_ALARM_STATE = 0;
    private static final int CHANGE_ALARM_STATE = 1;

    private List<Calendar> alarms;
    private List<Boolean> toggle;
    private Buzzer beep;

    private byte index;

    private boolean isSetAlarm;

    public Alarm() {
        alarms = new LinkedList<Calendar>();
        toggle = new LinkedList<Boolean>();
        beep = new Buzzer();
        isSetAlarm = false;
        index = 0;
    }

    public void setAlarm() {

    }

    public void changeAlarmIndex() {
        index++;
    }

    public void changeAlarmToggle() {
        toggle.set(index, !toggle.get(index));
    }

    public String[] requestAlarm(){
        String swit = new String();

        if (toggle.get(index) == false)
            swit = "OFF";
        else
            swit = "ON";
        Calendar temp = alarms.get(index);
        return new String[]{"ALARM" + index, temp.get(Calendar.HOUR_OF_DAY) + ":" + temp.get(Calendar.MINUTE), swit};
    }

    public void compare(Calendar curTime){
        for (Calendar c : alarms){
            if (c.equals(curTime) == true)
                beep.beep(30);
        }

    }

    @Override
    public void QPressed(boolean Longpress) {
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }

    }

    @Override
    public void WPressed(boolean Longpress) {
        if(beep.isBeep == true) {
                beep.stopBeep();
                return;
        }
        if (isSetAlarm == true) {
            isSetAlarm = false;
            return;
        }
        if (Longpress == true){
            isSetAlarm = true;
            setAlarm();
        }
        else{
            changeAlarmIndex();
        }
    }

    @Override
    public void SPressed(boolean Longpress) {
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }
        changeAlarmToggle();

    }
}