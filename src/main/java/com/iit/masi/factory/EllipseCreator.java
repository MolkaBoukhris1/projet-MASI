package com.iit.masi.factory;

import com.iit.masi.model.Ellipse;
import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

/** ConcreteCreator pour Ellipse (Factory Method Pattern). */
public class EllipseCreator implements ShapeCreator {
    @Override
    public Shape factoryMethod(double startX, double startY,
                               double endX,   double endY,
                               Color color,   double strokeWidth) {
        return new Ellipse(startX, startY, endX, endY, color, strokeWidth);
    }
}
