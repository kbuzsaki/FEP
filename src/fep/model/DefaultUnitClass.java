/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An enumeration of the default {@link UnitClass Unit Classes} in FEP. 
 * @author Kyle Buzsaki
 */
public enum DefaultUnitClass implements UnitClass {
    
    MERCENARY("Mercenary", Collections.<UnitType>emptyList(), MoveType.FOOT),
    WYVERN_KNIGHT("Wyvern Knight", Arrays.<UnitType>asList(DefaultUnitType.FLYER, DefaultUnitType.DRAGON), MoveType.FLYER);
    
    private final String name;
    private final Set<UnitType> types;
    private final MoveCostStrategy moveCostStrategy;
    
    DefaultUnitClass(String name, List<UnitType> types, MoveCostStrategy moveCostStrategy) {
        this.name = name;
        this.types = Collections.unmodifiableSet(new HashSet<>(types));
        this.moveCostStrategy = moveCostStrategy;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public Set<UnitType> getUnitTypes() {
        return types;
    }
    
    @Override
    public MoveCostStrategy getMoveCostStrategy() {
        return moveCostStrategy;
    }
    
    // JAVA8: should be default method in UnitClass
    @Override
    public String toString() {
        return getName();
    }
    
}
