package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Score {
    private Level level;
    private double time;
    private String playerName;

    public Score(Level level, double time, String playerName) {
        this.level = level;
        this.time = time;
        this.playerName = playerName;
    }

    public Score(Level level, double time) {
        this.level = level;
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public void setPlayerName(String playerName) {
        if (playerName.startsWith("$")) {
            this.playerName = "S" + playerName.substring(1);
            return;
        }
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return playerName + ":   " + doubleToTimeString(time);
    }

    private String doubleToTimeString(double time) {
        double seconds = (int) (time / 1000);
        int minutes = (int) (seconds / 60.0);
        int hours = minutes / 60;

        if (hours == 0) {
            if (minutes == 0) {
                return seconds % 60 + "sec";
            }
            return minutes % 60 + "min " + seconds % 60 + "sec";
        }
        return hours + "h " + minutes % 60 + "min " + seconds % 60 + "sec";
    }

    public void save() throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        File oldScoresFile = new File("res" + fileSeparator + "scores.txt");
        if (!oldScoresFile.exists()) {
            initalizeScoresFile("scores.txt");
        }
        File newScoresFile = new File("res" + fileSeparator + "new_scores.txt");
        newScoresFile.createNewFile();

        RandomAccessFile oldScoresRAF = new RandomAccessFile(new File("res" + fileSeparator + "scores.txt"), "rw");
        RandomAccessFile newScoresRAF = new RandomAccessFile(new File("res" + fileSeparator + "new_scores.txt"), "rw");

        String levelLine;
        do {
            levelLine = oldScoresRAF.readLine();
            newScoresRAF.write((levelLine + "\n").getBytes());
        } while (levelLine != null && !levelLine.equals("$" + level.getName()));

        String playerLine = oldScoresRAF.readLine();

        while (playerLine != null && !playerLine.startsWith("$")) {
            String[] scoreEntry = playerLine.split("=");
            if (Double.parseDouble(scoreEntry[0]) > time) break;
            newScoresRAF.write((playerLine + "\n").getBytes());
            playerLine = oldScoresRAF.readLine();
        }
        //randomAccessFile.seek(position);
        newScoresRAF.write((time + "=" + playerName + "\n").getBytes());

        while (playerLine != null) {
            newScoresRAF.write((playerLine + "\n").getBytes());
            playerLine = oldScoresRAF.readLine();
        }


        oldScoresRAF.close();
        newScoresRAF.close();

        oldScoresFile.delete();
        oldScoresFile = null;
        System.gc();
        newScoresFile.renameTo(new File("res" + fileSeparator + "scores.txt"));
    }

    private void initalizeScoresFile(String fileName) throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        File scoresFile = new File("res" + fileSeparator + fileName);
        scoresFile.createNewFile();
        FileWriter writer = new FileWriter(scoresFile);
        writer.write("$beginner\n");
        writer.write("$intermediate\n");
        writer.write("$expert\n");
        writer.close();
    }
}
