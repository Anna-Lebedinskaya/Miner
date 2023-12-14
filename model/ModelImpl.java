package ru.cft.focus.model;

import ru.cft.focus.view.constant.GameType;
import ru.cft.focus.view.listener.RecordListener;
import ru.cft.focus.view.listener.ViewUpdateListener;
import ru.cft.focus.view.listener.impl.Timer;

public class ModelImpl implements Model {
    private GameType gameType;
    private Field field;
    private int countOpenCells;
    private int countBombs;
    private boolean isBang;

    private ViewUpdateListener viewUpdateListener;
    private Timer timer;
    private RecordListener recordListener;

    public ModelImpl(GameType gameType) {
        this.gameType = gameType;
        field = new Field(gameType.getWidth(), gameType.getLength(), gameType.getBombs());
        countBombs = gameType.getBombs();
    }

    @Override
    public void setViewUpdateListener(ViewUpdateListener viewUpdateListener) {
        this.viewUpdateListener = viewUpdateListener;
    }

    @Override
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void setRecordListener(RecordListener recordListener) {
        this.recordListener = recordListener;
    }

    @Override
    public GameType getGameType() {
        return gameType;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public int getCountBombs() {
        return countBombs;
    }

    @Override
    public void startNewGame() {
        countOpenCells = 0;
        countBombs = gameType.getBombs();
        isBang = false;
        field.initField();
        notify(GameEvent.NEW_GAME);
    }

    @Override
    public void changeGameType(GameType gameType) {
        this.gameType = gameType;
        field = new Field(gameType.getWidth(), gameType.getLength(), gameType.getBombs());
        countBombs = gameType.getBombs();
    }

    @Override
    public void openCells(int i, int j) {
        while (countOpenCells == 0 && field.getCell()[i][j].isMine()) {
            startNewGame();
        }

        if (countOpenCells == 0) {
            notify(GameEvent.START);
        }

        open(i, j);

        if (field.getCell()[i][j].getCountBombNear() == 0) {
            openAround(i, j);
        }

    }

    private void openAround(int i, int j) {
        for (int y = i - 1; y <= i + 1; y++) {
            for (int x = j - 1; x <= j + 1; x++) {
                if (checkCell(y, x) && (countOpenCells > 0)) {
                    openCells(y, x);
                }
            }
        }
    }

    private boolean checkCell(int i, int j) {
        if (i < 0 || i > gameType.getWidth() - 1 || j < 0 || j > gameType.getLength() - 1) {
            return false;
        }
        if (field.getCell()[i][j].isOpen()) {
            return false;
        }
        if (field.getCell()[i][j].isFlag()) {
            return false;
        }
        if (isBang) {
            return false;
        }
        return true;
    }

    private void open(int i, int j) {
        if (!field.getCell()[i][j].isOpen() && !isBang) {
            field.getCell()[i][j].open();
            isBang = field.getCell()[i][j].isMine();
            if (!isBang) {
                countOpenCells++;
                notify(GameEvent.OPEN_CELL, i, j);
                if (checkWin()) {
                    notify(GameEvent.WIN);
                }
            } else {
                notify(GameEvent.GAME_OVER);
            }
        }
    }

    @Override
    public void openCellsAround(int i, int j) {
        if (field.getCell()[i][j].isOpen()
            && field.getCell()[i][j].getCountBombNear() > 0
            && field.getCell()[i][j].getCountBombNear() == countFlagAround(i, j)) {
            openAround(i, j);
        }
    }

    private int countFlagAround(int i, int j) {
        int countFlags = 0;
        for (int y = i - 1; y <= i + 1; y++) {
            for (int x = j - 1; x <= j + 1; x++) {
                if (!(y < 0 || y > gameType.getWidth() - 1 || x < 0 || x > gameType.getLength() - 1)
                    && field.getCell()[y][x].isFlag()) {
                    countFlags++;
                }
            }
        }
        return countFlags;
    }

    @Override
    public boolean checkWin() {
        return countOpenCells == gameType.getWidth() * gameType.getLength() - gameType.getBombs();
    }

    @Override
    public void inverseFlagOnCell(int i, int j) {
        if (!field.getCell()[i][j].isOpen() && !isBang) {
            if (!field.getCell()[i][j].isFlag() && countBombs > 0) {
                field.getCell()[i][j].inverseFlag();
                countBombs--;
            } else if (field.getCell()[i][j].isFlag()) {
                field.getCell()[i][j].inverseFlag();
                countBombs++;
            }
            notify(GameEvent.COUNT_BOMB);
            notify(GameEvent.SET_FLAG, i, j);
        }
    }

    @Override
    public void notify(GameEvent gameEvent) {
        switch (gameEvent) {
            case NEW_GAME -> {
                if (viewUpdateListener != null) {
                    viewUpdateListener.startNewGame(); //уведомление о запуске новой игры
                    viewUpdateListener.updateCountBombs();//
                }
                if (timer != null) {
                    timer.stopTimer(); //уведомление об остановке таймера (если работает)
                    timer.clearTimer(); //уведомление об обнулении таймера
                }
            }

            case START -> timer.startTimer(); //уведомление о запуске таймера

            case COUNT_BOMB -> viewUpdateListener.updateCountBombs(); //уведомление об изменении счетчика бомб

            case WIN -> {
                timer.stopTimer(); //уведомление об остановке таймера
                recordListener.update(timer.getTimerValue(), gameType); //уведомление об обновлении рекордов
                viewUpdateListener.win(); //уведомление о победе
            }

            case GAME_OVER -> {
                timer.stopTimer(); //уведомление об остановке таймера
                viewUpdateListener.gameOver(); //уведомление о проигрыше
            }
        }
    }

    @Override
    public void notify(GameEvent gameEvent, int i, int j) {
        switch (gameEvent) {
            //уведомление об открытии ячейки
            case OPEN_CELL -> viewUpdateListener.updateCell(j, i, field.getValueCellIJ(i, j));
            //уведомление о смене флага
            case SET_FLAG -> viewUpdateListener.updateFlagOnCell(j, i, field.getCell()[i][j].isFlag());
        }
    }
}
