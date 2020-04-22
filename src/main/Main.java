package main;

import controller.MainMenuController;
import controller.WelcomeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        WelcomeController welcomeController = new WelcomeController();
        MainMenuController mainMenuController = new MainMenuController();

        Platform.runLater(() -> {
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.submit(() -> Platform.runLater(welcomeController::createView));

            executor.schedule(
                    () -> Platform.runLater(() -> {
                        primaryStage.hide();
                        mainMenuController.createView();
                    })
                    , 4
                    , TimeUnit.SECONDS);
        });
        /*primaryStage.setOnCloseRequest(e -> {
            primaryStage.close();
            System.exit(0);
        });*/
    }
}
