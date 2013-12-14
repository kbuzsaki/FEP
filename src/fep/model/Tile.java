/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Represents a view of a single tile in the map. The tile will always view
 * the same location on the Map, though the map itself may be altered.
 * @author Kyle
 */
public class Tile {
    
    /**
     * The map that this Tile is a part of. 
     * Used to view this Tile's properties.
     */
    private final Map map;
    /**
     * This Tile's position on its map.
     */
    private final ImmutablePoint position;
    
    /**
     * Creates a new Tile viewing the specified position on the specified map.
     * @param map  the map that this Tile will view.
     * @param position  the position of this Tile on the map. Must be within the map's boundaries.
     */
    public Tile(Map map, ImmutablePoint position) {
        if(!position.inBounds(0, 0, map.getWidth(), map.getHeight())) {
            throw new IllegalArgumentException("Tile position must be within the map boundaries.");
        }
        
        this.map = map;
        this.position = position;
    }
    
    /**
     * Returns the Unit present in this tile of the Map. If no unit is present,
     * returns null.
     * @return  the unit at this Tile on the Map.
     */
    public Unit getUnit() {
        return map.getUnitAt(position);
    }
    /**
     * Returns the Terrain at this position on the Map. Guaranteed to be non-null.
     * @return  the Terrain at this Tile on the Map.
     */
    public Terrain getTerrainAt() {
        return map.getTerrainAt(position);
    }
    
}
