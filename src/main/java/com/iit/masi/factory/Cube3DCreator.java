package com.iit.masi.factory;

import com.iit.masi.model.Cube3D;
import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

/** ConcreteCreator pour Cube3D (Factory Method Pattern). */
public class Cube3DCreator implements ShapeCreator {
    @Override
    public Shape factoryMethod(double startX, double startY,
                               double endX,   double endY,
                               Color color,   double strokeWidth) {
        return new Cube3D(startX, startY, endX, endY, color, strokeWidth, 30);
    }
}
