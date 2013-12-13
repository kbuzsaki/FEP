/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 *
 * @author Kyle
 */
public interface TerrainBehavior {
    
    /**
     * Returns this terrain's bonus avoid.
     * @return this terrain's bonus avoid.
     */
    public int getBonusAvoid();
    /**
     * Returns his terrain's bonus defense.
     * @returnthis terrain's bonus defense.
     */
    public int getBonusDefense();
    /**
     * Returns this terrain's resistance or magical defense.
     * @return this terrain's resistance or magical defense.
     */
    public int getBonusResistance();
}
