package com.iit.masi.command;

import com.iit.masi.model.Shape;
import com.iit.masi.service.DrawingManager;

/**
 * Commande pour supprimer une forme
 */
public class RemoveShapeCommand implements Command {
    private final DrawingManager drawingManager;
    private final Shape shape;

    public RemoveShapeCommand(DrawingManager drawingManager, Shape shape) {
        this.drawingManager = drawingManager;
        this.shape = shape;
    }

    @Override
    public void execute() {
        drawingManager.removeShapeDirectly(shape);
    }

    @Override
    public void undo() {
        drawingManager.addShapeDirectly(shape);
    }

    @Override
    public String getDescription() {
        return "Suppression de " + shape.getType();
    }

    public Shape getShape() {
        return shape;
    }
}
