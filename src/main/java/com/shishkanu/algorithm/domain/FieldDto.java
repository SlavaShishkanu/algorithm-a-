package com.shishkanu.algorithm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FieldDto {
    
    private int height;
    
    private int width;
    
    private List<Point> walls;

    private List<Point> startObjectPoints = new ArrayList<>();

    private List<Point> goalObjectPoints = new ArrayList<>();

    private List<Point> path = new ArrayList<>();

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<Point> getWalls() {
        return walls;
    }

    public void setWalls(List<Point> walls) {
        this.walls = walls;
    }

    public List<Point> getStartObjectPoints() {
        return startObjectPoints;
    }

    public void setStartObjectPoints(List<Point> startSquare) {
        this.startObjectPoints = startSquare;
    }

    public List<Point> getGoalObjectPoints() {
        return goalObjectPoints;
    }

    public void setGoalObjectPoints(List<Point> goalSquare) {
        this.goalObjectPoints = goalSquare;
    }

    public List<Point> getPath() {
        return path;
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }
  
}
