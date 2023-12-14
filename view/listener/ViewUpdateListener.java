package ru.cft.focus.view.listener;

public interface ViewUpdateListener {
    void updateCell(int x, int y, int value);
    void updateFlagOnCell(int x, int y, boolean flag);
    void startNewGame();
    void gameOver();
    void win();
    void updateCountBombs();
}
