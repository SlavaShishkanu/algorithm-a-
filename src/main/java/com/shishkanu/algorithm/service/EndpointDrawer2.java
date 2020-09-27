package com.shishkanu.algorithm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;

public class EndpointDrawer2 extends Drawer {

    @Override
    public void draw(final FieldInterface fieldHolder) {
        List<Point> startObjectPoints = expandByOneRightAndDown(fieldHolder.getStart(), fieldHolder);
        fieldHolder.setStartObjectPoints(startObjectPoints);
        List<Point> goalObjectPoints = expandByOneRightAndDown(fieldHolder.getGoal(), fieldHolder);
        fieldHolder.setGoalObjectPoints(goalObjectPoints);
    }
    
    private List<Point> expandByOneRightAndDown(final Point point, final FieldInterface fieldHolder) {
        List<Point> objectPoints = new ArrayList<>();
        
        if (point != null) {
            final List<Point> expandedRight = expandRightOrElseLeft(point, fieldHolder);
            if (expandedRight.isEmpty()) {
                return objectPoints;
            } 
            
            objectPoints.addAll(expandedRight);
            
            final List<Point> expandedDownOrUp = expandDownOrElseUp(objectPoints, fieldHolder);
            if (expandedDownOrUp.isEmpty()) {
                objectPoints.clear();
                return objectPoints;
            }
            objectPoints.addAll(expandedDownOrUp);
        }
        return objectPoints;
    }

    private List<Point> expandRightOrElseLeft(final Point point, final FieldInterface fieldHolder) {
        final List<Point> points = new ArrayList<>();
        points.add(point);
        if (fieldHolder.isInBoudsAndAccessible(point.getX() + 1, point.getY())) {
            points.add(new Point(point.getX() + 1, point.getY()));
            return points;
        }
        
        if (strictEndpoints) {
            return points;
        }

        if (fieldHolder.isInBoudsAndAccessible(point.getX() - 1, point.getY())) {
            points.add(new Point(point.getX() - 1, point.getY()));
            return points;
        }
        points.clear();
        return points;
    }

    private List<Point> expandDownOrElseUp(final List<Point> expandedRight, final FieldInterface fieldHolder) {
        final List<Point> expandedDown = expandedRight.stream()
                .map(point -> new Point(point.getX(), point.getY() + 1))
                .collect(Collectors.toList());
        if (expandedDown.stream().allMatch(fieldHolder::isInBoundsAndAccessible)) {
            return expandedDown;
        }
        
        if (strictEndpoints) {
            return expandedDown;
        }

        final List<Point> expandedUp = expandedRight.stream()
                .map(point -> new Point(point.getX(), point.getY() - 1))
                .collect(Collectors.toList());
        if (expandedUp.stream().allMatch(fieldHolder::isInBoundsAndAccessible)) {
            return expandedUp;
        }
        return new ArrayList<>();
    }


}
