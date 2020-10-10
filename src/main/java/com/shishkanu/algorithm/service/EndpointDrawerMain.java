package com.shishkanu.algorithm.service;

import java.util.HashMap;

import com.shishkanu.algorithm.domain.FieldInterface;

public class EndpointDrawerMain extends Drawer {
    
    private HashMap<Integer, Drawer> drawers;

    public EndpointDrawerMain() {
	drawers = new HashMap<>();
        drawers.put(1, new EndpointDrawer1());
        drawers.put(2, new EndpointDrawer2());
        drawers.put(3, new EndpointDrawer3());
    }
    
    @Override
    void draw(FieldInterface field) {
	final Drawer endpointsDrawer = drawers.get(field.getObjectSize());
        endpointsDrawer.draw(field);
    }

}
