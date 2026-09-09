package com.iit.masi.factory;

import com.iit.masi.model.*;
import javafx.scene.paint.Color;

/**
 * Factory Pattern - Fabrique pour créer des formes géométriques
 * Ce pattern permet de créer des objets sans spécifier leur classe exacte
 */
public class ShapeFactory {
    
    public enum ShapeType {
        RECTANGLE, CIRCLE, ELLIPSE, LINE, CUBE3D, SPHERE3D, CYLINDER3D
    }

    /**
     * Crée une forme selon le type spécifié
     * @param type Type de forme à créer
     * @param startX Coordonnée X de départ
     * @param startY Coordonnée Y de départ
     * @param endX Coordonnée X de fin
     * @param endY Coordonnée Y de fin
     * @param color Couleur de la forme
     * @param strokeWidth Épaisseur du trait
     * @return La forme créée
     */
    public static Shape createShape(ShapeType type, double startX, double startY,
                                   double endX, double endY, Color color, double strokeWidth) {
        // Client utilise ShapeCreator (Creator) — Factory Method Pattern
        ShapeCreator creator = switch (type) {
            case RECTANGLE  -> new RectangleCreator();
            case CIRCLE     -> new CircleCreator();
            case ELLIPSE    -> new EllipseCreator();
            case LINE       -> new LineCreator();
            case CUBE3D     -> new Cube3DCreator();
            case SPHERE3D   -> new Sphere3DCreator();
            case CYLINDER3D -> new Cylinder3DCreator();
        };
        return creator.factoryMethod(startX, startY, endX, endY, color, strokeWidth);
    }

    /**
     * Crée une forme à partir d'une chaîne de caractères
     */
    public static Shape createShape(String type, double startX, double startY, 
                                   double endX, double endY, Color color, double strokeWidth) {
        try {
            ShapeType shapeType = ShapeType.valueOf(type.toUpperCase());
            return createShape(shapeType, startX, startY, endX, endY, color, strokeWidth);
        } catch (IllegalArgumentException e) {
            // Pour la compatibilité avec les anciens dessins
            return switch (type) {
                case "Rectangle" -> new Rectangle(startX, startY, endX, endY, color, strokeWidth);
                case "Circle" -> new Circle(startX, startY, endX, endY, color, strokeWidth);
                case "Ellipse" -> new Ellipse(startX, startY, endX, endY, color, strokeWidth);
                case "Line" -> new Line(startX, startY, endX, endY, color, strokeWidth);
                case "Cube3D" -> new Cube3D(startX, startY, endX, endY, color, strokeWidth, 30);
                case "Sphere3D" -> new Sphere3D(startX, startY, endX, endY, color, strokeWidth, 30);
                case "Cylinder3D" -> new Cylinder3D(startX, startY, endX, endY, color, strokeWidth, 20);
                default -> new Rectangle(startX, startY, endX, endY, color, strokeWidth);
            };
        }
    }
}
