package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.domain.SearchResult;

/**
 * Implementation of A star search algorithm
 * 
 * 
 * @author Slava Shishkanu
 *
 */
public class AStarSearchAlgorithm {
    
    /**
     * Extracts path from results
     * 
     * @param goal
     * @param searchResult
     * @return
     */
    public List<Point> extractPath(final Point goal, final SearchResult searchResult) {
	final Map<Point, Point> cameFrom = searchResult.getCameFrom();
	final List<Point> path = new ArrayList<>();

	path.add(goal);
	Point previous = cameFrom.get(goal);

	while (previous != null) {
	    path.add(previous);
	    previous = searchResult.getCameFrom().get(previous);
	}
	Collections.reverse(path);
	return path;
    }

    /**
     * Returns result of search algorithm containing two maps: </br>
     * {@code Map<Point, Point>} cameFrom - {@code cameFrom.get(givenPoint)} gives
     * parent of givenPoint </br>
     * {@code Map<Point, Float>} costSoFar - stores cost of current point that
     * consist of:</br>
     * cost of movement from start to this point + cost calculated by heuristic
     * function {@link #heuristic(Point, Point, Point, boolean)}
     * 
     * @param grid
     * @param start
     * @param goal
     * @return
     */
    public SearchResult getResult(final GraphInterface grid, final Point start, final Point goal) {
	final Map<Point, Point> cameFrom = new HashMap<>();
	cameFrom.put(start, null);
	final Map<Point, Float> costSoFar = new HashMap<>();
	costSoFar.put(start, 0.0F);
	final Comparator<PointWithPriority> priorityComparator = new Comparator<PointWithPriority>() {

	    @Override
	    public int compare(final PointWithPriority p1, final PointWithPriority p2) {
		return (int) (p1.priority - p2.priority);
	    }
	};

	final PriorityQueue<PointWithPriority> frontier = new PriorityQueue<PointWithPriority>(priorityComparator);
	frontier.add(new PointWithPriority(start, 0));
	while (!frontier.isEmpty()) {
	    final Point currentPoint = frontier.poll().getPoint();
	    if (currentPoint.equals(goal)) {
		break;
	    }
	    for (final Point nextPoint : grid.getNeighbours(currentPoint)) {
		final float newCost = costSoFar.get(currentPoint) + grid.getCost(currentPoint, nextPoint);
		if (!costSoFar.containsKey(nextPoint) || newCost < costSoFar.get(nextPoint)) {
		    costSoFar.put(nextPoint, newCost);
		    final float priority = newCost
			    + heuristic(start, nextPoint, goal, grid.isDiagonalMovementAllowed());
		    frontier.add(new PointWithPriority(nextPoint, priority));
		    cameFrom.put(nextPoint, currentPoint);
		}
	    }
	}
	return new SearchResult(cameFrom, costSoFar);
    }

    /**
     * Heuristic function </br>
     * See <a
     * href=http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html#heuristics-for-grid-maps>
     * read about heuristic on http://theory.stanford.edu/ </a>
     * 
     * Also check cost function {@link Graph#getCost(Point, Point)}, maybe it should
     * be changed or merged into {@link Graph#getNeighbours(Point)} as it called
     * 
     * Manhattan distance should be used in case diagonal movement disabled. </br>
     * Diagonal distance - Use in case diagonal movement enabled.</br>
     * 
     * @param start
     * @param point
     * @param goal
     * @param isDiagonalAllowed
     * @return
     */
    public float heuristic(final Point start, final Point point, final Point goal, boolean isDiagonalAllowed) {
	float heuristic = 0F;
	if (isDiagonalAllowed) {
	    heuristic = diagonalDistance(point, goal);
	} else {
	    heuristic = manhattanDistance(point, goal);
	}
	return heuristic;
    }

    private float manhattanDistance(final Point point, final Point goal) {
	float heuristic;
	heuristic = Math.abs(goal.getX() - point.getX()) + Math.abs(goal.getY() - point.getY());
	return heuristic;
    }

    private float diagonalDistance(final Point point, final Point goal) {
	float heuristic;
	int dx = Math.abs(point.getX() - goal.getX());
	int dy = Math.abs(point.getY() - goal.getY());
	float D = 1;
	float D2 = 1; // 1 or 2 -octile distance.
	heuristic = D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
	return heuristic;
    }

    class PointWithPriority {

	private Point point;

	private float priority;

	PointWithPriority(final Point point, final float priority) {
	    this.point = point;
	    this.priority = priority;
	}

	public Point getPoint() {
	    return point;
	}

	public void setPoint(final Point point) {
	    this.point = point;
	}

	public float getPriority() {
	    return priority;
	}

	public void setPriority(final int priority) {
	    this.priority = priority;
	}
    }
}
