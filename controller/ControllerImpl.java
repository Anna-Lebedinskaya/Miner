package ru.cft.focus.controller;

import ru.cft.focus.model.Model;
import ru.cft.focus.view.constant.GameType;

public class ControllerImpl implements Controller {
    private final Model model;

    public ControllerImpl(Model model) {
        this.model = model;
    }

    @Override
    public boolean isCellFlag(int i, int j) {
        return model.getField().getCell()[i][j].isFlag();
    }

    @Override
    public boolean isCellMine(int i, int j) {
        return model.getField().getCell()[i][j].isMine();
    }

    @Override
    public int getValueCellIJ(int i, int j) {
        return model.getField().getValueCellIJ(i, j);
    }

    @Override
    public GameType getGameType() {
        return model.getGameType();
    }

    @Override
    public int getCountBombs() {
        return model.getCountBombs();
    }

    @Override
    public void startNewGame() {
        model.startNewGame();
    }

    @Override
    public void openCells(int x, int y) {
        model.openCells(y, x);
    }

    @Override
    public void openCellsAround(int x, int y) {
        model.openCellsAround(y, x);
    }

    @Override
    public void inverseFlag(int x, int y) {
        model.inverseFlagOnCell(y, x);
    }

    @Override
    public void changeGameType(GameType gameType) {
        model.changeGameType(gameType);
    }
}
