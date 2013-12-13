/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Represents a failure to find the specified unit, typically because the unit
 * is not present in the given data structure (e.g. a {@link fep.model.Map})
 * @author Kyle
 */
public class UnitNotFoundException extends IllegalStateException {
    
    private Unit unitNotFound;
    
    public UnitNotFoundException(Unit unitNotFound, String message) {
        super(message + " (Unit not found: " + unitNotFound + ")");
        this.unitNotFound = unitNotFound;
    }
    
    public UnitNotFoundException(Unit unitNotFound) {
        this(unitNotFound, "");
    }
    
    public Unit getUnitNotFound() {
        return unitNotFound;
    }
    
}
