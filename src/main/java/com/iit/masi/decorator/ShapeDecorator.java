package com.iit.masi.decorator;

import com.iit.masi.model.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Decorator Pattern - Classe abstraite de base pour les décorateurs de formes.
 * Permet d'ajouter dynamiquement des comportements visuels (label, bordure, etc.)
 * à n'importe quelle forme sans modifier ses classes concrètes.
 */
public abstract class ShapeDecorator extends Shape {

    protected final Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        super(decoratedShape.getStartX(), decoratedShape.getStartY(),
              decoratedShape.getEndX(),   decoratedShape.getEndY(),
              decoratedShape.getColor(),  decoratedShape.getStrokeWidth());
        this.decoratedShape = decoratedShape;
    }

    /** Dessine la forme de base, puis ajoute la décoration */
    @Override
    public void draw(GraphicsContext gc) {
        decoratedShape.draw(gc);
    }

    @Override
    public boolean contains(double x, double y) {
        return decoratedShape.contains(x, y);
    }

    @Override
    public String getType() {
        return decoratedShape.getType();
    }

    public Shape getDecoratedShape() {
        return decoratedShape;
    }
}
