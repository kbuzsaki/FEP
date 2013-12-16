/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fep.model;

import fep.model.event.MapChangeListener;
import fep.model.event.TerrainChangedEvent;
import fep.model.event.UnitAddedEvent;
import fep.model.event.UnitMovedEvent;
import fep.model.event.UnitRemovedEvent;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kyle Buzsaki
 */
public class MapTest {
    
    public MapTest() {
    }

    private ImmutablePoint[] getOutOfBoundsPoints(Map map) {
        ImmutablePoint[] oobPoints = {
            new ImmutablePoint(map.getWidth(), 0),
            new ImmutablePoint(0, map.getHeight()),
            new ImmutablePoint(-1, 0),
            new ImmutablePoint(0, -1),
        };
        return oobPoints;
    }
    private Unit createTestUnit() {
        return new Unit();
    }
    
    /**
     * Test of the constructor, of class Map
     */
    @Test
    public void testMapConstructor() {
        Map map1 = new Map(10, 15);
        assertEquals(10, map1.getWidth());
        assertEquals(15, map1.getHeight());
        
        int[][] invalidDimensions = {
            {10, 0},
            {0,  10},
            {0,  0},
            {10, -5},
            {-3, 10},
            {-2, -8}
        };
        
        for(int[] dimensionSet : invalidDimensions) {
            int width = dimensionSet[0];
            int height = dimensionSet[1];
            try {
                Map invalidMap = new Map(width, height);
                fail("Successfully created a map with invalid dimensions: "
                        + "width: " + width + ", height: " + height);
            } catch(IllegalArgumentException ex) {}
        }
    }
    
    /**
     * Test of getWidth method, of class Map.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        Map map = new Map(10, 15);
        int expResult = 10;
        int result = map.getWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHeight method, of class Map.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        Map map = new Map(10, 15);
        int expResult = 15;
        int result = map.getHeight();
        assertEquals(expResult, result);
    }

    /**
     * Test of unitAt method, of class Map.
     */
    @Test
    public void testUnitAt() {
        System.out.println("unitAt");
        Map map = new Map(10, 10);
        Unit unit = createTestUnit();
        ImmutablePoint p1 = new ImmutablePoint(2, 2);
        ImmutablePoint p2 = new ImmutablePoint(0,0);
        ImmutablePoint p3 = new ImmutablePoint(9,9);
        
        map.addUnit(p1, unit);
        
        assertTrue(map.unitAt(p1));
        assertFalse(map.unitAt(p2));
        assertFalse(map.unitAt(p3));
        
        try {
            map.unitAt(null);
            fail("Successfully checked a null position.");
        } catch (IllegalArgumentException ex) {}
        
        ImmutablePoint[] oobPoints = getOutOfBoundsPoints(map);
        for (ImmutablePoint oobPoint : oobPoints) {
            try {
                map.unitAt(oobPoint);
                fail("Successfully checked an out of bounds position");
            } catch (IllegalArgumentException ex) {}
        }
    }

    /**
     * Test of getUnitAt method, of class Map.
     */
    @Test
    public void testGetUnitAt() {
        System.out.println("getUnitAt");
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,2);
        ImmutablePoint p2 = new ImmutablePoint(1,3);
        ImmutablePoint p3 = new ImmutablePoint(2,4);
        Unit unit1 = createTestUnit();
        Unit unit2 = createTestUnit();
        assertFalse(unit1.equals(unit2));
        
        assertNull(map.getUnitAt(p1));
        assertNull(map.getUnitAt(p2));
        assertNull(map.getUnitAt(p3));
        
        map.addUnit(p1, unit1);
        map.addUnit(p2, unit2);
        
        assertEquals(map.getUnitAt(p1), unit1);
        assertEquals(map.getUnitAt(p2), unit2);
        assertNull(map.getUnitAt(p3));
        
        try {
            map.getUnitAt(null);
            fail("Successfully checked a null position.");
        } catch (IllegalArgumentException ex) {}
        
