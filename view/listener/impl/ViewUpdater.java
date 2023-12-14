package ru.cft.focus.view.listener.impl;

import ru.cft.focus.view.View;
import ru.cft.focus.view.constant.GameImage;
import ru.cft.focus.view.listener.ViewUpdateListener;

public class ViewUpdater implements ViewUpdateListener {
    private final View view;

    public ViewUpdater(View view) {
        this.view = view;
    }

    @Override
    public void updateCell(int x, int y, int value) {
        switch (value) {
            case -1 -> view.getMainWindow().setCellImage(x, y, GameImage.BOMB);
            case 0 -> view.getMainWindow().setCellImage(x, y, GameImage.EMPTY);
            case 1 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_1);
            case 2 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_2);
            case 3 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_3);
            case 4 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_4);
            case 5 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_5);
            case 6 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_6);
            case 7 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_7);
            case 8 -> view.getMainWindow().setCellImage(x, y, GameImage.NUM_8);
        }
    }

    @Override
    public void updateFlagOnCell(int x, int y, boolean flag) {
        if (flag) {
            view.getMainWindow().setCellImage(x, y, GameImage.MARKED);
        } else {
            view.getMainWindow().setCellImage(x, y, GameImage.CLOSED);
        }
    }

    @Override
    public void startNewGame() {
        for (int i = 0; i < view.getMainWindow().getController().getGameType().getWidth(); i++) {
            for (int j = 0; j < view.getMainWindow().getController().getGameType().getLength(); j++) {
                view.getMainWindow().setCellImage(j, i, GameImage.CLOSED);
            }
        }
    }

    @Override
    public void gameOver() {
        int width = view.getMainWindow().getController().getGameType().getWidth();
        int length = view.getMainWindow().getController().getGameType().getLength();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (view.getMainWindow().getController().isCellFlag(i, j)) {
                    if (!view.getMainWindow().getController().isCellMine(i, j)) {
                        updateCell(j, i, view.getMainWindow().getController().getValueCellIJ(i, j));
                    }
                } else {
                    if (view.getMainWindow().getController().isCellMine(i, j)) {
                        view.getMainWindow().setCellImage(j, i, GameImage.BOMB);
                    }
                }
            }
        }
        view.getLoseWindow().setVisible(true);
    }

    @Override
    public void win() {
        view.getWinWindow().setVisible(true);
    }

    @Override
    public void updateCountBombs() {
        view.getMainWindow().setBombsCount(view.getMainWindow().getController().getCountBombs());
    }
}
