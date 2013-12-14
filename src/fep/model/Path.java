/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A series of adjacent points representing the path of a game element.
 * Each point in the path is guaranteed to be adjacent to the points preceding
 * and following it in the path, as specified by ImmutablePoint.isAdjacentTo().
 * This class is immutable.
 * @author Kyle
 */
public final class Path implements Iterable<ImmutablePoint> { 
    private static final Path EMPTY_PATH = new Path(Collections.<ImmutablePoint>emptyList());
    
    /**
     * An ImmutableList of the ImmutablePoint objects making up this path.
     * Each point in the list is guaranteed to be adjacent to the points preceding
     * and following it in the list, as specified by ImmutablePoint.isAdjacentTo().
     */
    private final List<ImmutablePoint> points;
    
    /**
     * Constructs a path with the specified points. This constructor is private
     * and performs no validation. It should not be called except by the
     * {@link #createPathInternal(java.util.List)} factory method.<br>
     * New paths should be created with the {@link #createPath(java.util.List)} 
     * and {@link #createPathInternal(java.util.List)} factory methods instead.
     * @param points  the points for the new path.
     */
    private Path(List<ImmutablePoint> points) {
        this.points = points;
    }
    
    /**
     * Returns a path containing no points.
     * @return an empty path
     */
    public static Path emptyPath() {
        return EMPTY_PATH;
    }    
    
    /**
     * Creates a path with the given points as its starting values. The points 
     * must form a valid path. This means that the list of points may not 
     * contain nulls, and that each point must be adjacent to the points on 
     * either side of it.
     * This method is deprecated because varargs are a bit confusing. 
     * The preferred form is Path.createPath(Arrays.asList(array));
     * @param unsanitizedPointsArray  the points whose values will be used to create the path.
     * @return a path representing the specified points.
     */
    @Deprecated
    public static Path createPath(ImmutablePoint... unsanitizedPointsArray) {
        if(unsanitizedPointsArray == null)  {
            throw new IllegalArgumentException("Unsanitized points may not be null.");
        }
        return createPath(Arrays.asList(unsanitizedPointsArray));
    }
    /**
     * Creates a path with the given points as its starting values. The points 
     * must form a valid path. This means that the list of points may not 
     * contain nulls, and that each point must be adjacent to the points on 
     * either side of it.
     * @param unsanitizedPoints  the points whose values will be used to create the path.
     * @return a path representing the specified points.
     */
    public static Path createPath(List<ImmutablePoint> unsanitizedPoints) {
        if(unsanitizedPoints == null)  {
            throw new IllegalArgumentException("Unsanitized points may not be null.");
        }
        // shallow copy to prevent outside modification
        List<ImmutablePoint> unvalidatedPoints = new ArrayList<>(unsanitizedPoints);
        if(!isValidPath(unvalidatedPoints)) {
            throw new IllegalArgumentException("Invalid path supplied. "
                    + "Points must be sequential and non-null.");
        }
        else {
            return createPathInternal(unvalidatedPoints);
        }
    }
    /**
     * Creates a path with the specified points. Assumes that the list of points
     * is valid and has already been defensively copied.
     * @param validPoints  the points used to construct the path.
     * @return a path representing the specified points.
     */
    private static Path createPathInternal(List<ImmutablePoint> validPoints) {
        return new Path(Collections.unmodifiableList(validPoints));
    }
    
