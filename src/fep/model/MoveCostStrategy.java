/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.List;

/**
 *
 * @author Kyle Buzsaki
 */
public interface MoveCostStrategy {

    int getCostOf(TerrainBehavior terrainBehavior);
    
    // JAVA8: should be a default method
    int getCostOf(List<TerrainBehavior> path);
    
}
