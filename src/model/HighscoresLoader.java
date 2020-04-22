package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HighscoresLoader {
    private ObservableList<Score>[] scores;

    public HighscoresLoader() throws FileNotFoundException {
        scores = new ObservableList[3];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = FXCollections.observableArrayList();
        }

        String fileSeparator = System.getProperty("file.separator");
        Scanner scanner = new Scanner(new File("res" + fileSeparator + "scores.txt"));

        int levelNumber = -1;
        String line = scanner.nextLine();
        while (line != null) {
            if (line.startsWith("$")) levelNumber++;
            else {
                String columns[] = line.split("=");
                scores[levelNumber].add(new Score(Level.intToLevel(levelNumber), Double.parseDouble(columns[0]), columns[1]));
            }
            if (scanner.hasNextLine()) line = scanner.nextLine();
            else break;
        }

        scanner.close();
    }

    public ObservableList<Score> getBeginnerScores() {
        return scores[0];
    }

    public ObservableList<Score> getIntermediateScores() {
        return scores[1];
    }

    public ObservableList<Score> getExpertScores() {
        return scores[2];
    }
}
