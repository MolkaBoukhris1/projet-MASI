package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe représentant une ligne
 */
public class Line extends Shape {

    public Line(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        super(startX, startY, endX, endY, color, strokeWidth);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(strokeWidth);
        gc.strokeLine(startX, startY, endX, endY);
    }

    @Override
    public boolean contains(double x, double y) {
        // Distance d'un point à une ligne
        double distance = Math.abs((endY - startY) * x - (endX - startX) * y + 
                                   endX * startY - endY * startX) /
                         Math.sqrt(Math.pow(endY - startY, 2) + Math.pow(endX - startX, 2));
        
        return distance <= strokeWidth + 5; // Tolérance de 5 pixels
    }

    @Override
    public String getType() {
        return "Line";
    }
}
