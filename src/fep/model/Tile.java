/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Represents a view of a single tile in the map. This class's data members are
 * immutable, though the objects viewed through it may be modified.
 * @author Kyle
 */
public class Tile {
    
    private Map map;
    private ImmutablePoint position;
    
    public Tile(Map map, ImmutablePoint position) {
        this.map = map;
        this.position = position;
    }
    
    public Unit getUnit() {
        return map.getUnitAt(position);
    }
    public Terrain getTerrainAt() {
        return map.getTerrainAt(position);
    }
    
}
