package com.shishkanu.algorithm.service;

import java.util.ArrayList;
import java.util.List;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;

public class EndpointDrawer1 extends Drawer {

    @Override
    public void draw(final FieldInterface fieldManager) {
        List<Point> startPoint = getEndpoint(fieldManager.getStart());
        fieldManager.setStartObjectPoints(startPoint);
        List<Point> goalPoint = getEndpoint(fieldManager.getGoal());
        fieldManager.setGoalObjectPoints(goalPoint);
    }

    private List<Point> getEndpoint(final Point point) {
        List<Point> objectPoint = new ArrayList<>();
        if (point != null) {
            objectPoint.add(point);
        }
        return objectPoint;
    }
    
}
