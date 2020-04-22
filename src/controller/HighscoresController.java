package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import model.HighscoresLoader;

import java.io.FileNotFoundException;
import java.io.IOException;

import static main.Main.primaryStage;

public class HighscoresController {
    HighscoresLoader highscoresLoader;

    @FXML
    private ListView beginnerList;
    @FXML
    private ListView intermediateList;
    @FXML
    private ListView expertList;

    public HighscoresController() throws FileNotFoundException {
        this.highscoresLoader = new HighscoresLoader();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/highscores.fxml"));
        loader.setController(this);
        primaryStage.setTitle("MINESWEEPER - highscores");
        try {
            primaryStage.setScene(new Scene(loader.load(), 800, 600));
        } catch (IOException ignored) {
        }
        primaryStage.show();
    }

    @FXML
    public void initialize(){
        beginnerList.setItems(highscoresLoader.getBeginnerScores());
        intermediateList.setItems(highscoresLoader.getIntermediateScores());
        expertList.setItems(highscoresLoader.getExpertScores());
    }

    public void back() {
        new MainMenuController().createView();
    }
}
