package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe représentant un rectangle
 */
public class Rectangle extends Shape {

    public Rectangle(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        super(startX, startY, endX, endY, color, strokeWidth);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(strokeWidth);
        
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        
        gc.strokeRect(x, y, width, height);
    }

    @Override
    public boolean contains(double x, double y) {
        double minX = Math.min(startX, endX);
        double maxX = Math.max(startX, endX);
        double minY = Math.min(startY, endY);
        double maxY = Math.max(startY, endY);
        
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    @Override
    public String getType() {
        return "Rectangle";
    }
}
