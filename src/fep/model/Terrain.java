/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 *
 * @author Kyle
 */
public interface Terrain {
    
    
    /**
     * Returns the name of this terrain type.
     * @return the name of this terrain type.
     */
    public String getName();
    
    public TerrainBehavior getTerrainBehavior();
    
}
