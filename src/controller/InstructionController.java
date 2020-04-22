package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

import static main.Main.primaryStage;

public class InstructionController {
    public InstructionController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/instruction.fxml"));
        loader.setController(this);
        primaryStage.setTitle("How to play");
        try {
            primaryStage.setScene(new Scene(loader.load(), 600, 575));
        } catch (IOException ignored) {
        }
        primaryStage.show();
    }

    public void back(){
        new MainMenuController().createView();
    }
}