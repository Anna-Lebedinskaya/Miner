package ru.cft.focus.model;

import ru.cft.focus.view.constant.GameType;
import ru.cft.focus.view.listener.RecordListener;
import ru.cft.focus.view.listener.ViewUpdateListener;
import ru.cft.focus.view.listener.impl.Timer;

public interface Model {
    void setViewUpdateListener(ViewUpdateListener viewUpdateListener);
    void setTimer(Timer timer);
    void setRecordListener(RecordListener recordListener);
    GameType getGameType();
    Field getField();
    int getCountBombs();
    void startNewGame();
    void changeGameType(GameType gameType);
    void openCells(int i, int j);
    void openCellsAround(int i, int j);
    boolean checkWin();
    void inverseFlagOnCell(int i, int j);
    void notify(GameEvent gameEvent);
    void notify(GameEvent gameEvent, int i, int j);
}
