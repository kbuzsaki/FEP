/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model.event;

import fep.model.ImmutablePoint;
import fep.model.Map;
import fep.model.Terrain;
import java.util.EventObject;

/**
 *
 * @author Kyle Buzsaki
 */
public class TerrainChangedEvent extends EventObject {

    private final Map map;
    private final ImmutablePoint pointAltered;
    private final Terrain previousTerrain;
    private final Terrain newTerrain;

    /**
     * Constructs a TerrainChangedEvent which represents the changing of the 
     * terrain at the given point on the given map from the given terrain to the
     * given terrain.
     * @param map  the map on which terrain was changed.
     * @param pointChanged  the point on the map where terrain was changed.
     * @param previousTerrain  the point's terrain before alteration
     * @param newTerrain   the point's terrain after alteration
     */
    public TerrainChangedEvent(Map map, ImmutablePoint pointChanged, Terrain previousTerrain, Terrain newTerrain) {
        super(map);
        this.map = map;
        this.pointAltered = pointChanged;
        this.previousTerrain = previousTerrain;
        this.newTerrain = newTerrain;
    }
    
    /**
     * Returns the map that terrain was changed on during this event.
     * @return the map changed.
     */
    public Map getMap() {
        return map;
    }
    
    /**
     * Returns the point that was changed on the map that was changed during this event. 
     * @return the point that was changed.
     */
    public ImmutablePoint getPointChanged() {
        return pointAltered;
    }

    /**
     * Returns the terrain of the point before it was changed during this event.
     * @return the terrain before it was changed.
     */
    public Terrain getPreviousTerrain() {
        return previousTerrain;
    }

    /**
     * Returns the terrain of the point after it was changed during this event.
     * @return the terrain after it was changed.
     */
    public Terrain getNewTerrain() {
        return newTerrain;
    }
    
    
    
}
