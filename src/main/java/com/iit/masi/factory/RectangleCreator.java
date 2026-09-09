package com.iit.masi.factory;

import com.iit.masi.model.Rectangle;
import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

/**
 * ConcreteCreator pour Rectangle (Factory Method Pattern).
 * Correspond à ConcreteCreatorA dans le diagramme UML du cours.
 */
public class RectangleCreator implements ShapeCreator {

    @Override
    public Shape factoryMethod(double startX, double startY,
                               double endX,   double endY,
                               Color color,   double strokeWidth) {
        return new Rectangle(startX, startY, endX, endY, color, strokeWidth);
    }
}
