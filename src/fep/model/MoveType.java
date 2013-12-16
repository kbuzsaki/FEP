/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.List;
import static fep.model.DefaultTerrainBehavior.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author Kyle Buzsaki
 */
public enum MoveType implements MoveCostStrategy {

    FOOT,
    ARMOR,
    KNIGHT_A,
    KNIGHT_B,
    NOMAD_A,
    NOMAD_B,
    FIGHTER,
    BANDIT,
    PIRATE,
    BERSERKER,
    MAGE,
    FLYER;
    
    private static final int IMPASSABLE = Integer.MAX_VALUE;
    private static final java.util.Map<MoveType, java.util.Map<TerrainBehavior, Integer>> moveCostTable;
    
    private static void setMapEntries(java.util.Map<MoveType, java.util.Map<TerrainBehavior, Integer>> map,
            TerrainBehavior terrain, int value, MoveType... moveTypes) {
        for(MoveType moveType : moveTypes) {
            map.get(moveType).put(terrain, value);
        }
    }
    static {
        // Collections of move types and terrain types for convenience
        MoveType[] moveTypes = MoveType.values();
        Collection<DefaultTerrainBehavior> terrainTypes = 
                Collections.unmodifiableCollection(
                Arrays.asList(DefaultTerrainBehavior.values()));
        
        java.util.Map<MoveType, java.util.Map<TerrainBehavior, Integer>> table = new HashMap<>();
        // initialize it to default impassable for everything
        for(MoveType moveType : moveTypes) {
            java.util.Map<TerrainBehavior, Integer> moveTypeCosts = new HashMap<>();
            for(TerrainBehavior terrainBehavior : terrainTypes) {
                moveTypeCosts.put(terrainBehavior, IMPASSABLE);
            }
            table.put(moveType, moveTypeCosts);
        }
        
        // fill in move costs for different terrain
        
        // Open Terrains
        // Flat, House, Sand, Throne -- everyone moves at move cost 1
        setMapEntries(table, FLAT_BEHAVIOR, 1, moveTypes);
        setMapEntries(table, HOUSE_BEHAVIOR, 1, moveTypes);
        setMapEntries(table, SAND_BEHAVIOR, 1, moveTypes);
        setMapEntries(table, THRONE_BEHAVIOR, 1, moveTypes);
        
        // Ruins, Fort -- everyone moves at move cost 2
        setMapEntries(table, RUINS_BEHAVIOR, 2, moveTypes);
        setMapEntries(table, FORT_BEHAVIOR, 2, moveTypes);
        
        // Rough Terrains
        // Desert -- default 2 | armor, fighter, nomad 3 | 4 knight | 1 mage
        setMapEntries(table, DESERT_BEHAVIOR, 2, moveTypes);
        setMapEntries(table, DESERT_BEHAVIOR, 3, ARMOR, FIGHTER, NOMAD_A, NOMAD_B);
        setMapEntries(table, DESERT_BEHAVIOR, 4, KNIGHT_A, KNIGHT_B);
        setMapEntries(table, DESERT_BEHAVIOR, 1, MAGE);
        
        // Forest -- default 2 | knight 3 
        setMapEntries(table, FOREST_BEHAVIOR, 2, moveTypes);
        setMapEntries(table, FOREST_BEHAVIOR, 3, KNIGHT_A, KNIGHT_B);
        
        // Pillar -- default 2 | knight nomad 3
        setMapEntries(table, PILLAR_BEHAVIOR, 2, moveTypes);
        setMapEntries(table, PILLAR_BEHAVIOR, 3, KNIGHT_A, KNIGHT_B, NOMAD_A, NOMAD_B);
        
        // Mountain -- default 4 | fighter bandit pirate berserker 3 | 
        //          -- nomad_b 5 | knight_b 6 | knight_a nomad_a armor impassable
        setMapEntries(table, MOUNTAIN_BEHAVIOR, 4, moveTypes);
        setMapEntries(table, MOUNTAIN_BEHAVIOR, 3, FIGHTER, BANDIT, PIRATE, BERSERKER);
        setMapEntries(table, MOUNTAIN_BEHAVIOR, 5, NOMAD_B);
        setMapEntries(table, MOUNTAIN_BEHAVIOR, 6, KNIGHT_B);
        setMapEntries(table, MOUNTAIN_BEHAVIOR, IMPASSABLE, KNIGHT_A, NOMAD_A, ARMOR);
        
        // River -- default 5 | pirate berserker 2 | armor fighter mage knight nomad_a impassable
        setMapEntries(table, RIVER_BEHAVIOR, 5, moveTypes);
        setMapEntries(table, RIVER_BEHAVIOR, 2, PIRATE, BERSERKER);
        setMapEntries(table, RIVER_BEHAVIOR, IMPASSABLE, ARMOR, FIGHTER, MAGE, KNIGHT_A, KNIGHT_B, NOMAD_A);
        
        // Impassable Terrains
        // Peak -- default impassable | bandit berserker 4
        setMapEntries(table, PEAK_BEHAVIOR, IMPASSABLE, moveTypes);
        setMapEntries(table, PEAK_BEHAVIOR, 4, BANDIT, BERSERKER);
        
        // Sea -- default impassable | pirate berserker 2
        setMapEntries(table, SEA_BEHAVIOR, IMPASSABLE, moveTypes);
        setMapEntries(table, SEA_BEHAVIOR, 2, PIRATE, BERSERKER);
        
        // Lake -- default impassable | pirate berserker 3
        setMapEntries(table, LAKE_BEHAVIOR, IMPASSABLE, moveTypes);
        setMapEntries(table, LAKE_BEHAVIOR, 3, PIRATE, BERSERKER);
        
        // Fence -- default impassable | flyers 1
        setMapEntries(table, FENCE_BEHAVIOR, IMPASSABLE, moveTypes);
        // set all terrain for flyers to be 1 (except walls)
        for(TerrainBehavior terrainBehavior : table.get(FLYER).keySet()) {
            table.get(FLYER).put(terrainBehavior, 1);
        }
        
        // Wall -- impassable by everything
        setMapEntries(table, WALL_BEHAVIOR, IMPASSABLE, moveTypes);
        
        moveCostTable = Collections.unmodifiableMap(table);
    }

    @Override
    public int getCostOf(TerrainBehavior terrainBehavior) {
        return moveCostTable.get(this).get(terrainBehavior);
    }

    // should be a default method of MoveCostStrategy
    @Override
    public int getCostOf(List<TerrainBehavior> path) {
        int totalCost = 0;
        // do not count the first point in move cost
        for(int i = 1; i < path.size(); i++) {
            totalCost += getCostOf(path.get(i));
        }
        return totalCost;
    }

}
