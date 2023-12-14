package ru.cft.focus.view;

import lombok.Getter;
import ru.cft.focus.controller.Controller;
import ru.cft.focus.view.constant.GameType;

@Getter
public class View {
    private final MainWindow mainWindow;
    private final HighScoresWindow highScoresWindow;
    private final LoseWindow loseWindow;
    private final WinWindow winWindow;

    public View(Controller controller) {

        mainWindow = new MainWindow(controller);
        highScoresWindow = new HighScoresWindow(mainWindow);
        loseWindow = new LoseWindow(mainWindow);
        winWindow = new WinWindow(mainWindow);

        SettingsWindow settingsWindow = new SettingsWindow(mainWindow);

        settingsWindow.setGameTypeListener(gameType -> {
            settingsWindow.setGameType(gameType);
            controller.changeGameType(gameType);
            mainWindow.createGameField(gameType.getWidth(), gameType.getLength());
            controller.startNewGame();
        });

        mainWindow.setNewGameMenuAction(e -> controller.startNewGame());

        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));

        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));

        mainWindow.setExitMenuAction(e -> mainWindow.dispose());

        mainWindow.setCellListener((x, y, buttonType) -> {
            switch (buttonType) {
                case LEFT_BUTTON -> controller.openCells(x, y);
                case RIGHT_BUTTON -> controller.inverseFlag(x, y);
                case MIDDLE_BUTTON -> controller.openCellsAround(x, y);
            }
        });

        mainWindow.createGameField(GameType.NOVICE.getWidth(), GameType.NOVICE.getLength());
        mainWindow.setVisible(true);


        loseWindow.setNewGameListener(e -> {
            controller.startNewGame();
            loseWindow.setVisible(false);
        });
        loseWindow.setExitListener(e -> mainWindow.dispose());

        winWindow.setNewGameListener(e -> controller.startNewGame());
        winWindow.setExitListener(e -> mainWindow.dispose());
    }
}
