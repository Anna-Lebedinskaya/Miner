package ru.cft.focus.view.listener.impl;

import lombok.extern.slf4j.Slf4j;
import ru.cft.focus.view.View;
import ru.cft.focus.view.*;
import ru.cft.focus.view.constant.GameType;
import ru.cft.focus.view.listener.RecordListener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Records implements RecordListener {
    private final View view;
    private final Properties properties;

    public Records(View view) {
        this.view = view;

        properties = new Properties();
        try {
            properties.load(new FileReader("task3/src/main/resources/app.properties"));
        } catch (IOException e) {
            log.error("Error in reading properties: {}", e.getMessage());
        }

        view.getHighScoresWindow().setNoviceRecord(properties.getProperty("nameNovice"),
                Integer.parseInt(properties.getProperty("timeValueNovice")));
        view.getHighScoresWindow().setMediumRecord(properties.getProperty("nameMedium"),
                Integer.parseInt(properties.getProperty("timeValueMedium")));
        view.getHighScoresWindow().setExpertRecord(properties.getProperty("nameExpert"),
                Integer.parseInt(properties.getProperty("timeValueExpert")));
    }

    public int getRecordValue(GameType gameType) {
        int value = 0;
        switch (gameType) {
            case NOVICE -> value = Integer.parseInt(properties.getProperty("timeValueNovice"));
            case MEDIUM -> value = Integer.parseInt(properties.getProperty("timeValueMedium"));
            case EXPERT -> value = Integer.parseInt(properties.getProperty("timeValueExpert"));
        }
        return value;
    }

    private boolean updateRecords(GameType gameType, String name, int time) {
        switch (gameType) {
            case NOVICE -> {
                properties.setProperty("nameNovice", name);
                properties.setProperty("timeValueNovice", String.valueOf(time));
            }
            case MEDIUM -> {
                properties.setProperty("nameMedium", name);
                properties.setProperty("timeValueMedium", String.valueOf(time));
            }
            case EXPERT -> {
                properties.setProperty("nameExpert", name);
                properties.setProperty("timeValueExpert", String.valueOf(time));
            }
        }
        try {
            properties.store(new FileWriter("task3/src/main/resources/app.properties"), "");
        } catch (IOException e) {
            log.error("Error in writing properties: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public void saveRecords(GameType gameType) {
        switch (gameType) {
            case NOVICE -> view.getHighScoresWindow().setNoviceRecord(properties.getProperty("nameNovice"),
                    Integer.parseInt(properties.getProperty("timeValueNovice")));
            case MEDIUM ->  view.getHighScoresWindow().setMediumRecord(properties.getProperty("nameMedium"),
                    Integer.parseInt(properties.getProperty("timeValueMedium")));
            case EXPERT ->  view.getHighScoresWindow().setExpertRecord(properties.getProperty("nameExpert"),
                    Integer.parseInt(properties.getProperty("timeValueExpert")));
        }
    }
    @Override
    public void update(int timerValue, GameType gameType) {
        int currentRecordValue = getRecordValue(gameType);
        if(timerValue < currentRecordValue) {
            RecordsWindow recordsWindow = new RecordsWindow(view.getMainWindow());
            recordsWindow.setNameListener(name -> {
                boolean update = updateRecords(gameType, name, timerValue);
                if (!update) {
                    log.error("Error: result {} is not written", timerValue);
                }  else {
                    saveRecords(gameType);
                }
            });
            recordsWindow.setVisible(true);
        }
    }
}

