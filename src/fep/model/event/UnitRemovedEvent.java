/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model.event;

import fep.model.Map;
import fep.model.Unit;
import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * Represents the removal of a unit from a map.
 * @author Kyle
 */
public class UnitRemovedEvent extends EventObject {
    
    private final Map map;
    private final Unit unitRemoved;

    /**
     * Constructs a UnitRemovedEvent representing the removal of the given unit
     * from the given map.
     * @param map  the map removed from.
     * @param unitRemoved  the unit removed.
     */
    public UnitRemovedEvent(Map map, Unit unitRemoved) {
        super(map);
        this.map = map;
        this.unitRemoved = unitRemoved;
    }
    
    /**
     * Returns the map that a unit was removed from during this event.
     * @return the map removed from.
     */
    public Map getMap() {
        return map;
    }
    
    /**
     * Returns the unit that was removed from a map during this event.
     * @return the unit removed.
     */
    public Unit getUnitRemoved() {
        return unitRemoved;
    }
    
}
