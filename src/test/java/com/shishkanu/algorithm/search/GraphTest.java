package com.shishkanu.algorithm.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.search.Graph;

class GraphTest {
    private GraphInterface graph; 
    
    @BeforeEach
    void setUp() throws Exception {
        graph = new Graph(5, 10);
        graph.addWall(1, 0);
        graph.addWall(1, 1);
        graph.addWall(0, 1);
        
        graph.addWall(1, 2);
        graph.addWall(2, 0);
    }

    @AfterEach
    void tearDown() throws Exception {}

    @Test
    void shouldReturnSixPoints() {
        assertEquals(5, graph.getWalls().size());
    }
    
    @Test
    public void shouldReturnSixAgain_WhenDuplicateInserted() {
        graph.addWall(1, 0);
        graph.addWall(1, 2);
        graph.addWall(2, 0);
        assertEquals(5, graph.getWalls().size());
    }
    
    @Test
    public void getWalls_shouldReturnSameWalls_OrderDoesntMatter() {
        Set<Point> points = new HashSet<Point>();
        points.add(new Point(1, 0));
        points.add(new Point(1, 1));
        points.add(new Point(0, 1));
        points.add(new Point(1, 2));
        points.add(new Point(2, 0));
        
        assertEquals(points, graph.getWalls().stream().collect(Collectors.toSet()));
    }
    
    @Test
    public void getNeighbours_shouldReturnZeroNeighbours() {
        assertTrue(graph.getNeighbours(new Point(0, 0)).isEmpty());
    }
    
    @Test
    public void getNeighbours_shouldReturnSingleNeighbours() {
        assertEquals(1, graph.getNeighbours(new Point(0, 2)).size());
        assertEquals(new Point(0, 3), graph.getNeighbours(new Point(0, 2)).get(0));
    }
    
    @Test
    public void getNeighbours_shouldReturnTwoNeighbours() {
        assertEquals(2, graph.getNeighbours(new Point(2, 1)).size());
    }
    
    @Test
    public void getNeighbours_shouldReturnFourNeighbours() {
        assertEquals(4, graph.getNeighbours(new Point(3, 1)).size());
    }
    
   
}
