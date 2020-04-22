package model;

public class Square {
    private char symbol;
    private int x, y;
    private boolean mine;
    private boolean uncovered;
    private boolean flagged;

    public Square(int x, int y) {
        this.symbol = '0';
        this.x = x;
        this.y = y;
        flagged = false;
        uncovered = false;
        mine = false;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean hasMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void incrementValue() {
        this.symbol++;
    }

    public boolean isUncovered() {
        return uncovered;
    }

    public void setUncovered(boolean uncovered) {
        this.uncovered = uncovered;
    }
}
