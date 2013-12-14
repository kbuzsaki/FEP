/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.controller;

import fep.model.DefaultTerrain;
import fep.model.Direction;
import fep.model.ImmutablePoint;
import fep.model.MoveCostStrategy;
import fep.model.MoveType;
import fep.model.Path;
import fep.model.TerrainBehavior;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Kyle
 */
public class Cursor {
    // NOTE: possibly extract some interface? "CommandHandler" or "CommandStrategy"
    private enum CursorState {
        MOVE,
        SELECT,
        TARGET;
    }
    
    private static final int MAX_COST = 7;
    
    private ImmutablePoint position;
    private Path currentPath;
    private CursorState state;
    private MoveCostStrategy moveCostStrategy = MoveType.FOOT;
    
    public Cursor() {
        position = new ImmutablePoint(0, 0);
        currentPath = Path.emptyPath();
        state = CursorState.MOVE;
    }
    
    public void handleCommand(Command command) {
        switch(command) {
            case A:
                switch(state) {
                    case MOVE:
                        state = CursorState.SELECT;
                        currentPath = Path.createPath(Arrays.asList(position));
                        break;
                    case SELECT:
                        state = CursorState.MOVE;
                        for(ImmutablePoint point : currentPath) {
                            System.out.print(point + ", ");
                        }
                        System.out.println();
                        currentPath = Path.emptyPath();
                        break;
                }
                break;
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                switch(state) {
                    case MOVE:
                        move(command.toDirection());
                        System.out.println("Position: " + position);
                        break;
                    case SELECT:
                        move(command.toDirection());
                        if(currentPath.getEnd().isAdjacentTo(position)) {
                            Path tempPath = currentPath.concat(position);
                            if(moveCostStrategy.getCostOf(getTerrainPath(tempPath)) <= MAX_COST) {
                                currentPath = tempPath;
                                System.out.println("Position: " + position + " Path: " + currentPath );
                            }
                            else {
                                System.out.println("Path is not pathable. (Too long or obstructed)");
                            }
                        }
                }
        }
    }
    
    private void move(Direction direction) {
        position = position.getTranslated(direction);
    }
    
    public static List<TerrainBehavior> getTerrainPath(Path path) {
        List<TerrainBehavior> terrainPath = new ArrayList<>();
        
        for(ImmutablePoint point : path) {
            // get the terrain at this point
            terrainPath.add(DefaultTerrain.PLAINS.getTerrainBehavior());
        }
        
        return terrainPath;
    }
    
}
