package com.shishkanu.algorithm.search;

import java.util.List;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.service.FieldManager;

/**
 * Creates graph from Field. <br/>
 * For object size = 1 does nothing, just adds walls as is. <br/>
 * For object size 2 and 3 graph remains the same but walls are added in different way
 * Also method {@link #reconstructPath(List)} reconstructPath is needed for marking path on the
 * entire object if boolean parameter boldPath in
 * {@link com.shishkanu.algorithm.service.PathFiller PathFiller} is set to true;
 * 
 * @see com.shishkanu.algorithm.service#PathFiller PathFiller
 * @author Slava Shishkanu
 */
public abstract class GraphExtractor {

    protected boolean diagonalMovementAllowed;

    public GraphExtractor() {}

    public GraphExtractor(boolean diagonalMovementAllowed) {
        this.diagonalMovementAllowed = diagonalMovementAllowed;
    }

    /**
     * Creates graph from Field. <br/>
     * For object size = 2 method is overridden and builds different graph <br/>
     * For object size = 3 uses different method for adding walls <br/>
     * 
     * @param FieldInterface
     * @return Graph
     */
    public GraphInterface getGraph(FieldInterface field) {
        final GraphInterface graph = new Graph(field.getHeight(), field.getWidth());
        graph.setDiagonalMovementAllowed(diagonalMovementAllowed);
        addWalls(graph, field);
        return graph;
    }
    public abstract Point getEndpointFromObjectPoints(List<Point> points);

    /**
     * Transforms calculated path of the center of an object(or upper-left corner if object is 2x2) to
     * path of the entire object
     * 
     * Method is used only by {@link com.shishkanu.algorithm.service.PathFiller PathFiller} if 
     * boolean parameter {@code boldPath} is set to {@code true} in it.
     * 
     * @see com.shishkanu.algorithm.service#PathFiller PathFiller
     * @param pathCalculated
     * @return
     */
    public abstract List<Point> reconstructPath(List<Point> pathCalculated);

    public void setDiagonalMovementAllowed(boolean diagonalMovementAllowed) {
        this.diagonalMovementAllowed = diagonalMovementAllowed;
    }

    public boolean isDiagonalMovementAllowed() {
        return diagonalMovementAllowed;
    }

    protected abstract void addWalls(GraphInterface graph, FieldInterface field);
}
