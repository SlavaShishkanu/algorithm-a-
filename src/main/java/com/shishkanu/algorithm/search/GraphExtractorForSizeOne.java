package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.service.FieldManager;

public class GraphExtractorForSizeOne extends GraphExtractor {
    
    private static Logger log = LoggerFactory.getLogger(GraphExtractorForSizeOne.class.getName());
    
    @Override
    public Point getEndpointFromObjectPoints(final List<Point> points) {
        final List<Point> distinctPoints = points.stream().distinct().collect(Collectors.toList());
        if (distinctPoints.size() != 1) {
            log.error("cannot calculate endpoint from points={}", points);
            points.clear();
            throw new SearchException(String.format("invalid data received, cannot calculate endpoint from points={}", points));
        }
        return distinctPoints.get(0);
    }

    @Override
    public List<Point> reconstructPath(final List<Point> pathCalculated) {
        return pathCalculated;
    }
    
    @Override
    protected void addWalls(GraphInterface graph, FieldInterface fieldInterface) {
        graph.addWalls(new ArrayList<>(fieldInterface.getWalls()));
        
    }

}
