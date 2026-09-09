package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe abstraite représentant une forme géométrique
 */
public abstract class Shape {
    protected double startX;
    protected double startY;
    protected double endX;
    protected double endY;
    protected Color color;
    protected double strokeWidth;
    protected String id;

    public Shape(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.id = java.util.UUID.randomUUID().toString();
    }

    /**
     * Méthode abstraite pour dessiner la forme
     */
    public abstract void draw(GraphicsContext gc);

    /**
     * Méthode pour vérifier si un point est dans la forme
     */
    public abstract boolean contains(double x, double y);

    /**
     * Obtenir le type de la forme
     */
    public abstract String getType();

    // Getters et Setters
    public double getStartX() { return startX; }
    public double getStartY() { return startY; }
    public double getEndX() { return endX; }
    public double getEndY() { return endY; }
    public Color getColor() { return color; }
    public double getStrokeWidth() { return strokeWidth; }
    public String getId() { return id; }

    public void setStartX(double startX) { this.startX = startX; }
    public void setStartY(double startY) { this.startY = startY; }
    public void setEndX(double endX) { this.endX = endX; }
    public void setEndY(double endY) { this.endY = endY; }
    public void setColor(Color color) { this.color = color; }
    public void setStrokeWidth(double strokeWidth) { this.strokeWidth = strokeWidth; }
    public void setId(String id) { this.id = id; }
}
