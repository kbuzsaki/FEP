/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.List;

/**
 * Represents an algorithm for determining the movement cost for a unit moving
 * along a given path. 
 * @author Kyle Buzsaki
 */
public interface MoveCostStrategy {

    // NOTE: potentially switch to operating on Tile and List<Tile>? 
    // would allow for movement cost that changes depending on the angle of approach
    
    /**
     * Determines the movement cost this MoveCostStrategy would incur when 
     * moving over a piece of terrain with the given TerrainBehavior.
     * @param terrainBehavior  the behavior of the terrain to be moved over
     * @return  the cost of moving over terrain with the given behavior
     */
    int getCostOf(TerrainBehavior terrainBehavior);
    
    // JAVA8: should be a default method?
    /**
     * Determines the movement cost this MoveCostStrategy would incur when
     * moving over a series of terrain pieces with the given behaviors.
     * @param path  ordered list of the behaviors of the terrain to be moved over
     * @return  the total cost of moving over the path with the given terrain behaviors.
     */
    int getCostOf(List<TerrainBehavior> path);
    
}
