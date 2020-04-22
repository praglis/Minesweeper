package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Level;
import model.SaveModule;
import model.Score;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static main.Main.primaryStage;


public class GameController {

    @FXML
    public Button backButton;
    @FXML
    public Text messageText;
    private MinefieldController minefieldController;
    private int windowWidth;
    private int windowHeight;
    private int bottomPaneHeight;
    private double startTime;
    private Level level;

    public GameController(Level level, boolean gameLoaded) {
        primaryStage.setOnCloseRequest(e -> {
            try {
                SaveModule.saveGame(minefieldController.getGrid(), level, System.currentTimeMillis() - startTime);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            primaryStage.close();
            System.exit(0);
        });

        this.level = level;
        bottomPaneHeight = 120;


        if (gameLoaded) minefieldController = SaveModule.loadGame(level);
        else minefieldController = new MinefieldController(level, false);

        minefieldController.gameResultProperty().addListener((obs, oldResult, newBoolean) -> {
            System.out.println("listener gameResult");
            if (!newBoolean) {
                messageText.setVisible(true);
                messageText.setText("GAME OVER");
            } else {
                SaveModule.clearSave(level);
                double gameTime = System.currentTimeMillis() - startTime + minefieldController.getCurrentTime();
                Score score = new Score(level, gameTime);
                new SaveScoreController(score);
            }


        });

        windowWidth = level.getFieldWidth() * SquareController.SQUARE_SIZE;
        windowHeight = level.getFieldHeight() * SquareController.SQUARE_SIZE + bottomPaneHeight;


        Stage stage = primaryStage;
        stage.setTitle("MINESWEEPER - " + level.getName() + " game");
        stage.setScene(new Scene(createBoard()));
        stage.show();
        startTime = System.currentTimeMillis();
    }

    private Parent createBoard() {
        Pane root = new Pane();
        root.setPrefSize(windowWidth, windowHeight);

        for (int y = 0; y < minefieldController.getHeight(); y++) {
            for (int x = 0; x < minefieldController.getWidth(); x++) {
                root.getChildren().add(minefieldController.getGrid()[x][y]);
            }
        }

        messageText = new Text();
        messageText.setFont(Font.font("Rubik-Bold", 16));
        messageText.setFill(Color.RED);
        messageText.setLayoutX(windowWidth / 2 - 46.5);
        messageText.setLayoutY(windowHeight - bottomPaneHeight * 2 / 3);

        backButton = new Button("BACK");
        backButton.setFont(Font.font("Rubik-Bold", 16));
        backButton.setPrefSize(80, 30);
        backButton.setLayoutX(windowWidth / 2 - backButton.getPrefWidth() / 2);
        backButton.setLayoutY(windowHeight - bottomPaneHeight / 3 - backButton.getPrefHeight() / 2);
        EventHandler<ActionEvent> backButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (minefieldController.getGameResult() == null) {
                    try {
                        SaveModule.saveGame(minefieldController.getGrid(), level, System.currentTimeMillis() - startTime);
                    } catch (IOException e) {
                        System.exit(-2);
                    }
                } else if (!minefieldController.getGameResult()) {
                    SaveModule.clearSave(level);
                }

                new MainMenuController().createView();
            }
        };
        backButton.setOnAction(backButtonHandler);

        root.getChildren().addAll(backButton, messageText);

        return root;
    }

    private List<SquareController> getNeighbors(SquareController square) {
        List<SquareController> neighbors = new ArrayList<>();

        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = square.getX() + dx;
            int newY = square.getY() + dy;

            if (newX >= 0 && newX < this.minefieldController.getWidth()
                    && newY >= 0 && newY < this.minefieldController.getHeight()) {
                neighbors.add(minefieldController.getGrid()[newX][newY]);
            }
        }

        return neighbors;
    }
}
