package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.service.FieldManager;

public class GraphExtractorForSizeThree extends GraphExtractor {
    
    private static Logger log = LoggerFactory.getLogger(GraphExtractorForSizeThree.class.getName());
    
    @Override
    protected void addWalls(GraphInterface graph, FieldInterface fieldHolder) {
        final Set<Point> walls = new HashSet<>();
        for (final Point point : fieldHolder.getWalls()) {
            walls.add(point);
            walls.addAll(getNeighbours(point));
        }
        
        for (int x = 0; x < graph.getWidth(); x++) {
            walls.add(new Point(x, 0));
            walls.add(new Point(x, graph.getHeight() - 1));
        }
        
        for (int y = 0; y < graph.getHeight(); y++) {
            walls.add(new Point(0, y));
            walls.add(new Point(graph.getHeight() - 1, y));
        }

        final List<Point> wallsFiltered = walls.stream().filter(point1 -> graph.isInBounds(point1))
                    .collect(Collectors.toList());

        graph.addWalls(wallsFiltered);
    }

    @Override
    public Point getEndpointFromObjectPoints(final List<Point> points) {
        final List<Point> distinctPoints = points.stream().distinct().collect(Collectors.toList());
        final int count = (int) distinctPoints.stream().count();

        if (count != 9) {
            log.error("invalid data received, cannot calculate endpoint from points={}", points);
            points.clear();
            throw new SearchException(String.format("invalid data received, cannot calculate endpoint from points={}", points));
        }

        int sumX = 0;
        int sumY = 0;

        for (final Point point : distinctPoints) {
            sumX += point.getX();
            sumY += point.getY();
        }
        return new Point(sumX / count, sumY / count);
    }

    @Override
    public List<Point> reconstructPath(final List<Point> pathCalculated) {
        final Set<Point> path = new HashSet<>();
        for (final Point point : pathCalculated) {
            path.addAll(getNeighbours(point));
        }
        return path.stream().collect(Collectors.toList());
    }

    private List<Point> getNeighbours(final Point point) {
        final List<Point> neighbours = new ArrayList<>();
        neighbours.add(new Point(point.getX() + 1, point.getY()));
        neighbours.add(new Point(point.getX(), point.getY() - 1));
        neighbours.add(new Point(point.getX() - 1, point.getY()));
        neighbours.add(new Point(point.getX(), point.getY() + 1));

        neighbours.add(new Point(point.getX() + 1, point.getY() + 1));
        neighbours.add(new Point(point.getX() + 1, point.getY() - 1));
        neighbours.add(new Point(point.getX() - 1, point.getY() - 1));
        neighbours.add(new Point(point.getX() - 1, point.getY() + 1));

        return neighbours;
    }

}
