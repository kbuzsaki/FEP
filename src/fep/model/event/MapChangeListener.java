/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model.event;

import java.util.EventListener;

/**
 *
 * @author Kyle Buzsaki
 */
public interface MapChangeListener extends EventListener {
    
    public void handleUnitAdded(UnitAddedEvent event);
    public void handleUnitRemoved(UnitRemovedEvent event);
    public void handleUnitMoved(UnitMovedEvent event);
    
    public void handleTerrainChanged(TerrainChangedEvent event);
    
}
