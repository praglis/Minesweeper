package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.Level;
import model.SaveModule;

import java.io.IOException;

import static main.Main.primaryStage;

public class DifficultySelectController {
    @FXML
    public Button beginner;

    public DifficultySelectController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/difficulty_select.fxml"));
        loader.setController(this);
        primaryStage.setTitle("Select difficulty level");
        try {
            primaryStage.setScene(new Scene(loader.load(), 600, 575));
        } catch (IOException ignored) {
        }
        primaryStage.show();
    }

    public void newGame(ActionEvent actionEvent) {
        String selectedLevelName = ((Button) actionEvent.getSource()).getText();
        Level level = new Level(selectedLevelName);

        if (SaveModule.isGameSaved(level)) new LoadGameController(level);
        else new GameController(level, false);
    }

    public void back() {
        new MainMenuController().createView();
    }
}
