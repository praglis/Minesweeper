package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import model.Level;

import java.io.IOException;

import static main.Main.primaryStage;

public class LoadGameController {
    Level level;

    public LoadGameController(Level level) {
        this.level = level;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/load_game.fxml"));
        loader.setController(this);
        primaryStage.setTitle("Continue game?");
        try {
            primaryStage.setScene(new Scene(loader.load(), 600, 160));
        } catch (IOException ignored) {
        }
        primaryStage.show();
    }

    public void loadGame() {
        new GameController(level, true);
    }

    public void newGame() {
        new GameController(level, false);
    }

    public void cancel() {
        new DifficultySelectController();
    }
}