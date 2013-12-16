/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fep.model;

import fep.model.ImmutablePoint;
import fep.model.Path;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the Game.Path class.
 * @author Kyle Buzsaki
 */
public class PathTest {
    
    public PathTest() {
    }

    /**
     * Test of the createPath static factory method, of class Path.
     */
    @Test
    public void testCreatePath() {
        System.out.println("createPath");
        ImmutablePoint[] pointsArray = {new ImmutablePoint(1, 1), new ImmutablePoint(2, 1), new ImmutablePoint(3, 1)};
        List<ImmutablePoint> pointsList = Arrays.asList(pointsArray);
        Path path = Path.createPath(pointsList);
        
        Path pathFromArray = Path.createPath(pointsArray);
        assertEquals(path, pathFromArray);
        
        assertTrue(path.size() == pointsList.size());
        for(int i = 0; i < path.size(); i++) {
            assertTrue(path.get(i).equals(pointsList.get(i)));
        }
        try {
            List<ImmutablePoint> nullPath = null;
            path = Path.createPath(nullPath);
            fail("Created a path with a null point list.");
        } catch (IllegalArgumentException ex) {}
        try {
            ImmutablePoint[] nullPointArray = null;
            path = Path.createPath(nullPointArray);
            fail("Created a path with a null point list.");
        } catch (IllegalArgumentException ex) {}
        try {
            List<ImmutablePoint> listWithNulls = Arrays.asList(new ImmutablePoint(1, 1), null, new ImmutablePoint(1, 3));
            path = Path.createPath(listWithNulls);
            fail("Created a path with a null point list.");
        } catch (IllegalArgumentException ex) {}
        try {
            List<ImmutablePoint> unsequencedList = Arrays.asList(new ImmutablePoint(1, 1), new ImmutablePoint(5, 6), new ImmutablePoint(5,5));
            path = Path.createPath(unsequencedList);
            fail("Created a path with a null point list.");
        } catch (IllegalArgumentException ex) {}
    }
    
    /**
     * Test of concat method, of class Path.
     */
    @Test
    public void testConcat() {
        System.out.println("concat");
        ImmutablePoint p0 = new ImmutablePoint(1, 2);
        ImmutablePoint p1 = new ImmutablePoint(1, 3);
        ImmutablePoint p2 = new ImmutablePoint(1, 4);
        ImmutablePoint invalid = new ImmutablePoint(7, 7);
        Path path = Path.emptyPath();
        
        try {
            path.concat(null);
            fail("Concatenated a null point.");
        }  catch (IllegalArgumentException ex) {}
        path = path.concat(p0);
        assertTrue(path.contains(p0) && path.getEnd().equals(p0) && path.size() == 1);
        path = path.concat(p1);
        assertTrue(path.contains(p1) && path.getEnd().equals(p1) && path.size() == 2);
        path = path.concat(p2);
        assertTrue(path.contains(p2) && path.getEnd().equals(p2) && path.size() == 3);
        try {
            path = path.concat(invalid);
            fail("Concatenated a point that was not connected to the tip.");
        }  catch (IllegalArgumentException ex) {}
        path = path.concat(p1);
        assertTrue(!path.contains(p2) && path.getEnd().equals(p1) && path.size() == 2);
        assertEquals(path, path.concat(path.getEnd()));
    }

    /**
     * Test of get method, of class Path.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        List<ImmutablePoint> points = Arrays.asList(new ImmutablePoint(1, 1), new ImmutablePoint(2, 1), new ImmutablePoint(3, 1));
        Path path = Path.emptyPath();
        
        try {
            path.get(0);
            fail("Got a point from an empty path.");
        } catch (IndexOutOfBoundsException ex) {}
        path = Path.createPath(points);
        for(int i = 0; i < path.size(); i++) {
            assertTrue(path.get(i).equals(points.get(i)));
        }
        try {
            path.get(-1);
            fail("Got a point from a negative index.");
        } catch (IndexOutOfBoundsException ex) {}
        try {
            path.get(3);
            fail("Got a point from an index >= the size.");
        } catch (IndexOutOfBoundsException ex) {}
    }

    /**
     * Test of getEnd method, of class Path.
     */
    @Test
    public void testGetEnd() {
        System.out.println("getTip");
        ImmutablePoint p0 = new ImmutablePoint(1, 2);
        ImmutablePoint p1 = new ImmutablePoint(1, 3);
        Path path = Path.emptyPath();
        
        try {
            path.getEnd();
            fail("Got the tip of an empty path.");
        } catch (NoSuchElementException ex) {}
        path = path.concat(p0);
        assertTrue(path.getEnd().equals(p0) && path.getEnd() != p1);
        path = path.concat(p1);
        assertTrue(path.getEnd().equals(p1) && path.getEnd() != p0);
    }

    /**
     * Test of contains method, of class Path.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        ImmutablePoint p0 = new ImmutablePoint(1, 2);
        ImmutablePoint p1 = new ImmutablePoint(0, 2);
        Path path = Path.emptyPath();
        
        assertTrue(!path.contains(p0) && !path.contains(p1));
        path = path.concat(p0);
        assertTrue(path.contains(p0) && !path.contains(p1));
        path = path.concat(p1);
        assertTrue(path.contains(p0) && path.contains(p1));
    }

    /**
     * Test of size method, of class Path.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        ImmutablePoint p0 = new ImmutablePoint(0, 0);
        ImmutablePoint p1 = new ImmutablePoint(1, 0);
        Path path = Path.emptyPath();
        
        assertTrue(path.size() == 0);
        path = path.concat(p0);
        assertTrue(path.size() == 1);
        path = path.concat(p1);
        assertTrue(path.size() == 2);
    }

    /**
     * Test of isEmpty method, of class Path.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        ImmutablePoint p0 = new ImmutablePoint(0,0);
        Path path = Path.emptyPath();
        
        assertTrue(path.isEmpty());
        path = path.concat(p0);
        assertFalse(path.isEmpty());
    }

    /**
     * Test of iterator method, of class Path.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        ImmutablePoint[] points = { new ImmutablePoint(0,0), new ImmutablePoint(0,1), new ImmutablePoint(1,1) };
        Path path = Path.emptyPath();
        for(ImmutablePoint p : points) {
            path = path.concat(p);
        }
        assertEquals(points.length, path.size());
        
        // foreach loop tests the iterator
        int i = 0;
        for(ImmutablePoint p : path) {
            assertEquals(points[i], p);
            i++;
        }
        assertEquals(i, path.size());
    }

    /**
     * Test of toList method, of class Path.
     */
    @Test
    public void testToList() {
        System.out.println("toList");
        List<ImmutablePoint> points = Arrays.asList(new ImmutablePoint(0,0), new ImmutablePoint(0, 1), new ImmutablePoint(1,1));
        Path path = Path.createPath(points);
        
        List<ImmutablePoint> listForm = path.toList();
        assertEquals(path.size(), listForm.size());
        for(int i = 0; i < path.size(); i++) {
            assertEquals(path.get(i), listForm.get(i));
        }
        assertTrue(listForm != path.toList()); // ensures defensive copying
    }
    
}