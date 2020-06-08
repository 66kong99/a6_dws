package clock;

import java.util.*;

public class Alarm implements Mode{

    private List<Calendar> alarms;
    private List<Boolean> toggle;
    private Buzzer beep;

    private int index;
    private int timeUnit[];
    private int Unit = 0;

    private boolean isSetAlarm;

    public Alarm() {
        alarms = new LinkedList<Calendar>();
        toggle = new LinkedList<Boolean>();
        beep = new Buzzer();
        timeUnit = new int[3];
        isSetAlarm = false;
        index = 0;

        Calendar temp = Calendar.getInstance();
        temp.set(0, 0, 0, 0, 0, 0);
        for(int i = 0; i < 5; i++) {
            alarms.add(temp);
            toggle.add(true);
        }
    }

    public void setAlarm() {
        Calendar temp = Calendar.getInstance();
        temp.set(0, 0, 0, timeUnit[2], timeUnit[1], timeUnit[0]);
        alarms.set(index, temp);
    }

    public void update(){ }

    public void changeAlarmIndex() {
        index = (index+1) % alarms.size();
    }

    public void changeAlarmToggle() {
        toggle.set(index, !toggle.get(index));
    }

    public String[] requestAlarm(){
        String swit = new String();
        Calendar temp = alarms.get(index);
        if (timeUnit[0] == 61){
            timeUnit[1]++;
            timeUnit[0] = 0;
        }
        if (timeUnit[1] == 61){
            timeUnit[2]++;
            timeUnit[1] = 0;
        }
        if (timeUnit[2] == 25){
            timeUnit[2] = 0;
        }

        if (toggle.get(index) == false)
            swit = "OFF";
        else
            swit = " ON";
        if (isSetAlarm){
            return new String[]{"ALARM " + (index+1), String.format("%02d", timeUnit[2]) + ":" + String.format("%02d", timeUnit[1]), Integer.toString(timeUnit[0])};
        }else {
            return new String[]{"ALARM " + (index+1), String.format("%02d", temp.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", temp.get(Calendar.MINUTE)), swit};
        }
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
    public void APressed() {
        if(beep.isBeep == true) {
            beep.stopBeep();
            return;
        }
    }

    @Override
    public void WPressed(boolean Longpress) {
        System.out.println("W Pressed");
        if(beep.isBeep == true) {
                beep.stopBeep();
                return;
        }
        if (isSetAlarm == true) {
            isSetAlarm = false;
            setAlarm();
            return;
        }
        if (Longpress && !isSetAlarm){
            isSetAlarm = true;
            for (int i : timeUnit)
                i = 0;
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
        if (Longpress && !isSetAlarm) {
            System.out.println("S Longpressed");
            isSetAlarm = !isSetAlarm;
            setAlarm();
            return;
        }
        if (Longpress && isSetAlarm) {
            Unit++;
            return;
        }
        if (isSetAlarm) {
            timeUnit[Unit]++;
            return;
        }
        changeAlarmToggle();

    }
}