package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.FileNotFoundException;
import java.io.IOException;

import static main.Main.primaryStage;

public class MainMenuController {

    public void createView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/main_menu.fxml"));
        loader.setController(this);
        primaryStage.setTitle("MINESWEEPER - main menu");
        try {
            primaryStage.setScene(new Scene(loader.load(), 600, 575));
        } catch (IOException ignored) {
        }
        primaryStage.show();
    }

    public void start() {
        new DifficultySelectController();
    }

    public void howToPlay() {
        new InstructionController();
    }

    public void highscores() throws FileNotFoundException {
        new HighscoresController();
    }

    public void exit() {
        System.exit(0);
    }
}
