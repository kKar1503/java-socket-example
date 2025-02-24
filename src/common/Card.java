package common;

import java.io.Serializable;

public class Card implements Serializable {
    private final int number;
    private final Color color;

    public Card(int number, Color color) {
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", color=" + color +
                '}';
    }
}