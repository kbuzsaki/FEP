/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model.event;

import fep.model.Map;
import fep.model.Unit;
import java.util.EventObject;

/**
 * Represents the addition of a unit to a map.
 * @author Kyle Buzsaki
 */
public class UnitAddedEvent extends EventObject {
    
    private final Map map;
    private final Unit unitAdded;

    /**
     * Constructs a UnitAddedEvent representing the addition of the given unit
     * to the given map.
     * @param map  the map added to.
     * @param unitAdded  the unit added.
     */
    public UnitAddedEvent(Map map, Unit unitAdded) {
        super(map);
        this.map = map;
        this.unitAdded = unitAdded;
    }
    
    /**
     * Returns the map that a unit was added to during this event.
     * @return the map added to.
     */
    public Map getMap() {
        return map;
    }
    
    /**
     * Returns the unit that was added to a map during this event.
     * @return the unit added.
     */
    public Unit getUnitAdded() {
        return unitAdded;
    }
    
}
