package com.iit.masi.command;

import com.iit.masi.model.Shape;
import com.iit.masi.service.DrawingManager;

/**
 * Commande pour ajouter une forme
 */
public class AddShapeCommand implements Command {
    private final DrawingManager drawingManager;
    private final Shape shape;

    public AddShapeCommand(DrawingManager drawingManager, Shape shape) {
        this.drawingManager = drawingManager;
        this.shape = shape;
    }

    @Override
    public void execute() {
        drawingManager.addShapeDirectly(shape);
    }

    @Override
    public void undo() {
        drawingManager.removeShapeDirectly(shape);
    }

    @Override
    public String getDescription() {
        return "Ajout de " + shape.getType();
    }

    public Shape getShape() {
        return shape;
    }
}
