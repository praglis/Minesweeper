package controller;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Square;

public class SquareController extends StackPane {
    public static final int SQUARE_SIZE = 30;
    public static final Color borderColor = Color.LIGHTGRAY;
    public static final Color flagColor = Color.ORANGERED;
    Color color;
    private Square square;
    private Rectangle border = new Rectangle(SQUARE_SIZE - 2, SQUARE_SIZE - 2);
    private Text text;

    public SquareController(int x, int y) {
        square = new Square(x, y);
        text = new Text();

        border.setStroke(Color.GRAY);
        border.setFill(borderColor);
        text.setFont(Font.font("Rubik-Bold", 18));
    }

    public void setMine(boolean mine) {
        square.setMine(mine);
        text.setText("⦿");
    }

    public Text getText() {
        return text;
    }

    public int getX() {
        return square.getX();
    }

    public int getY() {
        return square.getY();
    }

    public boolean hasMine() {
        return square.hasMine();
    }

    public void incrementValue() {
        square.incrementValue();
    }

    public boolean isUncovered() {
        return square.isUncovered();
    }

    public void setUncovered(boolean uncovered) {
        square.setUncovered(uncovered);
        if (uncovered) border.setFill(null);
        else border.setFill(borderColor);
        text.setVisible(uncovered);
    }

    public Rectangle getBorde() {
        return border;
    }

    public char getSymbol() {
        return square.getSymbol();
    }

    public void setSymbol(char symbol) {
        square.setSymbol(symbol);
        if (symbol == '0') text.setText(" ");
        else {
            text.setText(String.valueOf(symbol));
            setColor(symbol);
        }
    }

    private void setColor(char symbol) {
        switch (symbol) {
            case '1':
                color = Color.BLUE;
                text.setFill(Color.BLUE);
                break;
            case '2':
                color = Color.GREEN;
                text.setFill(Color.GREEN);
                break;
            case '3':
                color = Color.RED;
                text.setFill(Color.RED);
                break;
            case '4':
                color = Color.DARKBLUE;
                text.setFill(Color.DARKBLUE);
                break;
            case '5':
                color = Color.DARKRED;
                text.setFill(Color.DARKRED);
                break;
            case '6':
                color = Color.DARKGREEN;
                text.setFill(Color.DARKGREEN);
                break;
            case '7':
                color = Color.DARKCYAN;
                text.setFill(Color.DARKCYAN);
                break;
            case '8':
                color = Color.DARKMAGENTA;
                text.setFill(Color.DARKMAGENTA);
                break;
        }
    }

    public void init() {
        if (square.hasMine()) {
            setMine(true);
        } else {
            setSymbol(square.getSymbol());
        }

        text.setVisible(false);

        getChildren().addAll(border, text);

        setTranslateX(square.getX() * SQUARE_SIZE);
        setTranslateY(square.getY() * SQUARE_SIZE);

    }

    public void flipFlag() {
        if (this.isUncovered())
            return;
        if (square.isFlagged()) {
            square.setFlagged(false);
            text.setText(String.valueOf(square.getSymbol()));
            text.setFill(color);
            this.getText().setVisible(false);
        } else {
            square.setFlagged(true);
            text.setText("⚑");
            text.setFill(flagColor);
            this.getText().setVisible(true);
        }

    }

    public boolean isFlagged() {
        return square.isFlagged();
    }
}