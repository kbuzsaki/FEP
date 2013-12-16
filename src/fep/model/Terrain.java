/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Represents a square of terrain on a map. Links the name of a specific terrain
 * piece with its gameplay behavior, as defined by {@link fep.model.TerrainBehavior}.
 * @author Kyle Buzsaki
 */
public interface Terrain {
    
    /**
     * Returns the name of this terrain type.
     * @return the name of this terrain type.
     */
    public String getName();
    
    // not sure how I feel about this method.
    // It's a pretty ugly way of abstracting out the behavior.
    // Ideally, the fact that a DefaultTerrainBehavior is used should be invisible to the user.
    // This is needed for movement calculations though...
    public TerrainBehavior getTerrainBehavior();
    
}
