package com.iit.masi.factory;

import com.iit.masi.model.Circle;
import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

/**
 * ConcreteCreator pour Circle (Factory Method Pattern).
 */
public class CircleCreator implements ShapeCreator {

    @Override
    public Shape factoryMethod(double startX, double startY,
                               double endX,   double endY,
                               Color color,   double strokeWidth) {
        return new Circle(startX, startY, endX, endY, color, strokeWidth);
    }
}
