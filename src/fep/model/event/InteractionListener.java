/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model.event;

/**
 *
 * @author Kyle
 */
public interface InteractionListener {

    public void handleInteractionStartEvent();
    
    public void handleInteractionEndEvent();
    
}
