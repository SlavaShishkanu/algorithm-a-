package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.shishkanu.algorithm.domain.FieldInterface;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.search.GraphExtractor;
import com.shishkanu.algorithm.search.GraphExtractorForSizeTwoImpl;
import com.shishkanu.algorithm.service.FieldManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GraphExtractorForSizeTwoImplTest {
    private GraphExtractor graphMaker2Impl;
    
    @Mock
    private FieldInterface field;
    
    @BeforeEach
    void setUp() throws Exception {
        graphMaker2Impl = new GraphExtractorForSizeTwoImpl();
    }

    @Test
    void getGraph_ShouldReturnValidGraph() {
        when(field.getHeight()).thenReturn(10);
        when(field.getWidth()).thenReturn(5);
        GraphInterface graph = graphMaker2Impl.getGraph(field);
        assertEquals(9, graph.getHeight());
        assertEquals(4, graph.getWidth());
    }
    
    @Test
    void getGraph_ShouldReturnValidWalls() {
        when(field.getHeight()).thenReturn(5);
        when(field.getWidth()).thenReturn(5);
        
        Set<Point> initialWalls = new HashSet<>();
        initialWalls.add(new Point(1, 2));
        initialWalls.add(new Point(1, 3));
        when(field.getWalls()).thenReturn(initialWalls);

        Set<Point> wallsTransformedExpected = new HashSet<>();
        wallsTransformedExpected.add(new Point(0, 1));
        wallsTransformedExpected.add(new Point(0, 2));
        wallsTransformedExpected.add(new Point(0, 3));
        wallsTransformedExpected.add(new Point(1, 1));
        wallsTransformedExpected.add(new Point(1, 2));
        wallsTransformedExpected.add(new Point(1, 3));

        GraphInterface graph = graphMaker2Impl.getGraph(field);
        assertEquals(wallsTransformedExpected, graph.getWalls().parallelStream().collect(Collectors.toSet()));
    }

    @Test
    void getEndpoint() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2, 1));
        points.add(new Point(3, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 2));
        assertEquals(new Point(2, 1), graphMaker2Impl.getEndpointFromObjectPoints(points));
    }

}
