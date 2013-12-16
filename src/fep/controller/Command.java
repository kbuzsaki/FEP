/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.controller;

import fep.model.Direction;

/**
 * A single command representing a button press from a GameBoy Advance.
 * @author Kyle Buzsaki
 */
public enum Command {
    UP("Up"),
    DOWN("Down"),
    LEFT("Left"),
    RIGHT("Right"),
    A("A"),
    B("B"),
    L("L"),
    R("R"),
    START("Start"),
    SELECT("Select");
    
    public final String name;

    Command(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public Direction toDirection() {
        switch(this) {
            case UP: return Direction.UP;
            case DOWN: return Direction.DOWN;
            case LEFT: return Direction.LEFT;
            case RIGHT: return Direction.RIGHT;
            default:
                throw new UnsupportedOperationException("Cannot convert button " + this + " to a Direction.");
        }
    }
    
}

