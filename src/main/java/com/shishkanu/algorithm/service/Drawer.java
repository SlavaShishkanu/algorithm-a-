package com.shishkanu.algorithm.service;

import com.shishkanu.algorithm.domain.FieldInterface;

/**
 * Places endpoints on a field. <p/> 
 * EndpointDrawers draws object from start or goal point by filling 
 * goalObjectPoints List
 * 
 * @author Slava Shishkanu
 *
 */
public abstract class Drawer {
    
    abstract void draw(FieldInterface field);

}
