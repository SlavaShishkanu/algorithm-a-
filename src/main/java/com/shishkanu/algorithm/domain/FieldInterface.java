package com.shishkanu.algorithm.domain;

import java.util.List;
import java.util.Set;

public interface FieldInterface {

    int getObjectSize();

    void setSquareSize(int squareSize);

    int getHeight();

    int getWidth();

    Set<Point> getWalls();

    void setWalls(Set<Point> walls);

    Point getStart();

    void setStart(Point start);

    Point getGoal();

    void setGoal(Point goal);

    List<Point> getStartObjectPoints();

    void setStartObjectPoints(List<Point> startSquare);

    List<Point> getGoalObjectPoints();

    void setGoalObjectPoints(List<Point> goalSquare);

    List<Point> getPath();

    void setPath(List<Point> path);

    void clearGoal();

    void clearStart();

    void clearWalls();

    void clearField();

    void clearPath();

    void removeWall(Point point);

    void addWall(Point point);

    boolean isInBoudsAndAccessible(int x, int y);

    boolean isInBoundsAndAccessible(Point point);

}
