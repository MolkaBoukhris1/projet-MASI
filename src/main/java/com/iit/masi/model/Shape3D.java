package com.iit.masi.model;

import javafx.scene.paint.Color;

/**
 * Classe abstraite pour les formes 3D
 */
public abstract class Shape3D extends Shape {
    protected double depth; // Profondeur pour la 3D
    protected double rotateX;
    protected double rotateY;
    protected double rotateZ;

    public Shape3D(double startX, double startY, double endX, double endY, 
                   Color color, double strokeWidth, double depth) {
        super(startX, startY, endX, endY, color, strokeWidth);
        this.depth = depth;
        this.rotateX = 0;
        this.rotateY = 0;
        this.rotateZ = 0;
    }

    public double getDepth() { return depth; }
    public void setDepth(double depth) { this.depth = depth; }
    
    public double getRotateX() { return rotateX; }
    public void setRotateX(double rotateX) { this.rotateX = rotateX; }
    
    public double getRotateY() { return rotateY; }
    public void setRotateY(double rotateY) { this.rotateY = rotateY; }
    
    public double getRotateZ() { return rotateZ; }
    public void setRotateZ(double rotateZ) { this.rotateZ = rotateZ; }
}
