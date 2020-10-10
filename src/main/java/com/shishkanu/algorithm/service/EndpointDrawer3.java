package com.shishkanu.algorithm.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;

public class EndpointDrawer3 extends Drawer {
    private static final String POINT = "point={}";
    private static final String EXPANDED_POINTS = "expanded points={}";
    private static final String NOT_IN_BOUND = "not in bound={}";
    private static Logger log = LoggerFactory.getLogger(EndpointDrawer3.class.getName());
    
    @Override
    public void draw(FieldInterface fieldHolder) {
        List<Point> startPoints = drawSquare(fieldHolder.getStart(), fieldHolder);
        fieldHolder.setStartObjectPoints(startPoints);
        List<Point> goalObjectPoints = drawSquare(fieldHolder.getGoal(), fieldHolder);
        fieldHolder.setGoalObjectPoints(goalObjectPoints);
    }

    private List<Point> drawSquare(final Point point, final FieldInterface fieldHolder) {
        List<Point> objectPoints = new ArrayList<>();
        log.debug(POINT, point);
        if (point == null) {
            return objectPoints;
        }
        final List<Point> pointsExpanded = expandPoint(point);
        log.debug(EXPANDED_POINTS, pointsExpanded);
        
        final long pointsNotInBounds = pointsExpanded.stream()
                .filter(point1 -> !fieldHolder.isInBoundsAndAccessible(point1))
                .count();
        log.debug(NOT_IN_BOUND, pointsNotInBounds);
                
        if (pointsNotInBounds == 0) {
            objectPoints.addAll(pointsExpanded);
            return objectPoints;
        }
        
            return objectPoints;
    }

    private List<Point> expandPoint(final Point point) {
        final List<Point> points = new ArrayList<>();
        points.add(new Point(point.getX() - 1, point.getY() - 1));
        points.add(new Point(point.getX(), point.getY() - 1));
        points.add(new Point(point.getX() + 1, point.getY() - 1));

        points.add(new Point(point.getX() - 1, point.getY()));
        points.add(new Point(point.getX(), point.getY()));
        points.add(new Point(point.getX() + 1, point.getY()));

        points.add(new Point(point.getX() - 1, point.getY() + 1));
        points.add(new Point(point.getX(), point.getY() + 1));
        points.add(new Point(point.getX() + 1, point.getY() + 1));
        return points;
    }

}
