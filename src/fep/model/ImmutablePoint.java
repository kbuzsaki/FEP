/*
 * Copyright 2013 Kyle Buzsaki
 */
package fep.model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an integer point in 2 dimensional space. This point is immutable.
 * This class's purpose is to provide an immutable alternative to {@link Point}.
 * @author Kyle
 */
public final class ImmutablePoint {
    
    /**
     * The X coordinate for this point. May not be modified.
     */
    private final int x;
    /**
     * The Y coordinate for this point. May not be modified.
     */
    private final int y;
    
    /**
     * Constructs an ImmutablePoint with the specified x and y coordinates.
     * @param x  the specified x coordinate
     * @param y  the specified y coordinate
     */
    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Constructs an ImmutablePoint with the same coordinates as the specified Point.
     * @param point  the specified point
     */
    public ImmutablePoint(Point point) {
        this(point.x, point.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Returns a Point with the same coordinates as this ImmutablePoint.
     * @return a Point with the same coordinates as this ImmutablePoint.
     */
    public Point toPoint() {
        return new Point(getX(), getY());
    }

    /**
     * Returns the absolute distance between this and a specified point.
     * This distance may also be called the distance "as the crow flies".
     * It is defined as:
     * <center>distance = sqrt( (x1-x2)^2 + (y1-y2)^2 )</center>
     * @param point  the point to find this point's distance from.
     * @return the distance between the two points.
     */
    public double distance(ImmutablePoint point) {
        return Point2D.distance(x, y, point.x, point.y);
    }
    
    /**
     * Returns the gridwise distance between this point and the specified point.
     * This distance is the sum of the x offset and y offset between the two
     * points. It is defined as:
     * <center>distance = abs(x1-x2) + abs(y1-y2)</center>
     * @param point
     * @return 
     */
    public int distanceGrid(ImmutablePoint point) {
        return Math.abs(x - point.x) + Math.abs(y - point.y);
    }
    
    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
    
    @Override
    public int hashCode() {
        // lifted and slightly changed from java.awt.geom.Point2D
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 17;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ImmutablePoint) {
            ImmutablePoint ip = (ImmutablePoint) obj;
            return (getX() == ip.getX()) && (getY() == ip.getY());
        }
        return super.equals(obj);
    }
    
    /**
     * Returns true if and only if the specified point is adjacent to this point. 
     * Adjacency is defined as having a distance of 1 between the points.
     * This method is logically equivalent to {@code distance(point)} == 1.
     * @param point  the specified point to check
     * @return true if and only if the specified point is adjacent to this point.
     */
    public boolean isAdjacentTo(ImmutablePoint point) {
        return this.distance(point) == 1;
    }
    
    /**
     * Returns a point offset from this point by 1 in the specified direction.
     * For the purposes of this function, UP is positive, DOWN is negative,
     * LEFT is negative, and RIGHT is positive.
     * @param direction  the direction to be offset in.
     * @return a point 1 unit offset from this point in the specified direction.
     */
    public ImmutablePoint getTranslated(Direction direction) {
        return getTranslated(direction, 1);
    }
    
    /**
     * Returns a point offset from this point by the specified magnitude in the 
     * specified direction. For the purposes of this function, UP is positive, 
     * DOWN is negative, LEFT is negative, and RIGHT is positive.
     * @param direction  the direction to be offset in.
     * @return a point 1 unit offset from this point in the specified direction.
     */
    public ImmutablePoint getTranslated(Direction direction, int magnitude) {
        switch(direction) {
            case UP:    return new ImmutablePoint(getX(), getY()+magnitude);
            case DOWN:  return new ImmutablePoint(getX(), getY()-magnitude);
            case LEFT:  return new ImmutablePoint(getX()-magnitude, getY());
            case RIGHT: return new ImmutablePoint(getX()+magnitude, getY());
            default:
                throw new AssertionError();
        }
    }
    
    /**
     * Returns the set of ImmutablePoints that are adjacent to this point.
     * @return the set of ImmutablePoints that are adjacent to this point.
     */
    public Set<ImmutablePoint> getAdjacentPoints() {
        Set<ImmutablePoint> adjacentPoints = new HashSet<>();
        for(Direction direction : Direction.values()) {
            adjacentPoints.add(getTranslated(direction));
        }
        return adjacentPoints;
    }
    
}
