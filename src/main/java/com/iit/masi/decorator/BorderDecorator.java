package com.iit.masi.decorator;

import com.iit.masi.model.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Decorator Pattern - Ajoute une bordure colorée autour de la bounding box.
 * Peut être combiné avec LabelDecorator :
 *   new LabelDecorator(new BorderDecorator(shape, Color.GOLD))
 */
public class BorderDecorator extends ShapeDecorator {

    private final Color borderColor;
    private final double borderWidth;

    public BorderDecorator(Shape decoratedShape, Color borderColor, double borderWidth) {
        super(decoratedShape);
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // 1. Dessiner la forme de base
        decoratedShape.draw(gc);

        // 2. Dessiner la bordure autour de la bounding box
        double minX = Math.min(decoratedShape.getStartX(), decoratedShape.getEndX()) - 4;
        double minY = Math.min(decoratedShape.getStartY(), decoratedShape.getEndY()) - 4;
        double w    = Math.abs(decoratedShape.getEndX() - decoratedShape.getStartX()) + 8;
        double h    = Math.abs(decoratedShape.getEndY() - decoratedShape.getStartY()) + 8;

        gc.save();
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        gc.setLineDashes(6, 4);
        gc.strokeRoundRect(minX, minY, w, h, 6, 6);
        gc.setLineDashes(null);
        gc.restore();
    }
}
