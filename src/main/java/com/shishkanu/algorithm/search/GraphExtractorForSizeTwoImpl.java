package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.service.FieldManager;

public class GraphExtractorForSizeTwoImpl extends GraphExtractor {

    private static Logger log =
            LoggerFactory.getLogger(GraphExtractorForSizeTwoImpl.class.getName());

    @Override
    protected void addWalls(GraphInterface graph, FieldInterface fieldManager) {
        final Set<Point> walls = new HashSet<>();
        for (final Point point : fieldManager.getWalls()) {
            walls.addAll(getWallsFromSinglePoint(point));
        }
        walls.addAll(getWallsFromFieldEdges(fieldManager.getHeight(), fieldManager.getWidth()));
        

        final List<Point> wallsFiltered = walls.stream().filter(point1 -> graph.isInBounds(point1))
                .collect(Collectors.toList());
        graph.addWalls(wallsFiltered);
        
    }
    
    private List<Point> getWallsFromFieldEdges(int height, int width) {
        List<Point> walls = new ArrayList<>();
        
        for (int y = 0; y < height; y++) {
            walls.add(new Point(width - 1, y));
        }
        for (int x = 0; x < width; x++) {
            walls.add(new Point(x, height - 1));
        }
        
        return walls;
    }

    @Override
    public Point getEndpointFromObjectPoints(final List<Point> points) {
        if (points.stream().distinct().count() != 4 ) {
            log.error("invalid data received, cannot calculate endpoint from points={}", points);
            points.clear();
        }
        return points.stream()
            .sorted(Comparator.comparing(Point::getX).thenComparing(Point::getY))
            .findFirst().orElseThrow(() -> 
                new SearchException(String.format("invalid data received, cannot calculate endpoint from points=%s", points)));
    }

    private List<Point> getWallsFromSinglePoint(final Point point) {
        final List<Point> edges = new ArrayList<>();
        edges.add(point);
        edges.add(new Point(point.getX() - 1, point.getY()));
        edges.add(new Point(point.getX(), point.getY() - 1));
        edges.add(new Point(point.getX() - 1, point.getY() - 1));
        return edges;
    }

    @Override
    public List<Point> reconstructPath(final List<Point> pathCalculated) {
        final Set<Point> path = new HashSet<>();
        for (final Point point : pathCalculated) {
            path.addAll(getNeighbourPoints(point));
        }
        return path.stream().collect(Collectors.toList());
    }

    private List<Point> getNeighbourPoints(final Point point) {
        final List<Point> points = new ArrayList<>();
        points.add(point);
        points.add(new Point(point.getX() + 1, point.getY()));
        points.add(new Point(point.getX(), point.getY() + 1));
        points.add(new Point(point.getX() + 1, point.getY() + 1));
        return points;
    }
}
