/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import fep.model.event.MapChangeListener;
import fep.model.event.TerrainChangedEvent;
import fep.model.event.UnitAddedEvent;
import fep.model.event.UnitMovedEvent;
import fep.model.event.UnitRemovedEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.EventListenerList;

/**
 * Represents a game map.
 * A game map is a 2-dimensional grid of fixed height and width. Every square
 * contained within the map is guaranteed to have a non-null piece of terrain.
 * Squares may or may not contain units. Units are guaranteed to only appear
 * once in the map.
 * @author Kyle Buzsaki
 */
public class Map {
    
    /**
     * The width of the map in tiles.
     */
    private final int width;
    /**
     * The height of the map in tiles.
     */
    private final int height;
    /**
     * Positionally organized array of the units on this map.
     * The dimensions of this 2D array should match width and height, respectively.
     */
    private final Unit[][] unitIndex;
    /**
     * Positionally organized array of the terrain making up this map.
     * The dimensions of this 2D array should match width and height, respectively.
     */
    private final Terrain[][] terrainIndex;
    /**
     * EventListenerList of all of this map's EventListeners. 
     * Contains {@link fep.model.event.MapChangeListener} objects that this map
     * will notify when changes are made to its terrain or units.
     */
    private final EventListenerList listeners;
    
    public Map(final int width, final int height) {
        if(width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Map height and width must be"
                    + " greater than 0");
        }
        this.width = width;
        this.height = height;
        
