package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.shishkanu.algorithm.domain.Point;

public class Graph implements GraphInterface {
    
    private boolean[][] barrierMask;
    
    private final int height;
    
    private final int width;
    
    private boolean diagonalMovementAllowed;

    public Graph(final int height, final int width) {
        barrierMask = new boolean[height][width];
        this.height = height;
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public List<Point> getWalls() {
        List<Point> barriers = new ArrayList<>();
        for (int y = 0; y < barrierMask.length; y++) {
            for (int x = 0; x < barrierMask[0].length; x++) {
                if (barrierMask[y][x]) {
                    barriers.add(new Point(x, y));
                }
            }
        }

        return barriers;
    }

    @Override
    public void addWalls(final List<Point> walls) {
        walls.stream()
                .filter(this::isInBounds)
                .filter(this::isAccessible)
                .forEach(point -> {
                    barrierMask[point.getY()][point.getX()] = true;
                });
    }

    @Override
    public void addWall(final Point point) {
        if (isInBounds(point) && isAccessible(point)) {
            barrierMask[point.getY()][point.getX()] = true;
        }
    }

    @Override
    public void addWall(final int x, final int y) {
        final Point point = new Point(x, y);
        addWall(point);
    }

    @Override
    public void removeWall(final Point point) {
        if (isInBounds(point) && !isAccessible(point)) {
            barrierMask[point.getY()][point.getX()] = false;
        }
    }


    /**
     * Cost function, calculates cost of movement between two points.</br>
     * Used by {@link AStarSearchAlgorithm#getResult(GraphInterface, Point, Point)}
     * when computing cost of movement between neighbor points.</br>
     * Can be merged (refactored) with function {@link #getNeighbours(Point) getNeighbours} because it is
     * called only on neighbor points.</br>
     * Currently it returns sqrt(2) for diagonal neghbors and 1 for others.
     *  
     * 
     */
    @Override
    public float getCost(final Point from, final Point to) {
        if (diagonalMovementAllowed) {
            if (Math.abs(from.getX() - to.getX()) == 1 && Math.abs(from.getY() - to.getY()) == 0) {
                return 1;
            }
            if (Math.abs(from.getX() - to.getX()) == 0 && Math.abs(from.getY() - to.getY()) == 1) {
                return 1;
            }
            
            if (Math.abs(from.getX() - to.getX()) == 1 && Math.abs(from.getY() - to.getY()) == 1) {
                return 1.5F;
            }
        }
        
        return (float) Math.sqrt(Math.pow(from.getX() - to.getX(), 2) + Math.pow(from.getY() - to.getY(), 2));
    }

    @Override
    public void clear() {
        barrierMask = new boolean[height][width];
    }
    
    @Override
    public List<Point> getNeighbours(final Point point) {
        return getNeighbours(point.getX(), point.getY());
    }

    private List<Point> getNeighbours(final int x, final int y) {
        final List<Point> neighbours = new ArrayList<>();
        neighbours.add(new Point(x + 1, y));
        neighbours.add(new Point(x, y - 1));
        neighbours.add(new Point(x - 1, y));
        neighbours.add(new Point(x, y + 1));

        if (diagonalMovementAllowed) {
            neighbours.add(new Point(x - 1, y - 1));
            neighbours.add(new Point(x - 1, y + 1));
            neighbours.add(new Point(x + 1, y - 1));
            neighbours.add(new Point(x + 1, y + 1));
        }

        return neighbours.stream()
                .filter(this::isInBounds)
                .filter(this::isAccessible)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isInBounds(final Point point) {
        return point.getX() >= 0 && point.getX() < width &&
                point.getY() >= 0 && point.getY() < height;
    }

    @Override
    public boolean isAccessible(final Point point) {
        return !barrierMask[point.getY()][point.getX()];
    }

    @Override
    public boolean isDiagonalMovementAllowed() {
        return diagonalMovementAllowed;
    }

    @Override
    public void setDiagonalMovementAllowed(boolean diagonalMovementAllowed) {
        this.diagonalMovementAllowed = diagonalMovementAllowed;
    }

}
