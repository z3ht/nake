package me.zinno.nake.enums;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public enum Direction {

    NORTH(Arrays.asList(KeyEvent.VK_UP, KeyEvent.VK_W), new Dimension(0, 1)),
    EAST(Arrays.asList(KeyEvent.VK_RIGHT, KeyEvent.VK_D), new Dimension(1, 0)),
    SOUTH(Arrays.asList(KeyEvent.VK_DOWN, KeyEvent.VK_S), new Dimension(0, -1)),
    WEST(Arrays.asList(KeyEvent.VK_LEFT, KeyEvent.VK_A), new Dimension(-1, 0));

    public static Direction getDirection(Integer keyCode)  {
        for(Direction direction : Direction.values()) {
            for(Integer keyEvent : direction.getKeyEvents()) {
                if(keyCode == keyEvent) {
                    return direction;
                }
            }
        }
        return null;
    }

    private final Dimension location;
    private final List<Integer> keyEvents;

    Direction(List<Integer> keyEvents, Dimension location) {
        this.location = location;
        this.keyEvents = keyEvents;
    }

    public List<Integer> getKeyEvents() {
        return this.keyEvents;
    }

    public Dimension getLocation() {
        return this.location;
    }
}
