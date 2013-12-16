/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.List;

/**
 * Represents the class or profession of a unit in FEP.
 * @author Kyle Buzsaki
 */
public interface UnitClass {
    
    /*
     * Future functionality:
     *  - name
     *  - weaknesses in battle
     *  - max stats
     *  - promotion gains, if any? or defined by promo item?
     *  - default weapon experience     (does not have to be used at unit creation)
     *  - default stats (base + growth) (does not have to be used at unit creation)
     */
    
    List<DefaultUnitType> getUnitTypes();
    
    MoveCostStrategy getMoveCostStrategy();
    
}
