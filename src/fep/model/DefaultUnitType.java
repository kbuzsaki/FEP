/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

/**
 * An enumeration of the default {@link UnitType Unit Types} of FEP.
 * @author Kyle Buzsaki
 */
public enum DefaultUnitType implements UnitType {
    /**
     * Units weak to the Swordslayer. (see http://serenesforest.net/fe7/axe.html)
     * Such as Myrmidons, Mercenaries, and Blade Lords.
     */
    LIGHT_SWORDSMAN,
    /**
     * All units mounted on horseback.
     * Such as Cavaliers, Troubadors, and Knight Lords.
     */
    HORSE,
    /**
     * Units in heavy armor.
     * Such as Knights, Generals, and Great Lords.
     */
    ARMOR,
    /**
     * Units on flying mounts.
     * Such as Pegasus Knights, Wyvern Knights, and Princess Crimea.
     */
    FLYER,
    /**
     * Units that are or are mounted on dragons.
     * Such as Dragons, Dragon Laguz, and Wyvern Knights.
     */
    DRAGON;
}
