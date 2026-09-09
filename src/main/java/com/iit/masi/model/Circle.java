package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe représentant un cercle
 */
public class Circle extends Shape {

    public Circle(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        super(startX, startY, endX, endY, color, strokeWidth);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(strokeWidth);
        
        double centerX = startX;
        double centerY = startY;
        double radius = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        
        gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
    }

    @Override
    public boolean contains(double x, double y) {
        double radius = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        double distance = Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
        return distance <= radius;
    }

    @Override
    public String getType() {
        return "Circle";
    }
}