        ImmutablePoint[] oobPoints = getOutOfBoundsPoints(map);
        for (ImmutablePoint oobPoint : oobPoints) {
            try {
                map.getUnitAt(oobPoint);
                fail("Successfully got a unit from an out of bounds position");
            } catch (IllegalArgumentException ex) {}
        }
    }

    /**
     * Test of getUnits method, of class Map.
     */
    @Test
    public void testGetUnits() {
        System.out.println("getUnits");
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,1);
        ImmutablePoint p2 = new ImmutablePoint(1,2);
        Unit unit1 = createTestUnit();
        Unit unit2 = createTestUnit();
        assertFalse(unit1.equals(unit2));
        
        assertTrue(map.getUnits().isEmpty());
        
        map.addUnit(p1, unit1);
        map.addUnit(p2, unit2);
        
        Set<Unit> units = map.getUnits();
        
        assertTrue(units.size() == 2);
        assertTrue(units.contains(unit1));
        assertTrue(units.contains(unit2));
    }

    /**
     * Test of getTerrainAt method, of class Map.
     */
    @Test
    public void testGetTerrainAt() {
        System.out.println("getTerrainAt");
        Map map = new Map(10,10);
        for(int x = 0; x < map.getWidth(); x++) {
            for(int y = 0; y < map.getHeight(); y++) {
                assertNotNull(map.getTerrainAt(new ImmutablePoint(x, y)));
            }
        }
        
        ImmutablePoint p1 = new ImmutablePoint(1, 2);
        map.setTerrainAt(p1, DefaultTerrain.PEAK);
        
        assertEquals(map.getTerrainAt(p1), DefaultTerrain.PEAK);
        
        try {
            map.getTerrainAt(null);
            fail("Successfully checked a null position.");
        } catch (IllegalArgumentException ex) {}
        
        ImmutablePoint[] oobPoints = getOutOfBoundsPoints(map);
        for (ImmutablePoint oobPoint : oobPoints) {
            try {
                map.getTerrainAt(oobPoint);
                fail("Successfully got terrain at an out of bounds position");
            } catch (IllegalArgumentException ex) {}
        }
    }

    /**
     * Test of getTileAt method, of class Map.
     */
    @Test
    public void testGetTileAt() {
        System.out.println("getTileAt");
        Map map = new Map(10, 10);
        ImmutablePoint p0 = new ImmutablePoint(1,1);
        ImmutablePoint p1 = new ImmutablePoint(1,2);
        ImmutablePoint p2 = new ImmutablePoint(1,3);
        Unit unit1 = createTestUnit();
        Unit unit2 = createTestUnit();
        Terrain terrain1 = DefaultTerrain.FOREST;
        Terrain terrain2 = DefaultTerrain.PEAK;
        
        Tile tile0 = map.getTileAt(p0);
        assertEquals(map.getUnitAt(p0), tile0.getUnit());
        assertEquals(map.getTerrainAt(p0), tile0.getTerrainAt());
        
        map.addUnit(p1, unit1);
        map.setTerrainAt(p1, terrain1);
        Tile tile1 = map.getTileAt(p1);
        assertEquals(map.getUnitAt(p1), tile1.getUnit());
        assertEquals(map.getTerrainAt(p1), tile1.getTerrainAt());
        
        map.addUnit(p2, unit2);
        map.setTerrainAt(p2, terrain2);
        Tile tile2 = map.getTileAt(p2);
        assertEquals(map.getUnitAt(p2), tile2.getUnit());
        assertEquals(map.getTerrainAt(p2), tile2.getTerrainAt());
        
        try {
            map.getTileAt(null);
            fail("Successfully checked a null position.");
        } catch (IllegalArgumentException ex) {}
        
        ImmutablePoint[] oobPoints = getOutOfBoundsPoints(map);
        for (ImmutablePoint oobPoint : oobPoints) {
            try {
                map.getTileAt(oobPoint);
                fail("Successfully got a tile from an out of bounds position");
            } catch (IllegalArgumentException ex) {}
        }
    }

    /**
     * Test of containsUnit method, of class Map.
     */
    @Test
    public void testContainsUnit() {
        System.out.println("containsUnit");
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,2);
        ImmutablePoint p2 = new ImmutablePoint(1,3);
        Unit unit1 = createTestUnit();
        Unit unit2 = createTestUnit();
        
        assertFalse(map.containsUnit(null));
        
        assertFalse(map.containsUnit(unit1));
        assertFalse(map.containsUnit(unit2));
        
        map.addUnit(p1, unit1);
        
        assertTrue(map.containsUnit(unit1));
        assertFalse(map.containsUnit(unit2));
        
        map.addUnit(p2, unit2);
        
        assertTrue(map.containsUnit(unit1));
        assertTrue(map.containsUnit(unit2));
    }

    /**
     * Test of getPositionOf method, of class Map.
     */
    @Test
    public void testGetPositionOf() {
        System.out.println("getPositionOf");
        Map map = new Map(10, 10);
        ImmutablePoint p1 = new ImmutablePoint(5,2);
        ImmutablePoint p2 = new ImmutablePoint(1,7);
        Unit unit1 = createTestUnit();
        Unit unit2 = createTestUnit();
        
        map.addUnit(p1, unit1);
        assertEquals(p1, map.getPositionOf(unit1));
        
        map.addUnit(p2, unit2);
        assertEquals(p2, map.getPositionOf(unit2));
        
        assertFalse(p1.equals(p2));
        assertFalse(p1.equals(map.getPositionOf(unit2)));
        assertFalse(p2.equals(map.getPositionOf(unit1)));
        assertFalse(map.getPositionOf(unit1).equals(map.getPositionOf(unit2)));
        
        Unit unit3 = createTestUnit();
        try {
            map.getPositionOf(null);
            fail("Successfully checked for a null unit");
        } catch (IllegalArgumentException ex) {}
        try {
            map.getPositionOf(unit3); 
            fail("Successfully checked for a unit not in the map.");
        } catch (UnitNotFoundException ex) {}
    }

    /**
     * Test of addUnit method, of class Map.
     */
    @Test
    public void testAddUnit() {
        System.out.println("addUnit");
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,2);
        ImmutablePoint p2 = new ImmutablePoint(1,3);
        ImmutablePoint p3 = new ImmutablePoint(4,5);
        Unit unit1 = createTestUnit();
        Unit unit2 = createTestUnit();
        
        map.addUnit(p1, unit1);
        map.addUnit(p2, unit2);
        
        assertTrue(map.containsUnit(unit1));
        assertTrue(map.containsUnit(unit2));
        
        try {
            map.addUnit(p1, unit1);
            fail("Successfully added the same unit to the same position twice.");
        } catch (IllegalArgumentException ex) {}
        try {
            map.addUnit(p1, unit2);
            fail("Successfully added two units to the same point");
        } catch (IllegalArgumentException ex) {}
        try {
            map.addUnit(p3, unit1);
            fail("Successfully added the same unit to the map twice.");
        } catch (IllegalArgumentException ex) {}
        
        Unit unit3 = createTestUnit();
        ImmutablePoint p4 = new ImmutablePoint(7,8);
        try {
            map.addUnit(null, unit3);
            fail("Successfully added a unit to a null position.");
        } catch (IllegalArgumentException ex) {}
        try {
            map.addUnit(p4, null);
            fail("Successfully added a null unit to the map.");
        } catch (IllegalArgumentException ex) {}
        try {
            map.addUnit(null, null);
            fail("Successfully added a null unit to a null position.");
        } catch (IllegalArgumentException ex) {}
        
        ImmutablePoint[] oobPoints = getOutOfBoundsPoints(map);
        for (ImmutablePoint oobPoint : oobPoints) {
            try {
                map.addUnit(oobPoint, unit3);
                fail("Successfully added a unit to an out of bounds position");
            } catch (IllegalArgumentException ex) {}
        }
        
        // making sure that nothing is left behind from failed add attempts
        assertFalse(map.containsUnit(unit3));
        map.addUnit(p4, unit3);
        assertTrue(map.containsUnit(unit3));
    }

    /**
     * Test of removeUnit method, of class Map.
     */
    @Test
    public void testRemoveUnit() {
        System.out.println("removeUnit");
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,2);
        Unit unit1 = createTestUnit();
        
        try {
            map.removeUnit(unit1);
            fail("Successfully removed a unit not in the map.");
        } catch(UnitNotFoundException ex) {}
        
        map.addUnit(p1, unit1);
        
        assertTrue(map.containsUnit(unit1));
        
        map.removeUnit(unit1);
        
        assertFalse(map.containsUnit(unit1));
        
        try {
            map.removeUnit(null);
            fail("Successfully removed a null unit.");
        } catch(IllegalArgumentException ex) {}
    }

    /**
     * Test of moveUnitTo method, of class Map.
     */
    @Test
    public void testMoveUnitTo() {
        System.out.println("moveUnitTo");
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,2);
        ImmutablePoint p2 = new ImmutablePoint(1,3);
        Unit unit1 = createTestUnit();
        Unit unit2 = createTestUnit();
        
        map.addUnit(p1, unit1);
        
        assertEquals(p1, map.getPositionOf(unit1));
        
        map.moveUnitTo(p2, unit1);
        
        assertEquals(p2, map.getPositionOf(unit1));
        
        map.addUnit(p1, unit2);
        try {
            map.moveUnitTo(p1, unit1);
            fail("Successfully moved a unit to an occupied space.");
        } catch (IllegalArgumentException ex) {}
        
        Unit unit3 = createTestUnit();
        ImmutablePoint p3 = new ImmutablePoint(1, 5);
        try {
            map.moveUnitTo(p3, unit3);
            fail("Successfully moved a unit not in the map.");
        } catch (UnitNotFoundException ex) {}
        
        try {
            map.moveUnitTo(p3, null);
            fail("Successfully moved a null unit.");
        } catch (IllegalArgumentException ex) {}
        
        try {
            map.moveUnitTo(null, unit3);
            fail("Successfully moved a unit to a null position.");
        } catch (IllegalArgumentException ex) {}
        
        ImmutablePoint[] oobPoints = getOutOfBoundsPoints(map);
        for (ImmutablePoint oobPoint : oobPoints) {
            try {
                map.moveUnitTo(oobPoint, unit3);
                fail("Successfully moved a unit to an out of bounds position");
            } catch (IllegalArgumentException ex) {}
        }
    }

    /**
     * Test of setTerrainAt method, of class Map.
     */
    @Test
    public void testSetTerrainAt() {
        System.out.println("setTerrainAt");
        Map map = new Map(10, 10);
        ImmutablePoint p1 = new ImmutablePoint(1,2);
        Terrain t1 = DefaultTerrain.PLAINS;
        Terrain t2 = DefaultTerrain.FOREST;
        
        map.setTerrainAt(p1, t1);
        assertEquals(t1, map.getTerrainAt(p1));
        map.setTerrainAt(p1, t2);
        assertEquals(t2, map.getTerrainAt(p1));
        
        try {
            map.setTerrainAt(p1, null);
            fail("Successfully set null terrain.");
        } catch(IllegalArgumentException ex) {}
        
        try {
            map.setTerrainAt(null, t1);
            fail("Successfully set terrain at a null position.");
        } catch(IllegalArgumentException ex) {}
        
        ImmutablePoint[] oobPoints = getOutOfBoundsPoints(map);
        for (ImmutablePoint oobPoint : oobPoints) {
            try {
                map.setTerrainAt(oobPoint, t1);
                fail("Successfully set terrain at an out of bounds position");
            } catch (IllegalArgumentException ex) {}
        }
    }
    
    /**
     * Test of addMapChangeListener method, of class Map.
     */
    @Test
    public void testAddMapChangeListener() {
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,1);
        Unit unit1 = createTestUnit();
        
        // is a final array so that the anonymous inner class can modify it
        final boolean[] hasSucceeded = new boolean[1];
        hasSucceeded[0] = false;
        MapChangeListener mapChangeListener = new MapChangeListener() {
            @Override
            public void handleUnitAdded(UnitAddedEvent event) {
                hasSucceeded[0] = true;
            }

            @Override
            public void handleUnitRemoved(UnitRemovedEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void handleUnitMoved(UnitMovedEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void handleTerrainChanged(TerrainChangedEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        // map change listener sets hasSucceeded[0] to true upon receiving an event
        map.addMapChangeListener(mapChangeListener);
        map.addUnit(p1, unit1);
        
        assertTrue(hasSucceeded[0]);
    }
    
    /**
     * Test of removeMapChangeListener method, of class Map.
     */
    @Test
    public void testRemoveMapChangeListener() {
        Map map = new Map(10,10);
        ImmutablePoint p1 = new ImmutablePoint(1,1);
        Unit unit1 = createTestUnit();
        
        // is a final array so that the anonymous inner class can modify it
        final boolean[] hasSucceeded = new boolean[1];
        hasSucceeded[0] = false;
        MapChangeListener mapChangeListener = new MapChangeListener() {
            @Override
            public void handleUnitAdded(UnitAddedEvent event) {
                hasSucceeded[0] = true;
            }

            @Override
            public void handleUnitRemoved(UnitRemovedEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void handleUnitMoved(UnitMovedEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void handleTerrainChanged(TerrainChangedEvent event) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        // map change listener sets hasSucceeded[0] to true upon receiving an event
        map.addMapChangeListener(mapChangeListener);
        // if the change listener is successfully removed, hasSucceeded[0] will still be false
        map.removeMapChangeListener(mapChangeListener);
        map.addUnit(p1, unit1);
        
        assertFalse(hasSucceeded[0]);
    }
    
    /**
     * Test of the firing and accuracy of the UnitAddedEvent, UnitRemovedEvent,
     * and UnitMovedEvent fired by the class Map.
     */
    @Test
    public void testUnitEventFiring() {
        final Map map = new Map(10,10);
        final ImmutablePoint p1 = new ImmutablePoint(1,1);
        final ImmutablePoint p2 = new ImmutablePoint(1,2);
        final Unit unit1 = createTestUnit();
        
        MapChangeListener mapChangeListener = new MapChangeListener() {
            @Override
            public void handleUnitAdded(UnitAddedEvent event) {
                assertEquals(map, event.getMap());
                assertEquals(unit1, event.getUnitAdded());
            }

            @Override
            public void handleUnitRemoved(UnitRemovedEvent event) {
                assertEquals(map, event.getMap());
                assertEquals(unit1, event.getUnitRemoved());
            }

            @Override
            public void handleUnitMoved(UnitMovedEvent event) {
                assertEquals(map, event.getMap());
                assertEquals(unit1, event.getUnitMoved());
                assertEquals(p1, event.getPriorPosition());
                assertEquals(p2, event.getNewPosition());
            }

            @Override
            public void handleTerrainChanged(TerrainChangedEvent event) {
                fail("Should not fire a terrain change event.");
            }
        };
        
        map.addMapChangeListener(mapChangeListener);
        map.addUnit(p1, unit1);
        map.moveUnitTo(p2, unit1);
        map.removeUnit(unit1);
        
    }
    
    /**
     * Test of the firing and accuracy of the TerrainChangedEvent fired by the 
     * class Map.
     */
    @Test
    public void testTerrainChangedEventFiring() {
        final Map map = new Map(10, 10);
        final ImmutablePoint p1 = new ImmutablePoint(1, 1);
        final Terrain t1 = DefaultTerrain.PEAK;
        final Terrain t2 = DefaultTerrain.FOREST;
        map.setTerrainAt(p1, t1);
        
        MapChangeListener mapChangeListener = new MapChangeListener() {

            @Override
            public void handleUnitAdded(UnitAddedEvent event) {
                fail("Should not fire a unit added event");
            }

            @Override
            public void handleUnitRemoved(UnitRemovedEvent event) {
                fail("Should not fire a unit removed event");
            }

            @Override
            public void handleUnitMoved(UnitMovedEvent event) {
                fail("Should not fire a unit moved event");
            }

            @Override
            public void handleTerrainChanged(TerrainChangedEvent event) {
                assertEquals(map, event.getMap());
                assertEquals(p1, event.getPointChanged());
                assertEquals(t1, event.getPreviousTerrain());
                assertEquals(t2, event.getNewTerrain());
            }
            
        };
        
        map.addMapChangeListener(mapChangeListener);
        map.setTerrainAt(p1, t2);
        
    }
}