package ru.cft.focus.view.listener.impl;

import ru.cft.focus.view.View;
import ru.cft.focus.view.listener.TimerListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Timer implements TimerListener {
    private final View view;

    private ScheduledExecutorService timer;
    private int timerValue = 0;

    public Timer(View view) {
        this.view = view;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public int getTimerValue() {
        return timerValue;
    }

    @Override
    public void startTimer() {
        timer = Executors.newScheduledThreadPool(1);
        final Runnable runnable = new Runnable() {
            int countSeconds = 0;

            public void run() {
                view.getMainWindow().setTimerValue(countSeconds);
                setTimerValue(countSeconds);
                countSeconds++;
            }
        };

        timer.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
    }

    @Override
    public void stopTimer() {
        if (timer != null) {
            timer.shutdown();
        }
    }

    @Override
    public void clearTimer() {
        view.getMainWindow().setTimerValue(0);
        setTimerValue(0);
    }
}
