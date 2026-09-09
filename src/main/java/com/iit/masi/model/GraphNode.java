package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Représente un nœud dans un graphe
 */
public class GraphNode extends Shape {
    private String label;
    private static final double RADIUS = 20;

    public GraphNode(double x, double y, Color color, String label) {
        super(x, y, x, y, color, 2.0);
        this.label = label;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeWidth);
        
        // Dessiner le cercle
        gc.fillOval(startX - RADIUS, startY - RADIUS, RADIUS * 2, RADIUS * 2);
        gc.strokeOval(startX - RADIUS, startY - RADIUS, RADIUS * 2, RADIUS * 2);
        
        // Dessiner le label
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        gc.fillText(label, startX - 5, startY + 5);
    }

    @Override
    public boolean contains(double x, double y) {
        double distance = Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
        return distance <= RADIUS;
    }

    @Override
    public String getType() {
        return "GraphNode";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getCenterX() {
        return startX;
    }

    public double getCenterY() {
        return startY;
    }
}
