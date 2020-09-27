package com.shishkanu.algorithm.search;

import java.util.List;
import com.shishkanu.algorithm.domain.Point;

public interface GraphInterface {

    int getHeight();

    int getWidth();

    List<Point> getWalls();

    void addWalls(List<Point> walls);

    void addWall(Point point);

    void addWall(int x, int y);

    void removeWall(Point point);

    float getCost(Point from, Point to);

    void clear();

    List<Point> getNeighbours(Point point);

    boolean isInBounds(Point point);

    boolean isAccessible(Point point);

    boolean isDiagonalMovementAllowed();

    void setDiagonalMovementAllowed(boolean diagonalMovementAllowed);

}
