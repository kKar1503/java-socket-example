package common;

import java.io.Serializable;

public enum Color implements Serializable /* implements Serializable to make the object sendable
    across the network */ {
    BLUE,
    RED,
    YELLOW,
    BLACK,
    GREEN,
    PURPLE,
}