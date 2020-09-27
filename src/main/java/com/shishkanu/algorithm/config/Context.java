package com.shishkanu.algorithm.config;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.shishkanu.algorithm.search.AStarSearchAlgorithm;
import com.shishkanu.algorithm.search.Field;
import com.shishkanu.algorithm.search.GraphExtractor;
import com.shishkanu.algorithm.search.GraphExtractorForSizeOne;
import com.shishkanu.algorithm.search.GraphExtractorForSizeThree;
import com.shishkanu.algorithm.search.GraphExtractorForSizeTwoImpl;
import com.shishkanu.algorithm.service.Drawer;
import com.shishkanu.algorithm.service.EndpointDrawer1;
import com.shishkanu.algorithm.service.EndpointDrawer2;
import com.shishkanu.algorithm.service.EndpointDrawer3;
import com.shishkanu.algorithm.service.FieldManager;
import com.shishkanu.algorithm.service.PathFiller;

/**
 * Algorithm context that should be initialized in order to work 
 * with algorithm({@link com.shishkanu.algorithm.service.FieldManager FieldManager} class)</br>
 * It's made as singleton
 * call {@link #init()} method, then retrieve {@link com.shishkanu.algorithm.service.FieldManager FieldManager}</br>
 * 
 * @author Slava Shishkanu
 *
 */
public final class Context {
    private static Logger log = LoggerFactory.getLogger(Context.class.getName());
    
    private static final int FIELD_HEIGHT = 100;
    
    private static final int FIELD_WIDTH = 100;
    
    private static final int MAX_OBJECT_SIZE = 3;

    private int objectSize = 1;
    
    private boolean boldPath = false;

    private boolean diagonalMovementAllowed = false;
    
    private boolean strictEndpoints = true;

    private PathFiller pathComputator;

    private FieldManager fieldHolder;

    private Map<Integer, GraphExtractor> graphExtractors;

    private HashMap<Integer, Drawer> drawers;

    private Context() { }

    public static Context getInstance() {
        return ContextHolder.INSTANCE;
    }

    public void init() {
        log.debug("Initializing context");

        drawers = new HashMap<>();
        drawers.put(1, new EndpointDrawer1());
        drawers.put(2, new EndpointDrawer2());
        drawers.put(3, new EndpointDrawer3());

        graphExtractors = new HashMap<>();
        graphExtractors.put(1, new GraphExtractorForSizeOne());
        graphExtractors.put(2, new GraphExtractorForSizeTwoImpl());
        graphExtractors.put(3, new GraphExtractorForSizeThree());
        graphExtractors.forEach((i, graphExtractor) -> graphExtractor.setDiagonalMovementAllowed(diagonalMovementAllowed));

        pathComputator = new PathFiller(graphExtractors, new AStarSearchAlgorithm());
        pathComputator.setBoldPath(boldPath);

        fieldHolder = new FieldManager(drawers, pathComputator, MAX_OBJECT_SIZE,
                objectSize,
                new Field(FIELD_WIDTH, FIELD_HEIGHT));
        
        setStrictEndpoints(strictEndpoints);
        
    }

    public boolean isDiagonalMovementAllowed() {
        return diagonalMovementAllowed;
    }

    public void setDiagonalMovementAllowed(boolean diagonalMovementAllowed) {
        this.diagonalMovementAllowed = diagonalMovementAllowed;
        graphExtractors.forEach((i, graphExtractor) -> graphExtractor.setDiagonalMovementAllowed(diagonalMovementAllowed));
    }
    
    public boolean isStrictEndpoints() {
        return strictEndpoints;
    }

    public void setStrictEndpoints(boolean strictEndpoints) {
        drawers.forEach((i, drawer) -> drawer.setStrictEndpoints(strictEndpoints));
        this.strictEndpoints = strictEndpoints;
    }

    public boolean isBoldPath() {
        return boldPath;
    }

    public void setBoldPath(boolean boldPath) {
        this.boldPath = boldPath;
        pathComputator.setBoldPath(boldPath);
    }

    public PathFiller getPathComputator() {
        return pathComputator;
    }

    public FieldManager getFieldManager() {
        return fieldHolder;
    }
    
    public int getObjectSize() {
        return objectSize;
    }

    public void setObjectSize(int objectSize) {
        this.objectSize = objectSize;
        fieldHolder.setObjectSize(objectSize);
    }

    private static class ContextHolder {
        public static final Context INSTANCE = new Context();
    }
}
