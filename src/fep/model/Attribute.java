/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Represents an attribute in a unit's stats. It 
 * @author Kyle
 */
public class Attribute {
    
    private int value;
    private int cap;
    
    /**
     * Constructs a new attribute with the specified value and value cap.
     * @param value  the value to assign to this attribute.
     * @param cap  the value cap to assign to this attribute.
     */
    public Attribute(int value, int cap) {
        this.value = value;
        this.cap = cap;
    }
    
    /**
     * Returns the value of this attribute.
     * @return the value of this attribute.
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Returns the cap for the value of this attribute.
     * @return the cap for the value of this attribute.
     */
    public int getCap() {
        return cap;
    }
    
    /**
     * Adds the specified delta to this attribute
     * @param deltaValue 
     */
    public void add(int deltaValue) {
        int possibleValue = value + deltaValue;
        if(!inBounds(possibleValue)) {
            throw new IllegalArgumentException("Illegal new value for Attribute:"
                    + possibleValue + ". Must be between 0 and cap (" + cap + ")");
        }
        else {
            value = possibleValue;
        }
    }
    
    private boolean inBounds(int possibleValue) {
        return 0 <= possibleValue && possibleValue <= cap;
    }
    
// TODO: levelUp method? or handled from within unit?
    
}
