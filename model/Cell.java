package ru.cft.focus.model;

public class Cell {
    private boolean isOpen;
    private boolean isMine;
    private boolean isFlag;
    private int countBombNear = 0;

    public void open() {
        isOpen = true;
    }

    public void mine() {
        isMine = true;
    }

    public void inverseFlag() {
        isFlag = !isFlag;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public int getCountBombNear() {
        return countBombNear;
    }

    public void setCountBombNear(int countBombNear) {
        this.countBombNear = countBombNear;
    }
}
