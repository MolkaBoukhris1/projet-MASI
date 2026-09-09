package com.iit.masi.factory;

import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

/**
 * Factory Method Pattern — interface Creator (selon diagramme UML du cours).
 * Déclare la méthode factoryMethod() que chaque créateur concret doit implémenter.
 *
 * UML cours :
 *   << interface >> Creator  →  + factoryMethod()
 *        ↑ implements
 *   ConcreteCreatorA, ConcreteCreatorB
 */
public interface ShapeCreator {

    /**
     * Factory Method — crée une forme selon les paramètres.
     * Chaque créateur concret décide quelle classe instancier.
     */
    Shape factoryMethod(double startX, double startY,
                        double endX,   double endY,
                        Color color,   double strokeWidth);
}
