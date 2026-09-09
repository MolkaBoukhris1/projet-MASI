package com.iit.masi.factory;

import com.iit.masi.model.Shape;
import com.iit.masi.model.Sphere3D;
import javafx.scene.paint.Color;

/** ConcreteCreator pour Sphere3D (Factory Method Pattern). */
public class Sphere3DCreator implements ShapeCreator {
    @Override
    public Shape factoryMethod(double startX, double startY,
                               double endX,   double endY,
                               Color color,   double strokeWidth) {
        return new Sphere3D(startX, startY, endX, endY, color, strokeWidth, 30);
    }
}
