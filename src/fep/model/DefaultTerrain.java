/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 *
 * @author Kyle
 */
public enum DefaultTerrain implements Terrain {

    PLAINS("Plains", DefaultTerrainBehavior.FLAT_BEHAVIOR),
    FOREST("Forest", DefaultTerrainBehavior.FOREST_BEHAVIOR),
    PEAK("Peak", DefaultTerrainBehavior.PEAK_BEHAVIOR),
    WALL("Wall", DefaultTerrainBehavior.WALL_BEHAVIOR);

    private final String name;
    private final TerrainBehavior terrainBehavior;

    private DefaultTerrain(String name, TerrainBehavior terrainBehavior) {
        this.name = name;
        this.terrainBehavior = terrainBehavior;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TerrainBehavior getTerrainBehavior() {
        return terrainBehavior;
    }
    
}
