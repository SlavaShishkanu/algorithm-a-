package com.shishkanu.algorithm.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.search.GraphExtractor;
import com.shishkanu.algorithm.search.GraphExtractorForSizeTwoImpl;
import com.shishkanu.algorithm.search.GraphInterface;
import com.shishkanu.algorithm.search.SearchException;
import com.shishkanu.algorithm.search.AStarSearchAlgorithm;

public class PathFiller {
    
    private static Logger log =
            LoggerFactory.getLogger(PathFiller.class.getName());
    
    private Map<Integer, GraphExtractor> graphMakers;
    
    private AStarSearchAlgorithm search;
    
    private boolean boldPath;
    
    public PathFiller() { }

    public PathFiller(final Map<Integer, GraphExtractor> graphMakers, final AStarSearchAlgorithm search) {
        this.graphMakers = graphMakers;
        this.search = search;
    }

    public Map<Integer, GraphExtractor> getFieldGraphTransformers() {
        return graphMakers;
    }

    public void setFieldGraphTransormers(final Map<Integer, GraphExtractor> graphMakers) {
        this.graphMakers = graphMakers;
    }

    public AStarSearchAlgorithm getSearch() {
        return search;
    }

    public void setSearch(final AStarSearchAlgorithm search) {
        this.search = search;
    }
    
    public void setBoldPath(boolean boldPath) {
        this.boldPath = boldPath;
    }
    
    public void fillPath(FieldInterface field) {
        if (field.getStartObjectPoints().isEmpty() || field.getGoalObjectPoints().isEmpty()) {
            field.clearPath();
            return;
        }
        
        final GraphExtractor graphMaker = graphMakers.get(field.getObjectSize());
        final GraphInterface graph = graphMaker.getGraph(field);
        Point start = null;
        Point goal = null;
        try {
            start = graphMaker.getEndpointFromObjectPoints(field.getStartObjectPoints());
            goal = graphMaker.getEndpointFromObjectPoints(field.getGoalObjectPoints());
        } catch (SearchException e) {
            log.error("Cannot get put endpoint", e);
            field.clearPath();
            return;
        }
        
        final List<Point> rawPath = search.getPath(graph, start, goal);
        if (boldPath) {
            final List<Point> pathBold = graphMaker.reconstructPath(rawPath);
            Collections.reverse(pathBold);
            field.setPath(pathBold);
        } else {
            Collections.reverse(rawPath);
            field.setPath(rawPath);
        }
        
    }
    
}
