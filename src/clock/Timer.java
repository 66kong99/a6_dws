package clock;

import java.util.*;

public class Timer implements Mode {
    private char timerHour;
    private char timerMin;
    private char timerSec;
    private boolean isPaused;
    public Timer() {
    }

    private void requestTimerTime() {
    }

    private void increaseTimerValue() {
    }

    private void decreaseTimer() {
    }

    private void pauseTimer() {
    }

    private void resetTimer() {
    }

    public void QPressed() {

    }
    public void SPressed(){
        decreaseTimer();
    }
    public void WPressed(){
        if(isPaused == false)
            pauseTimer();
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