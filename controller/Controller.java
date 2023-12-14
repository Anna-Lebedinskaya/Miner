package ru.cft.focus.controller;

import ru.cft.focus.view.constant.GameType;

public interface Controller {
    boolean isCellFlag(int i, int j);
    boolean isCellMine(int i, int j);
    int getValueCellIJ(int i, int j);
    GameType getGameType();
    int getCountBombs();
    void startNewGame();
    void openCells(int x, int y) ;
    void openCellsAround(int x, int y) ;
    void inverseFlag(int x, int y);
    void changeGameType(GameType gameType);
}
