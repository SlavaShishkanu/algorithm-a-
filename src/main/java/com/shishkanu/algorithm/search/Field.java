package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;

public class Field implements FieldInterface {
    
    private GraphInterface graph;

    private int objectSize;

    private Point start;

    private Point goal;

    private List<Point> startSquare = new ArrayList<>();

    private List<Point> goalSquare = new ArrayList<>();

    private List<Point> path = new ArrayList<>();

    public Field(final int width, final int height) {
        graph = new Graph(height, width);
    }

    @Override
    public int getObjectSize() {
        return objectSize;
    }

    @Override
    public void setSquareSize(final int squareSize) {
        this.objectSize = squareSize;
    }

    @Override
    public int getHeight() {
        return graph.getHeight();
    }

    @Override
    public int getWidth() {
        return graph.getWidth();
    }

    @Override
    public Set<Point> getWalls() {
        return new HashSet<>(graph.getWalls());
    }

    @Override
    public void setWalls(final Set<Point> walls) {
        graph.clear();
        graph.addWalls(new ArrayList<>(walls));
    }

    @Override
    public Point getStart() {
        return start;
    }

    @Override
    public void setStart(final Point start) {
        this.start = start;
    }

    @Override
    public Point getGoal() {
        return goal;
    }

    @Override
    public void setGoal(final Point goal) {
        this.goal = goal;
    }

    @Override
    public List<Point> getStartObjectPoints() {
        return startSquare;
    }

    @Override
    public void setStartObjectPoints(final List<Point> startSquare) {
        this.startSquare = startSquare;
    }

    @Override
    public List<Point> getGoalObjectPoints() {
        return goalSquare;
    }

    @Override
    public void setGoalObjectPoints(final List<Point> goalSquare) {
        this.goalSquare = goalSquare;
    }

    @Override
    public List<Point> getPath() {
        return path;
    }

    @Override
    public void setPath(final List<Point> path) {
        this.path = path;
    }

    @Override
    public void clearGoal() {
        goalSquare.clear();
        
    }

    @Override
    public void clearStart() {
        startSquare.clear();
    }

    @Override
    public void clearWalls() {
        graph.clear();
        
    }

    @Override
    public void clearField() {
        clearStart();
        clearWalls();
        clearGoal();
        clearPath();
    }

    @Override
    public void clearPath() {
        path.clear();
    }

    @Override
    public void removeWall(Point point) {
        graph.removeWall(point);
    }

    @Override
    public void addWall(Point point) {
        graph.addWall(point);
    }

    @Override
    public boolean isInBoudsAndAccessible(int x, int y) {
        return isInBoundsAndAccessible(new Point(x, y));
    }

    @Override
    public boolean isInBoundsAndAccessible(Point point) {
        return graph.isInBounds(point) && graph.isAccessible(point);
    }
    
}
