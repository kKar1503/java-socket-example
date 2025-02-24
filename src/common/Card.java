package common;

import java.io.Serializable;

public class Card implements Serializable /* implements Serializable to make the object sendable
    across the network */ {
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