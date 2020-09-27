package com.shishkanu.algorithm.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import com.shishkanu.algorithm.domain.FieldDto;
import com.shishkanu.algorithm.domain.Point;

public class FieldDrawer {

    private int pixelSize;

    public FieldDrawer(final int pixelSize) {
        this.pixelSize = pixelSize;
    }

    public BufferedImage fieldToImage(final FieldDto field) {
        final BufferedImage image = new BufferedImage(field.getWidth() * pixelSize,
                field.getHeight() * pixelSize, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2d = image.createGraphics();

        graphics2d.setColor(Color.LIGHT_GRAY);
        graphics2d.fillRect(0, 0, field.getWidth() * pixelSize, field.getHeight() * pixelSize);

        drawRectangles(graphics2d, field.getPath(), Color.CYAN);
        drawRectangles(graphics2d, field.getStartObjectPoints(), Color.GREEN);
        drawRectangles(graphics2d, field.getGoalObjectPoints(), Color.RED);
        drawRectangles(graphics2d, field.getWalls(),
                Color.BLACK);
        return image;
    }
    

    private void drawRectangles(final Graphics2D graphics2d, final List<Point> points,
            final Color color) {
        graphics2d.setColor(color);
        points.forEach(point -> graphics2d.fillRect(point.getX() * pixelSize,
                                                    point.getY() * pixelSize, pixelSize, pixelSize));
    }

}
