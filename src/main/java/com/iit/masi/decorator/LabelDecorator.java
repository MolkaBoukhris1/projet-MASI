package com.iit.masi.decorator;

import com.iit.masi.model.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Decorator Pattern - Ajoute le nom de la forme au centre de celle-ci.
 * Usage : new LabelDecorator(shape)
 */
public class LabelDecorator extends ShapeDecorator {

    public LabelDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw(GraphicsContext gc) {
        // 1. Dessiner la forme de base
        decoratedShape.draw(gc);

        // 2. Calculer le centre de la bounding box
        double cx = (decoratedShape.getStartX() + decoratedShape.getEndX()) / 2.0;
        double cy = (decoratedShape.getStartY() + decoratedShape.getEndY()) / 2.0;

        String label = decoratedShape.getType();

        // 3. Dessiner un fond blanc semi-transparent pour la lisibilité
        gc.save();
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        // Estimation taille du texte (environ 7px par caractère)
        double textW = label.length() * 7.0;
        double textH = 14.0;
        double padding = 3.0;

        gc.setFill(Color.rgb(255, 255, 255, 0.75));
        gc.fillRoundRect(cx - textW / 2 - padding,
                         cy - textH / 2 - padding,
                         textW + padding * 2,
                         textH + padding * 2,
                         6, 6);

        // 4. Dessiner le texte
        gc.setFill(Color.rgb(30, 30, 30));
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(0.5);
        gc.strokeText(label, cx - textW / 2, cy + textH / 2 - 2);
        gc.fillText(label,   cx - textW / 2, cy + textH / 2 - 2);

        gc.restore();
    }

    @Override
    public String getType() {
        return decoratedShape.getType();
    }
}
