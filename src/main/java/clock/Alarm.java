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
        for(int i = 0; i < 4; i++) {
            alarms.add(temp);
            toggle.add(true);
        }
    }

    public void setAlarm() {
        Calendar temp = Calendar.getInstance();
        temp.set(0, 0, 0, timeUnit[1], timeUnit[0], 0);
        alarms.set(index, temp);
    }

    public int updateAlarm(Calendar curTime) {
        for (int i = 0; i < alarms.size(); i++) {
            if (alarms.get(i).equals(curTime) == true && toggle.get(i) == true)
                return 3000;
        }
        return 0;
    }

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
        if (timeUnit[1] == 25){
            timeUnit[1] = 0;
        }

        if (toggle.get(index) == false)
            swit = "OFF";
        else
            swit = " ON";
        if (isSetAlarm){
            return new String[]{"ALARM " + (index+1), String.format("%02d", timeUnit[1]) + ":" + String.format("%02d", timeUnit[0]), "SET", String.format("%d", Unit+2)};
        }else {
            return new String[]{"ALARM " + (index+1), String.format("%02d", temp.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", temp.get(Calendar.MINUTE)), swit, "X"};
        }
    }

    // W(LP) : setting mode
    // S = Start
    // S(LP) = changeUnit
    // W = reset

    @Override
    public void QPressed(boolean Longpress) {
    }

    @Override
    public void APressed() {
    }

    @Override
    public void WPressed(boolean Longpress) { // C
//        System.out.println("W Pressed");
        if (Longpress && !isSetAlarm){
            isSetAlarm = true;
            Unit = 0;
            for (int i : timeUnit)
                i = 0;
            return;
        }
        if (isSetAlarm) {
            isSetAlarm = false;
            setAlarm();
            return;
        }

        changeAlarmIndex();
    }

    @Override
    public void SPressed(boolean Longpress) { // D
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