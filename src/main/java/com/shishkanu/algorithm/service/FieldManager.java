package com.shishkanu.algorithm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.domain.FieldDto;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;

/**
 * Main class that maintains field
 * All interactions with field shouild be done throug this class
 * call {@link #updateField()} to update field and fill path
 * then you can get FieldDto and get everything about field current state
 * i.e. path, walls and endpoints markers
 * 
 * @author Slava Shishkanu
 *
 */
public class FieldManager {
    
    private static final String POINT_IS_NOT_ACCESSIBLE = "point={} is not accessible";

    private static final String FIELD_IS = "field={}";

    private static Logger log = LoggerFactory.getLogger(FieldManager.class.getName());

    private int maxObjectSize;

    private FieldInterface field;

    private Map<Integer, Drawer> drawers;

    private PathFiller pathComputator;

    public FieldManager(final Map<Integer, Drawer> drawers, final PathFiller pathComputator,
            final int maxSquareSize, final int initialSquareSize, final FieldInterface field) {
        this.drawers = drawers;
        this.pathComputator = pathComputator;
        this.maxObjectSize = maxSquareSize;
        this.field = field;
        this.setObjectSize(initialSquareSize);
    }

    public void updateField() {
        log.debug("square size ={}", getObjectSize());
        
        final Drawer endpointsDrawer = drawers.get(getObjectSize());
        endpointsDrawer.draw(field);

        log.debug(FIELD_IS, field);
        
        if (field.getStartObjectPoints().stream().anyMatch(point -> field.getWalls().contains(point))) {
            field.clearStart();
        }
        if (field.getGoalObjectPoints().stream().anyMatch(point -> field.getWalls().contains(point))) {
            field.clearGoal();
        }    
        
        if (field.getStartObjectPoints().isEmpty() || field.getGoalObjectPoints().isEmpty()) {
            clearPath();
            return;
        }
        log.debug(FIELD_IS, field);
        pathComputator.fillPath(field);
    }

    public void setStart(final Point start) {
        if (field.isInBoundsAndAccessible(start)) {
            field.setStart(start);
        } else {
            log.debug(POINT_IS_NOT_ACCESSIBLE, start);
            return;
        }
    }

    public void setGoal(final Point goal) {
        if (field.isInBoundsAndAccessible(goal)) {
            field.setGoal(goal);
        } else {
            log.debug(POINT_IS_NOT_ACCESSIBLE, goal);
            return;
        }
    }

    public void setWalls(final List<Point> walls) {
        field.setWalls(
                walls.stream().filter(field::isInBoundsAndAccessible)
                .collect(Collectors.toSet()));
    }

    public void setObjectSize(final int objectSize) {
        if (objectSize > 0 && objectSize <= maxObjectSize) {
            field.setSquareSize(objectSize);
        } else {
            log.error("algorithm for size={} not implemented", objectSize);
            throw new UnsupportedOperationException(String.format("algorithm for size=%s not implemented", objectSize));
        }
    }
    
    public void addWall(final int x, final int y) {
        addWall(new Point(x, y));
    }

    public void addWall(final Point point) {
        
        if (field.isInBoundsAndAccessible(point)) {
            field.addWall(point);
        } else {
            log.debug("point ={} is not accessible", point);
            return;
        }
        
        if (field.getGoalObjectPoints().contains(point)) {
            field.clearGoal();
        }
        if (field.getStartObjectPoints().contains(point)) {
            field.clearStart();
        }
    }
    
    public FieldDto getFieldDto() {
        FieldDto fieldDto = new FieldDto();
        fieldDto.setStartObjectPoints(copyData(field.getStartObjectPoints()));
        fieldDto.setGoalObjectPoints(copyData(field.getGoalObjectPoints()));
        fieldDto.setWalls(copyData(field.getWalls()));
        fieldDto.setHeight(field.getHeight());
        fieldDto.setWidth(field.getWidth());
        fieldDto.setPath(copyData(field.getPath()));
        return fieldDto;
    }
    
    private List<Point> copyData(Collection<Point> source) {
        List<Point> destination = new ArrayList<>();
                source.forEach(point -> destination.add(new Point(point.getX(), point.getY())));       
        return destination;
    }

    public int getObjectSize() {
        return field.getObjectSize();
    }

    public int getMaxObjectSize() {
        return maxObjectSize;
    }

    public void setMaxObjectSize(final int size) {
        this.maxObjectSize = size;
    }

    public int getHeight() {
        return field.getHeight();
    }

    public int getWidth() {
        return field.getWidth();
    }

    public Point getStart() {
        return field.getStart();
    }

    public Point getGoal() {
        return field.getGoal();
    }

    public void clearPath() {
        field.clearPath();
    }

    public void wallsClear() {
        field.clearWalls();
    }

    public void removeWall(final Point point) {
        field.removeWall(point);
    }

}
