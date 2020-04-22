package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static main.Main.primaryStage;

public class WelcomeController {
    private Stage stage;

    public void createView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/welcome.fxml"));
        loader.setController(this);
        primaryStage.setTitle("Welcome to MINESWEEPER");
        try {
            primaryStage.setScene(new Scene(loader.load(), 600, 575));
        } catch (IOException ignored) {
        }
        primaryStage.show();
    }
}
