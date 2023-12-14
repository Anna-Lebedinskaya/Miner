package ru.cft.focus.view.listener;

import ru.cft.focus.view.constant.ButtonType;

public interface CellEventListener { //интерфейс приемника событий
    void onMouseClick(int x, int y, ButtonType buttonType);
}
