/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.Set;

/**
 * Represents the class or profession of a unit in FEP. 
 * @author Kyle Buzsaki
 */
public interface UnitClass {
    
    /*
     * Future functionality:
     *  - weaknesses in battle
     *  - max stats
     *  - promotion gains, if any? or defined by promo item? probably promo item
     *  - default weapon experience     (does not have to be used at unit creation)
     *  - default stats (base + growth) (does not have to be used at unit creation)
     */
    
    /**
     * Returns the in-game name of this UnitClass.
     * @return  this UnitClass's name.
     */
    String getName();
    
    /**
     * Returns a set of the UnitTypes associated with this UnitClass. 
     * The set of UnitTypes is unmodifiable and should not be changed at runtime.
     * @return  this UnitClass's UnitTypes.
     */
    Set<UnitType> getUnitTypes();
    
    /**
     * Returns the formula for calculating movement cost used by this UnitClass.
     * @return  this UnitClass's MoveCostStrategy.
     */
    MoveCostStrategy getMoveCostStrategy();
    
}
