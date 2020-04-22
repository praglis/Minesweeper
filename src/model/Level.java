package model;

public class Level {
    private String name;
    private int fieldWidth;
    private int fieldHeight;
    private int mineCount;

    public Level(String name, int fieldWidth, int fieldHeight, int mineCount) {
        this.name = name.toLowerCase();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.mineCount = mineCount;
    }

    public Level(String name) {
        this.name = name.toLowerCase();
        switch (this.name) {
            case "beginner":
                fieldHeight = 9;
                fieldWidth = 9;
                mineCount = 10;
                break;
            default:
            case "intermediate":
                fieldHeight = 16;
                fieldWidth = 16;
                mineCount = 40;
                break;
            case "expert":
                fieldHeight = 16;
                fieldWidth = 30;
                mineCount = 99;
                break;
        }
    }

    public static Level intToLevel(int number) {
        switch (number) {
            case 0:
                return new Level("beginner");
            case 1:
                return new Level("intermediate");
            case 2:
            default:
                return new Level("expert");
        }
    }

    public String getName() {
        return name;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }
}
