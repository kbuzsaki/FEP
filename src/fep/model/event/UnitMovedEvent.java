/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model.event;

import fep.model.ImmutablePoint;
import fep.model.Map;
import fep.model.Unit;
import java.util.EventObject;

/**
 * Represents the moving of a unit on a map.
 * @author Kyle Buzsaki
 */
public class UnitMovedEvent extends EventObject {

    private final Map map;
    private final Unit unitMoved;
    private final ImmutablePoint newPosition;
    private final ImmutablePoint priorPosition;

    /**
     * Constructs a UnitMovedEvent representing the moving of the given unit
     * on the given map from the given point to the given point.
     * @param map  the on which the unit was moved.
     * @param unitMoved  the unit moved.
     * @param priorPosition  the position the unit was moved from.
     * @param newPosition  the position the unit was moved to.
     */
    public UnitMovedEvent(Map map, Unit unitMoved, ImmutablePoint priorPosition, ImmutablePoint newPosition) {
        super(map);
        this.map = map;
        this.unitMoved = unitMoved;
        this.priorPosition = priorPosition;
        this.newPosition = newPosition;
    }
    
    /**
     * Returns the map that a unit was moved on during this event.
     * @return the map moved on.
     */
    public Map getMap() {
        return map;
    }
    
    /**
     * Returns the unit that was moved on a map during this event.
     * @return the unit moved.
     */
    public Unit getUnitMoved() {
        return unitMoved;
    }
    
    /**
     * Returns the point that the unit was moved to during this event.
     * @return the point moved to.
     */
    public ImmutablePoint getNewPosition() {
        return newPosition;
    }
    
    /**
     * Returns the point that the unit was moved from during this event.
     * @return the point moved from.
     */
    public ImmutablePoint getPriorPosition() {
        return priorPosition;
    }
    
}
