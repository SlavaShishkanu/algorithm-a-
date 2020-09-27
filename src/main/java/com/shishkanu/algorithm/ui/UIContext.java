package com.shishkanu.algorithm.ui;

public final class UIContext {
    
    private static final int WINDOW_HEIGHT = 750;

    private static final int WINDOW_WIDTH = 870;

    private static final int PIXEL_SIZE = 7;
    
    private FieldDrawer fieldDrawer; 

    private AppMode mode = AppMode.SET_START;

    private UIContext() { }

    public static UIContext getInstance() {
        return ContextHolder.INSTANCE;
    }

    public void init() {
        fieldDrawer = new FieldDrawer(PIXEL_SIZE);
    }

    public FieldDrawer getFieldDrawer() {
        return fieldDrawer;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getPixelSize() {
        return PIXEL_SIZE;
    }

    public AppMode getMode() {
        return mode;
    }

    public void setMode(final AppMode mode) {
        this.mode = mode;
    }

    private static class ContextHolder {
        public static final UIContext INSTANCE = new UIContext();
    }
}
