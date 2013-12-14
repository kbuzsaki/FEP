/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Enumerates the built-in TerrainBehavior instances used and provided by FEP.
 * These are based on and synthesized from the information found at
 * <a href="http://serenesforest.net/fe7/terrain.html">Serenes Forest</a>
 * @author Kyle
 */
public enum DefaultTerrainBehavior implements TerrainBehavior {

    // Traditional "Open" Terrain
    FLAT_BEHAVIOR(0, 0, 0),
    SAND_BEHAVIOR(5, 0, 0),
    // Traditional "Rough" Terrain
    FOREST_BEHAVIOR(20, 1, 0),
    PILLAR_BEHAVIOR(20, 1, 0),
    DESERT_BEHAVIOR(5, 0, 0),
    // Mountainous Terrain
    MOUNTAIN_BEHAVIOR(30, 1, 0),
    PEAK_BEHAVIOR(40, 1, 0),
    // Watery Terrain
    RIVER_BEHAVIOR(0, 0, 0),
    LAKE_BEHAVIOR(10, 0, 0),
    SEA_BEHAVIOR(10, 0, 0),
    // City Terrain
    RUINS_BEHAVIOR(0, 0, 0),
    HOUSE_BEHAVIOR(10, 0, 0),
    FORT_BEHAVIOR(20, 2, 0),
    THRONE_BEHAVIOR(20, 2, 0),
    // Traditionally Impassable Terrain
    FENCE_BEHAVIOR(0, 0, 0), // can be flown over
    WALL_BEHAVIOR(0, 0, 0);  // cannot be flown over

    private final int bonusAvoid;
    private final int bonusDefense;
    private final int bonusResistance;

    private DefaultTerrainBehavior(int bonusAvoid, int bonusDefense, 
            int bonusResistance) {
        this.bonusAvoid = bonusAvoid;
        this.bonusDefense = bonusDefense;
        this.bonusResistance = bonusResistance;
    }

    @Override
    public int getBonusAvoid() {
        return bonusAvoid;
    }

    @Override
    public int getBonusDefense() {
        return bonusDefense;
    }

    @Override
    public int getBonusResistance() {
        return bonusResistance;
    }
    
}