        unitIndex = new Unit[width][height];
        terrainIndex = new Terrain[width][height];
        
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                terrainIndex[x][y] = DefaultTerrain.PLAINS;
            }
        }
        
        this.listeners = new EventListenerList();
    }
    
    /**
     * Returns the width of the map in tiles.
     * @return the width of the map in tiles
     */
    public final int getWidth() {
        return width;
    }
    /**
     * Returns the height of the map in tiles.
     * @return the height of the map in tiles
     */
    public final int getHeight() {
        return height;
    }

    // Positional Accessors
    /**
     * Returns whether or not a unit is present at the given position.
     * @param position the position to check for units. Must be non-null and
     * within the map's dimensions (exclusive).
     * @return true if a unit is present at the position
     */
    public boolean unitAt(ImmutablePoint position) {
        return getUnitAt(position) != null;
    }
    /**
     * Returns the unit at the given position. Returns null if no unit is 
     * present.
     * @param position the position to check. Must be non-null and within 
     * the map's dimensions (exclusive).
     * @return the unit at the given position. May be null.
     */
    public Unit getUnitAt(ImmutablePoint position) {
        boundsCheck(position);
        return unitIndex[position.getX()][position.getY()];
    }
    /**
     * Returns a collection of all of the units on the map. 
     * Guaranteed to be non-null and have no null elements.
     * @return a collection of all of the units on the map.
     */
    public Set<Unit> getUnits() {
        Set<Unit> units = new HashSet<>();
        
        for(Unit[] column : unitIndex) {
            for(Unit unit : column) {
                if(unit != null) {
                    units.add(unit);
                }
            }
        }
        
        return units;
    }
    /**
     * Returns the terrain at the given position. Guaranteed to be non-null.
     * @param position the position to check. Must be non-null and within 
     * the map's dimensions (exclusive).
     * @return the terrain at the given position.
     */
    public Terrain getTerrainAt(ImmutablePoint position) {
        boundsCheck(position);
        return terrainIndex[position.getX()][position.getY()];
    }
    /**
     * Returns a tile at the given position. Guaranteed to be non-null, but
     * may have a null unit.
     * @param position the position to check. Must be non-null and within 
     * the map's dimensions (exclusive).
     * @return a tile at the given position.
     */
    public Tile getTileAt(ImmutablePoint position) {
        boundsCheck(position);
        return new Tile(this, position);
    }
    
    // Specific Unit Accessors
    /**
     * Returns whether or not the given unit is present in the map.
     * In the case of a null unit, always returns false.
     * @param unit the unit to check for. May be null.
     * @return true if the unit is present in the map.
     */
    public boolean containsUnit(Unit unit) {
        if(unit == null) {
            return false;
        }
        else {
            return getUnits().contains(unit);
        }
    }
    /**
     * Returns the position of the given unit on the map. 
     * Guaranteed to be non-null.
     * @param unit the unit to check the position of. Must be non-null.
     * @return the position of the given unit on the map.
     * @throws UnitNotFoundException if the unit is not present in the map.
     */
    public ImmutablePoint getPositionOf(Unit unit) {
        nullCheck(unit);
        
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                ImmutablePoint position = new ImmutablePoint(x, y);
                if(unit.equals(getUnitAt(position))) {
                    return position;
                }
            }
        }
        
        throw new UnitNotFoundException(unit, "Attempting to get the position of a unit not in the map.");
    }
    
    // Unit and Terrain Mutators
    /**
     * Adds the given unit to the map at the given position. There must not be
     * a unit already occupying the given position.
     * @param position the position to add the unit to. Must be non-null 
     * and within the map's dimensions (exclusive).
     * @param unit the unit to add to the map. Must be non-null.
     * @throws IllegalArgumentException if there is a unit already present at 
     * the specified position.
     */
    public void addUnit(ImmutablePoint position, Unit unit) {
        boundsCheck(position);
        nullCheck(unit);
        if(unitAt(position)) {
            throw new IllegalArgumentException("Attempting to add a unit to an "
                    + "occupied position: Position: " + position + " Unit: " + unit);
        }
        
        setUnitAt(position, unit);
        unit.setMap(this);
        
        for(MapChangeListener mapChangeListener : getMapChangeListeners()) {
            mapChangeListener.handleUnitAdded(new UnitAddedEvent(this, unit));
        }
    }
    /**
     * Removes the given unit from the map. The unit must be present in the map.
     * @param unit the unit to remove from the map. Must be non-null and
     * present in the map.
     * @throws UnitNotFoundException if the unit is not present in the map.
     */
    public void removeUnit(Unit unit) {
        nullCheck(unit);
        if(!containsUnit(unit)) {
            throw new UnitNotFoundException(unit, "Attempting to remove a unit not in the map.");
        }
        
        ImmutablePoint position = getPositionOf(unit);
        setUnitAt(position, null);
        unit.clearMap();
        
        for(MapChangeListener mapChangeListener : getMapChangeListeners()) {
            mapChangeListener.handleUnitRemoved(new UnitRemovedEvent(this, unit));
        }
    }
    /**
     * Moves the given unit to the given position on the map. The unit must be
     * present in the map and there must not be a unit already occupying the 
     * given position. 
     * @param position the position to move the unit to. Must be non-null and
     * within the map's dimensions (exclusive).
     * @param unit the unit to move. Must be non-null and present in the map.
     * @throws IllegalArgumentException if there is a unit already present at
     * the specified position
     * @throws UnitNotFoundException if the unit is not present in the map.
     */
    void moveUnitTo(ImmutablePoint position, Unit unit) {
        boundsCheck(position);
        nullCheck(unit);
        if(unitAt(position)) {
            throw new IllegalArgumentException("Attempting to move a unit to an"
                    + " occupied position: Position: " + position + ", Unit: " 
                    + unit + ", Blocking Unit: " + getUnitAt(position));
        }
        if(!containsUnit(unit)) {
            throw new UnitNotFoundException(unit, "Attempting to move a unit not in the map.");
        }
        
        ImmutablePoint priorPosition = getPositionOf(unit);
        setUnitAt(priorPosition, null);
        setUnitAt(position, unit);
        
        for(MapChangeListener mapChangeListener : getMapChangeListeners()) {
            mapChangeListener.handleUnitMoved(new UnitMovedEvent(this, unit, priorPosition, position));
        }
    }
    /**
     * Sets the terrain at the given point to be the given terrain. 
     * @param position the position to set terrain at. Must be non-null and 
     * within the map's dimensions (exclusive).
     * @param terrain the terrain to set at the given position. Must be non-null.
     */
    public void setTerrainAt(ImmutablePoint position, Terrain terrain) {
        boundsCheck(position);
        if(terrain == null) {
            throw new IllegalArgumentException("Terrain must be non-null");
        }
        
        Terrain priorTerrain = getTerrainAt(position);
        terrainIndex[position.getX()][position.getY()] = terrain;
        
        for(MapChangeListener mapChangeListener : getMapChangeListeners()) {
            mapChangeListener.handleTerrainChanged(new TerrainChangedEvent(this, position, priorTerrain, terrain));
        }
    }
    
    // Listener Accessors and Mutators
    /**
     * Registers the given listener to receive events when the map is altered.
     * @param listener the listener to register.
     */
    public void addMapChangeListener(MapChangeListener listener) {
        listeners.add(MapChangeListener.class, listener);
    }
    /**
     * Unregisters the given listener from receiving events when the map is altered.
     * @param listener the listener to unregister.
     */
    public void removeMapChangeListener(MapChangeListener listener) {
        listeners.remove(MapChangeListener.class, listener);
    }
    /**
     * Returns an array of this Map's MapChangeListeners. 
     * @return  this map's MapChangeListeners.
     */
    private MapChangeListener[] getMapChangeListeners() {
        return listeners.getListeners(MapChangeListener.class);
    }
    
    private void setUnitAt(ImmutablePoint position, Unit unit) {
        boundsCheck(position);
        if(containsUnit(unit)) {
            throw new IllegalArgumentException("Attempting to set a unit at multiple positions.");
        }
        unitIndex[position.getX()][position.getY()] = unit;
    }
    private void boundsCheck(ImmutablePoint position) {
        if(position == null) {
            throw new IllegalArgumentException("Position must be non-null");
        }
        if(!position.inBounds(0, 0, getWidth(), getHeight())) {
            throw new IllegalArgumentException("Position out of bounds: "
                    + "Position: " + position + ", Width: " + getWidth() 
                    + ", Height: " + getHeight());
        }
    }
    private void nullCheck(Unit unit) {
        if(unit == null) {
            throw new IllegalArgumentException("Unit must be non-null");
        }
    }
}