    // Positional Accessors
    /**
     * Returns the point at the specified index in the path. 
     * @param index  the index of the point to be returned. Must be greater than
     * or equal to 0 and less than the total size of the path.
     * @return the point at the given index in the path.
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (<tt>index &lt; 0 || index &gt;= size()</tt>)
     */
    public ImmutablePoint get(int index) {
        if(index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + " Path Size: " + size());
        }
        else {
            return points.get(index);
        }
    } 
    /**
     * Returns final point in the path. The end of the path is the point that 
     * was most recently added. 
     * @return the tip of the path.
     */
    public ImmutablePoint getEnd() {
        if(isEmpty()) {
            throw new NoSuchElementException("Attemping to get tip of empty path.");
        }
        return get(points.size() - 1);
    }
    
    // Metadata Accessors
    /**
     * Returns the number of points in the path.
     * @return the number of points in the path.
     */
    public int size() {
        return points.size();
    }
    /**
     * Returns <tt>true</tt> if the path contains no points. This is logically
     * equivalent to <code>size() == 0</code>
     * @return true if the path contains no points.
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    // Point-Specific Accessors and Mutators
    /**
     * Returns <tt>true</tt> if the path contains the specified point.
     * More formally, returns <tt>true</tt> if and only if this path contains
     * at least one Point <tt>p2</tt> such that
     * <tt>(p1==null&nbsp;?&nbsp;p2==null&nbsp;:&nbsp;p1.equals(p2))</tt>.
     * @param point  the point whose presence in the path is to be tested.
     * @return  <tt>true</tt> if this path contains the specified point.
     */
    public boolean contains(ImmutablePoint point) {
        return points.contains(point);
    }
    /**
     * Returns a path with this path's data and the supplied point appended.
     * The point must either be adjacent to the tip of the path or in the path 
     * already. If the point is adjacent, it will be appended. If the supplied
     * point is already in the path, the returned path will truncate at that
     * point instead.
     * @param point  the point to with to the path. Must be either adjacent to 
     * the tip of the path or in the path. Must be non-null.
     * @return  a new path instance representing the result of the concatenation.
     */
    public Path concat(ImmutablePoint point) {
        if(point == null) {
            throw new IllegalArgumentException("Added point must be non-null.");
        }
        // if the point is already in the path, simplify the path by removing all points following it
        if(contains(point)) {
            // logging code in case of weird behavior. 
            // Technically legal, but probably shouldn't happen.
            if(getEnd().equals(point)) {
                System.err.println("Warning: concating with point equal to end "
                        + "of path. (path: " + this + ")");
            }
            // index + 1 because sublist's range is [begin, end)
            return createPathInternal(points.subList(0, points.indexOf(point) + 1)); 
        }
        // the point can be added if it only if it is adjacent to the tip
        // if there is no tip, it can be added anyways
        else if(isEmpty() || point.isAdjacentTo(getEnd())) {
            return createPathInternal(catList(points, point)); 
        }
        else {
            throw new IllegalArgumentException("Added points must be in the"
                    + " path or adjacent to its end. (end: " + getEnd() 
                    + " point: " + point + ")");
        }
    }
    
    // Path specific overrides for built-in methods
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < size(); i++) {
            sb.append(get(i));
            if(i < (size() - 1)) {
                sb.append(", ");
            }
        }
        
        return sb.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Path) {
            Path path = (Path) obj;
            return points.equals(path.points);
        }
        else {
            return super.equals(obj); 
        }
    }
    @Override
    public int hashCode() {
        int hashCode = 1;
        for (ImmutablePoint point : this) {
            hashCode = 17*hashCode + point.hashCode();
        }
        return hashCode;
    }
    
    // Convenience methods for iteration.
    /**
     * Provides an ordered list of the points in the path. The points are 
     * ordered with the most recent points at the end of the list. The list
     * returned is a copy of the internal list.
     * @return and ordered list of the points in the path.
     */
    public List<ImmutablePoint> toList() {
        return new ArrayList<>(points);
    }
    
    /**
     * Returns an iterator for this path's points. The iterator does not permit
     * modifying operations.
     * @return  an iterator for this path's points.
     */
    @Override
    public Iterator<ImmutablePoint> iterator() {
        return new PathIterator();
    }
    
    /**
     * Disposable Iterator to iterate over the points in this Path.
     * Does not allow modifications to the Path.
     */ 
    private class PathIterator implements Iterator<ImmutablePoint> {
    private int cursor = 0;
    
    @Override
    public boolean hasNext() {
        return cursor != size();
    }
    
    @Override
    public ImmutablePoint next() {
        return get(cursor++);
    }
    
    /**
     * Unsupported Operation.
     * @throws UnsupportedOperationException because this operation is not supported.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("May not remove points from path.");
    }
}
    
    // Private Implementation Methods
    private static boolean isValidPath(List<ImmutablePoint> points) {
        // first ensure no null points
        for(ImmutablePoint p : points) {
            if(p == null) {
                return false;
            }
        }
        // then ensure all points are adjacent
        for(int i = 0; i < points.size() - 1; i++) {
            if(!points.get(i).isAdjacentTo(points.get(i + 1))) {
                return false;
            }
        }
        return true;
    }
    private static <E> List<E> catList(List<E> list, E el) {
        List<E> catList = new ArrayList<>(list);
        catList.add(el);
        return catList;
    }
    
}
