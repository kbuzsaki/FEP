/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fep.model;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kyle
 */
public class ImmutablePointTest {
    
    public ImmutablePointTest() {
    }

    /**
     * Test of getX method, of class ImmutablePoint.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        ImmutablePoint instance = new ImmutablePoint(4, 1);
        int expResult = 4;
        int methodResult = instance.getX();
        
        assertEquals(expResult, methodResult, 0.0);
    }

    /**
     * Test of getY method, of class ImmutablePoint.
     */
    @Test
    public void testGetY() {
        ImmutablePoint instance = new ImmutablePoint(4, 1);
        int expResult = 1;
        int methodResult = instance.getY();
        
        assertEquals(expResult, methodResult, 0.0);
    }
    
    /**
     * Test of distance method, of class ImmutablePoint
     */
    @Test
    public void testDistance() {
        ImmutablePoint instance = new ImmutablePoint(0, 0);
        ImmutablePoint rootTwoAway = new ImmutablePoint(1, 1);
        
        assertEquals(instance.distance(rootTwoAway), Math.sqrt(2), 0.0);
    }
    
    /**
     * Test of distanceGrid method, of class ImmutablePoint
     */
    @Test
    public void testDistanceGrid() {
        ImmutablePoint instance = new ImmutablePoint(0, 0);
        ImmutablePoint twoAway = new ImmutablePoint(1, 1);
        ImmutablePoint nineAway = new ImmutablePoint(2, 7);
        
        assertEquals(instance.distanceGrid(twoAway), 2);
        assertEquals(instance.distanceGrid(nineAway), 9);
    }

    /**
     * Test of toPoint method, of class ImmutablePoint.
     */
    @Test
    public void testToPoint() {
        System.out.println("toPoint");
        Point p = new Point(0, 1);
        ImmutablePoint instance1 = new ImmutablePoint(p);
        ImmutablePoint instance2 = new ImmutablePoint(0, 1);
        ImmutablePoint instance3 = new ImmutablePoint(1, 1);
        
        assertEquals(p, instance1.toPoint());
        assertEquals(p, instance2.toPoint());
        assertTrue(!p.equals(instance3.toPoint()));
        p.x = 1;
        assertTrue(!p.equals(instance1.toPoint()));
    }

    /**
     * Test of isAdjacentTo method, of class ImmutablePoint.
     */
    @Test
    public void testIsAdjacentTo() {
        System.out.println("isAdjacentTo");
        ImmutablePoint instance = new ImmutablePoint(0, 0);
        ImmutablePoint adjacent = new ImmutablePoint(0, 1);
        ImmutablePoint notAdjacent = new ImmutablePoint(1, 1);
        
        assertTrue(instance.isAdjacentTo(adjacent));
        assertFalse(instance.isAdjacentTo(notAdjacent));
        assertFalse(instance.isAdjacentTo(instance));
    }

    /**
     * Test of getTranslated method, of class ImmutablePoint.
     */
    @Test
    public void testGetTranslated() {
        System.out.println("getTranslated");
        ImmutablePoint instance = new ImmutablePoint(0,0);
        ImmutablePoint up2 =    new ImmutablePoint(0, 2);
        ImmutablePoint down3 =  new ImmutablePoint(0, -3);
        ImmutablePoint left1 =  new ImmutablePoint(-1, 0);
        ImmutablePoint rightn5 = new ImmutablePoint(-5, 0);
        
        assertEquals(instance.getTranslated(Direction.UP, 2), up2);
        assertEquals(instance.getTranslated(Direction.DOWN, 3), down3);
        assertEquals(instance.getTranslated(Direction.LEFT, 1), left1);
        assertEquals(instance.getTranslated(Direction.RIGHT, -5), rightn5);
        
        for(Direction direction : Direction.values()) {
            assertEquals(instance.getTranslated(direction), instance.getTranslated(direction, 1));
        }
        
        try {
            instance.getTranslated(null);
            fail("Successfully translated in a null direction.");
        } catch(NullPointerException ex) {}
    }
    
    /**
     * Test of getAdjacentPoints method, of class ImmutablePoint.
     */
    @Test
    public void testGetAdjacentPoints() {
        System.out.println("getAdjacentPoints");
        ImmutablePoint instance = new ImmutablePoint(0, 0);
        Set<ImmutablePoint> expResult = new HashSet<>(Arrays.asList(
                new ImmutablePoint(1, 0),
                new ImmutablePoint(0, 1),
                new ImmutablePoint(-1, 0),
                new ImmutablePoint(0, -1)));
        Set<ImmutablePoint> result = instance.getAdjacentPoints();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of the equals method, of class ImmutablePoint.
     */
    @Test
    public void testEquals() {
        ImmutablePoint p1 = new ImmutablePoint(1, 1);
        ImmutablePoint p2 = new ImmutablePoint(1, 1);
        ImmutablePoint p3 = new ImmutablePoint(1, 2);
        Object o = new Object();
        
        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(o));
    }
    
}