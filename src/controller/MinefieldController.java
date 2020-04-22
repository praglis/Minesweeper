package controller;


import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import model.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinefieldController {
    private final ReadOnlyObjectWrapper<Boolean> gameResult = new ReadOnlyObjectWrapper<>();
    private int mineCount;
    private SquareController[][] grid;
    private int uncoveredSquaresCount;
    private double currentTime;

    public MinefieldController(Level level, boolean loadedGame) {
        uncoveredSquaresCount = 0;
        this.mineCount = level.getMineCount();
        this.grid = new SquareController[level.getFieldWidth()][level.getFieldHeight()];

        //create squares
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                grid[i][j] = new SquareController(i, j);
            }
        }

        if (!loadedGame) layMinesAndSetValues();

        //init squares
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                grid[i][j].init();
                int finalJ = j;
                int finalI = i;
                grid[i][j].setOnMouseClicked(mouseEvent -> {
                    System.out.println(mouseEvent.toString());
                    if (mouseEvent.getButton() == MouseButton.PRIMARY) uncoverSquare(grid[finalI][finalJ]);
                    else if (mouseEvent.getButton() == MouseButton.SECONDARY) grid[finalI][finalJ].flipFlag();
                });
            }
        }
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    private void layMinesAndSetValues() {
        Random rnd = new Random();
        int randX, randY;

        //lay mines
        for (int i = 0; i < mineCount; i++) {
            randX = rnd.nextInt(getWidth());
            randY = rnd.nextInt(getHeight());
            if (!grid[randX][randY].hasMine()) grid[randX][randY].setMine(true);
            else i--;
        }

        //set number values
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (!grid[i][j].hasMine()) {
                    int startCol, startRow, endCol, endRow;

                    if (i == 0) startCol = i;
                    else startCol = i - 1;
                    if (j == 0) startRow = j;
                    else startRow = j - 1;

                    if (i == getWidth() - 1) endCol = i;
                    else endCol = i + 1;
                    if (j == getHeight() - 1) endRow = j;
                    else endRow = j + 1;

                    for (; startCol <= endCol; startCol++) {
                        for (int k = startRow; k <= endRow; k++) {
                            if (grid[startCol][k].hasMine()) grid[i][j].incrementValue();
                        }
                    }
                }
            }
        }
    }

    public ReadOnlyObjectProperty<Boolean> gameResultProperty() {
        return gameResult.getReadOnlyProperty();
    }

    public Boolean getGameResult() {
        return gameResult.get();
    }

    public void uncoverSquare(SquareController squareController) {
        if (squareController.isUncovered())
            return;

        if (squareController.hasMine()) {
            gameResult.set(false);
            lockFieldAndShowMines();
            return;
        }

        if (squareController.isFlagged()) {
            squareController.flipFlag();
        }

        uncoveredSquaresCount++;
        if (uncoveredSquaresCount >= grid.length * grid[0].length - mineCount) gameResult.set(true);

        squareController.setUncovered(true);

        if (squareController.getSymbol() == '0') {
            getNeighbors(squareController).forEach(this::uncoverSquare);
        }
    }

    private void lockFieldAndShowMines() {
        for (SquareController[] squareControllers : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                squareControllers[j].setMouseTransparent(true);
                if (squareControllers[j].hasMine()) {
                    squareControllers[j].getText().setFill(Color.CRIMSON);
                    squareControllers[j].getText().setVisible(true);
                    squareControllers[j].getBorde().setFill(null);
                }
            }
        }
    }

    public SquareController[][] getGrid() {
        return grid;
    }

    public int getHeight() {
        return this.grid[0].length;
    }

    public int getWidth() {
        return this.grid.length;
    }

    public void incrementUncoveredSquaresCount(){
        this.uncoveredSquaresCount++;
    }

    public List<SquareController> getNeighbors(SquareController squareController) {
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

            int newX = squareController.getX() + dx;
            int newY = squareController.getY() + dy;

            if (newX >= 0 && newX < this.getWidth()
                    && newY >= 0 && newY < this.getHeight()) {
                neighbors.add(getGrid()[newX][newY]);
            }
        }
        return neighbors;
    }
}

