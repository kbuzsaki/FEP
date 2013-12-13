/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * Represents a weapon item in FEP.
 * @author Kyle
 */
public class Weapon {
    // private constructor to prevent initialization until properly created
    private Weapon() {}
    
    /*
     * Might want to make into interface? (probably should with abstract weapon)
     *  - Def needs member vars -- uses etc     // not all items have uses! (myrrh claws)
     *  - Weapon Stats could be shared between weapons, no need for each to have
     * Methods:
     *  - Provide accessors for (each takes user and target):
     *      - damage / protection
     *      - hit / avoid
     *      - crit / crit avoid
     *      - attack speed / number attacks
     */
    
}
