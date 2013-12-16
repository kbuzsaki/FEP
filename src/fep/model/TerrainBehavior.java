/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Describes the game mechanic related properties of a piece of Terrain.
 * TerrainBehavior objects are intended to make the expression of Terrain
 * objects terser, allowing many nominally different Terrain types to share
 * the same behavior.
 * TerrainBehavior defines the bonuses to battle attributes provided by a piece
 * of Terrain.
 * Additionally, a Terrain's TerrainBehavior is used by MoveCostStrategy objects
 * to determine the cost of moving through the Terrain.
 * Instances of TerrainBehavior are expected to be unique instances, typically
 * expressed as enumerations.
 * @author Kyle Buzsaki
 */
public interface TerrainBehavior {
    
    /**
     * Returns this terrain's bonus avoid.
     * @return  this terrain's bonus avoid.
     */
    public int getBonusAvoid();
    /**
     * Returns this terrain's bonus defense.
     * @return  this terrain's bonus defense.
     */
    public int getBonusDefense();
    /**
     * Returns this terrain's resistance or magical defense.
     * @return  this terrain's resistance or magical defense.
     */
    public int getBonusResistance();
}
