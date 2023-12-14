package ru.cft.focus.view.constant;

public enum GameType {
    NOVICE(9, 9, 10),
    MEDIUM(16, 16, 40),
    EXPERT(16, 30, 99)
    ;

    private final int width;
    private final int length;
    private final int bombs;

    GameType(int width, int length, int bombs) {
        this.width = width;
        this.length = length;
        this.bombs = bombs;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getBombs() {
        return bombs;
    }
}

