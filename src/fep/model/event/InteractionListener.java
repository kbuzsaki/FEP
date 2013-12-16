/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model.event;

/**
 *
 * @author Kyle Buzsaki
 */
public interface InteractionListener {

    public void handleInteractionStartEvent();
    
    public void handleInteractionEndEvent();
    
}
