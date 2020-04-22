package model;

import controller.MinefieldController;
import controller.SquareController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveModule {

    public static void saveGame(SquareController[][] grid, Level level, double currentTime) throws IOException {
        clearSave(level);

        String fileSeparator = System.getProperty("file.separator");
        FileWriter fileWriter = new FileWriter(new File("res" + fileSeparator + level.getName() + "_save"));

        //fileWriter.write(level.getName() + "\n");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].isUncovered()) {
                    fileWriter.write('[');
                } else if (grid[i][j].isFlagged()) {
                    fileWriter.write('{');
                } else {
                    fileWriter.write('<');
                }

                if (grid[i][j].hasMine()) {
                    fileWriter.write('@');
                } else {
                    fileWriter.write(grid[i][j].getSymbol());
                }
            }
            fileWriter.write('\n');
        }
        fileWriter.write(String.valueOf(currentTime));
        fileWriter.close();
    }

    public static MinefieldController loadGame(Level level) {
        String fileSeparator = System.getProperty("file.separator");

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("res" + fileSeparator + level.getName() + "_save"));
        } catch (FileNotFoundException e) {
            System.exit(-3);
        }

        //Level level = new Level(scanner.nextLine());
        MinefieldController minefieldController = new MinefieldController(level, true);

        for (int i = 0; i < minefieldController.getGrid().length; i++) {
            String line = scanner.nextLine();

            for (int j = 0; j < minefieldController.getGrid()[0].length; j++) {
                if (line.charAt(j * 2 + 1) == '@') {
                    minefieldController.getGrid()[i][j].setMine(true);
                } else {
                    minefieldController.getGrid()[i][j].setSymbol(line.charAt(j * 2 + 1));
                }

                if (line.charAt(j * 2) == '[') {
                    minefieldController.getGrid()[i][j].setUncovered(true);
                    minefieldController.incrementUncoveredSquaresCount();
                } else if (line.charAt(j * 2) == '{') {
                    minefieldController.getGrid()[i][j].setUncovered(false);
                    minefieldController.incrementUncoveredSquaresCount();
                    minefieldController.getGrid()[i][j].flipFlag();
                } else {
                    minefieldController.getGrid()[i][j].setUncovered(false);
                }
            }

        }
        //String help = scanner.next();
        minefieldController.setCurrentTime(Double.parseDouble(scanner.next()));
        scanner.close();
        return minefieldController;
    }

    public static boolean isGameSaved(Level level) {
        String fileSeparator = System.getProperty("file.separator");
        File savedGame = new File("res" + fileSeparator + level.getName() + "_save");
        return savedGame.exists();
    }

    public static void clearSave(Level level) {
        String fileSeparator = System.getProperty("file.separator");

        File save = new File("res" + fileSeparator + level.getName() + "_save");
        if (save.exists()) {
            save.delete();
        }
    }
}
