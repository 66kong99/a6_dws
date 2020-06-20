package clock;

import java.util.List;
import java.util.LinkedList;
import java.util.Calendar;

public class Alarm implements Mode{


    private transient final List<Calendar> alarms;
    private transient final List<Boolean> toggle;

    private transient int index;
    private transient final int[] timeUnit;
    private transient int Unit;

    private transient boolean isSetAlarm;

    private transient final String stringformat = "%02d";

    public Alarm() {
        alarms = new LinkedList<Calendar>();
        toggle = new LinkedList<Boolean>();
        timeUnit = new int[2];
        isSetAlarm = false;
        index = 0;
        Unit = 0;

        Calendar temp = Calendar.getInstance();
        temp.set(0, 0, 0, 0, 0, 0);
        for(int i = 0; i < 4; i++) {
            alarms.add(temp);
            toggle.add(false);
        }
    }

    public void setAlarm() {
        Calendar temp = Calendar.getInstance();
        temp.set(0, 0, 0, timeUnit[1], timeUnit[0], 0);
        alarms.set(index, temp);
    }

    public void increaseAlarmValue(){

        timeUnit[Unit]++;
        if (timeUnit[0] == 60){
            timeUnit[0] = 0;
        }
        if (timeUnit[1] == 24){
            timeUnit[1] = 0;
        }
    }

    public void changeAlarmUnit(){
        Unit++;
        if(Unit == 2)
            Unit = 0;
    }


    public int updateAlarm(Calendar curTime) {
        for (int i = 0; i < alarms.size(); i++) {
            if (alarms.get(i).get(Calendar.HOUR)==curTime.get(Calendar.HOUR)&&
                    alarms.get(i).get(Calendar.MINUTE)==curTime.get(Calendar.MINUTE)&&
                    alarms.get(i).get(Calendar.SECOND)==curTime.get(Calendar.SECOND)&& toggle.get(i) == true)
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
        String swit;
        Calendar temp = alarms.get(index);

        if (toggle.get(index) == false)
            swit = "OFF";
        else
            swit = " ON";
        if (isSetAlarm){
            return new String[]{"ALARM " + (index+1), String.format(stringformat, timeUnit[1]) + ":" + String.format(stringformat, timeUnit[0]), "SET", String.format("%d", Unit+2)};
        }else {
            return new String[]{"ALARM " + (index+1), String.format(stringformat, temp.get(Calendar.HOUR_OF_DAY)) + ":" + String.format(stringformat, temp.get(Calendar.MINUTE)), swit, "X"};
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
        if(Longpress) {
            if (!isSetAlarm) {
                isSetAlarm = true;
                Unit = 0;
                timeUnit[0] = alarms.get(index).get(Calendar.MINUTE);
                timeUnit[1] = alarms.get(index).get(Calendar.HOUR);
                return;
            }
        } else{
            if (isSetAlarm) {
                isSetAlarm = false;
                setAlarm();
                return;
            }

            changeAlarmIndex();
        }
    }

    @Override
    public void SPressed(boolean Longpress) { // D
        if(Longpress){
            if(isSetAlarm){
                changeAlarmUnit();
                return;
            }
        }else{
            if(isSetAlarm){
                increaseAlarmValue();
                return;
            }
            changeAlarmToggle();
        }
    }
}