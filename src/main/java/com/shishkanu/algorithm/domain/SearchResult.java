package com.shishkanu.algorithm.domain;

import java.util.Map;

public class SearchResult {
    
    private Map<Point, Point> cameFrom;
    
    private Map<Point, Float> costSoFar;
    
    public SearchResult() {
    }
    
    public SearchResult(final Map<Point, Point> cameFrom, final Map<Point, Float> costSoFar) {
        this.cameFrom = cameFrom;
        this.costSoFar = costSoFar;
    }

    public Map<Point, Point> getCameFrom() {
        return cameFrom;
    }
    
    public void setCameFrom(final Map<Point, Point> cameFrom) {
        this.cameFrom = cameFrom;
    }
    
    public Map<Point, Float> getCostSoFar() {
        return costSoFar;
    }
    
    public void setCostSoFar(final Map<Point, Float> costSoFar) {
        this.costSoFar = costSoFar;
    }
    
}
