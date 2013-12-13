/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 *
 * @author Kyle
 */
public enum DefaultTerrainBehavior implements TerrainBehavior {

    /*
     * Desert
     * Fence
     * Flat
     * Forest
     * Fort
     * House
     * Lake
     * Mountain
     * Peak
     * River
     * Ruins
     * Sand
     * Sea
     * Throne
     * Wall
     */
    
    DESERT_BEHAVIOR(5, 0, 0),
    FENCE_BEHAVIOR(0, 0, 0),
    FLAT_BEHAVIOR(0, 0, 0),
    FOREST_BEHAVIOR(20, 1, 0),
    FORT_BEHAVIOR(20, 2, 0),
    HOUSE_BEHAVIOR(10, 0, 0),
    LAKE_BEHAVIOR(10, 0, 0),
    MOUNTAIN_BEHAVIOR(30, 1, 0),
    PEAK_BEHAVIOR(40, 1, 0),
    PILLAR_BEHAVIOR(20, 1, 0),
    RIVER_BEHAVIOR(0, 0, 0),
    RUINS_BEHAVIOR(0, 0, 0),
    SAND_BEHAVIOR(5, 0, 0),
    SEA_BEHAVIOR(10, 0, 0),
    THRONE_BEHAVIOR(20, 2, 0),
    WALL_BEHAVIOR(0, 0, 0);

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
