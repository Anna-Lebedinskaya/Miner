package ru.cft.focus.app;

import ru.cft.focus.controller.Controller;
import ru.cft.focus.controller.ControllerImpl;
import ru.cft.focus.model.Model;
import ru.cft.focus.model.ModelImpl;
import ru.cft.focus.view.View;
import ru.cft.focus.view.constant.GameType;
import ru.cft.focus.view.listener.RecordListener;
import ru.cft.focus.view.listener.ViewUpdateListener;
import ru.cft.focus.view.listener.impl.Records;
import ru.cft.focus.view.listener.impl.Timer;
import ru.cft.focus.view.listener.impl.ViewUpdater;

public class Application {
    public static void main(String[] args) {
        Model model = new ModelImpl(GameType.NOVICE);
        Controller controller = new ControllerImpl(model);
        View view = new View(controller);

        ViewUpdateListener viewUpdateListener = new ViewUpdater(view);
        Timer timer = new Timer(view);
        RecordListener records = new Records(view);

        model.setTimer(timer);
        model.setRecordListener(records);
        model.setViewUpdateListener(viewUpdateListener);

        controller.startNewGame();
    }
}
