package com.shishkanu.algorithm.search;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.shishkanu.algorithm.domain.Point;
import com.shishkanu.algorithm.search.GraphExtractor;
import com.shishkanu.algorithm.search.GraphExtractorForSizeThree;
import static org.junit.jupiter.api.Assertions.*;

class GraphExtractorForSizeThreeImplTest {

    private GraphExtractor graphMaker3Impl;
    
    @BeforeEach
    void setUp() throws Exception {
        graphMaker3Impl = new GraphExtractorForSizeThree();
    }

    @AfterEach
    void tearDown() throws Exception {}

    @Test
    void testCalculateEndpoint() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(2, 1));
        points.add(new Point(3, 1));
        points.add(new Point(4, 1));
        
        points.add(new Point(2, 2));
        points.add(new Point(3, 2));
        points.add(new Point(4, 2));
        
        points.add(new Point(2, 3));
        points.add(new Point(3, 3));
        points.add(new Point(4, 3));
        
        assertEquals(new Point(3, 2), graphMaker3Impl.getEndpointFromObjectPoints(points));

    }

}
