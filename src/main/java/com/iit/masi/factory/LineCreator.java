package com.iit.masi.factory;

import com.iit.masi.model.Line;
import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

/** ConcreteCreator pour Line (Factory Method Pattern). */
public class LineCreator implements ShapeCreator {
    @Override
    public Shape factoryMethod(double startX, double startY,
                               double endX,   double endY,
                               Color color,   double strokeWidth) {
        return new Line(startX, startY, endX, endY, color, strokeWidth);
    }
}
