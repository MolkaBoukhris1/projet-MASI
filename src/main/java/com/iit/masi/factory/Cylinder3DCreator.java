package com.iit.masi.factory;

import com.iit.masi.model.Cylinder3D;
import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

/** ConcreteCreator pour Cylinder3D (Factory Method Pattern). */
public class Cylinder3DCreator implements ShapeCreator {
    @Override
    public Shape factoryMethod(double startX, double startY,
                               double endX,   double endY,
                               Color color,   double strokeWidth) {
        return new Cylinder3D(startX, startY, endX, endY, color, strokeWidth, 20);
    }
}
