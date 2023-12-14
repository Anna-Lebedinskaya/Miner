package ru.cft.focus.model;

import java.util.Random;

public class Field {
    private final int width;
    private final int length;
    private final int countBomb;
    private final Cell[][] cell;

    public Field(int width, int length, int countBomb) {
        this.width = width;
        this.length = length;
        this.countBomb = countBomb;
        cell = new Cell[width][length];
    }

    public Cell[][] getCell() {
        return cell;
    }

    public int getValueCellIJ(int i, int j) {
        if (cell[i][j].isMine()) {
            return -1;
        } else if (cell[i][j].getCountBombNear() == 0) {
            return 0;
        } else {
            return cell[i][j].getCountBombNear();
        }
    }

    public void initField() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                cell[i][j] = new Cell();
            }
        }
        fillRandomBombs();
        fillDigitCells();
    }

    private void fillRandomBombs() {
        Random random = new Random();
        int count = 0;
        int i;
        int j;
        while (count < countBomb) {
            do {
                i = random.nextInt(width);
                j = random.nextInt(length);
            } while (cell[i][j].isMine());
            cell[i][j].mine();
            count++;
        }
    }

    private void fillDigitCells() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (!cell[i][j].isMine()) {
                    int count = 0;
                    for (int di = -1; di < 2; di++)
                        for (int dj = -1; dj < 2; dj++) {
                            int nI = i + di;
                            int nJ = j + dj;
                            if (nI < 0 || nJ < 0 || nI > width - 1 || nJ > length - 1) {
                                nI = i;
                                nJ = j;
                            }
                            count += (cell[nI][nJ].isMine()) ? 1 : 0;
                        }
                    cell[i][j].setCountBombNear(count);
                }
            }
        }
    }
}


