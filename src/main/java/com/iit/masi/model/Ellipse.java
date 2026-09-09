package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe représentant une ellipse
 */
public class Ellipse extends Shape {

    public Ellipse(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
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
        
        gc.strokeOval(x, y, width, height);
    }

    @Override
    public boolean contains(double x, double y) {
        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;
        double a = Math.abs(endX - startX) / 2;
        double b = Math.abs(endY - startY) / 2;
        
        if (a == 0 || b == 0) return false;
        
        double result = Math.pow(x - centerX, 2) / Math.pow(a, 2) + 
                       Math.pow(y - centerY, 2) / Math.pow(b, 2);
        
        return result <= 1;
    }

    @Override
    public String getType() {
        return "Ellipse";
    }
}
