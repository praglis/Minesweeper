package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Score;

import java.io.IOException;

import static main.Main.primaryStage;

public class SaveScoreController {
    @FXML
    public TextField nameField;
    @FXML
    public Text timeText;

    private Score score;

    public SaveScoreController(Score score) {
        this.score = score;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/save_score.fxml"));
        loader.setController(this);
        //loader.setRoot(this);
        primaryStage.setTitle("You won!");
        try {
            primaryStage.setScene(new Scene(loader.load(), 600, 575));
        } catch (IOException ignored) {
        }
        primaryStage.show();

    }

    @FXML
    public void initialize(){
        timeText.setText(this.score.getTime() / 1000 + " seconds");
    }

    public void confirm() throws IOException {
        String playerName = nameField.getText();
        score.setPlayerName(playerName);
        score.save();
        new MainMenuController().createView();
    }
}
