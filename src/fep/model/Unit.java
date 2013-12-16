/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Represents a unit in FEP.
 * @author Kyle Buzsaki
 */
public class Unit {
    
     /*
     * Data Members:
     *  - Name
     *  - Position
     *  - UnitClass
     *  - Stats
     *  - Inventory
     *  - Faction?
     * 
     * Methods:
     *  - Stat Getters:
     *   - int getters (getStrength) (effective value)
     *   - attribute getter (getStrengthAttribute) (base + cap)
     *  - Apply Damage
     *  - Apply Effect
     */
    
    private Map map;
    
    public Unit() {}
    
    public boolean isOnMap() {
        return map != null;
    }
    
    public ImmutablePoint getPosition() {
        if(map != null) {
            return map.getPositionOf(this);
        }
        else {
            throw new IllegalStateException("Attempting to get the position of "
                    + "a unit that is not on a map.");
        }
    }
    
    /**
     * Sets the unit's map. This is an internal method that should only
     * be called by the addUnit() method of the {@link fep.model.Map} class. 
     * Users wishing to add a unit to a map should instead call {@link 
     * fep.model.Map#addUnit(fep.model.ImmutablePoint, fep.model.Unit) 
     * Map.addUnit(ImmutablePoint, Unit)} with this unit as an argument.
     * @param map the map to set this unit's map to.
     */
    void setMap(Map map) {
        if(this.map != null) {
            throw new IllegalStateException("Attempting to set a unit's map"
                    + " without first clearing its map. Possible failure to"
                    + " remove the unit from its original map.");
        }
        else {
            this.map = map;
        }
    }
    /**
     * Clears the unit's map. This is an internal method that should only
     * be called by the removeUnit() method of the {@link fep.model.Map} class. 
     * Users wishing to remove a unit from a map should instead call {@link 
     * fep.model.Map#removeUnit(fep.model.Unit) with this unit as an argument.
     * Map.removeUnit(Unit)}
     */
    void clearMap() {
        this.map = null;
    }
    
}
