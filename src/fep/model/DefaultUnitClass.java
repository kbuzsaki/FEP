/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An enumeration of the default {@link UnitClass Unit Classes} in FEP. 
 * @author Kyle
 */
public enum DefaultUnitClass implements UnitClass {
    
    MERCENARY(Collections.<DefaultUnitType>emptyList(), MoveType.FOOT),
    WYVERN_KNIGHT(Arrays.asList(DefaultUnitType.FLYER, DefaultUnitType.DRAGON), MoveType.FLYER);
    
    private final List<DefaultUnitType> types;
    private final MoveCostStrategy moveCostStrategy;
    
    DefaultUnitClass(List<DefaultUnitType> types, MoveCostStrategy moveCostStrategy) {
        this.types = types;
        this.moveCostStrategy = moveCostStrategy;
    }
    
    @Override
    public List<DefaultUnitType> getUnitTypes() {
        return types;
    }
    
    @Override
    public MoveCostStrategy getMoveCostStrategy() {
        return moveCostStrategy;
    }
    
}
