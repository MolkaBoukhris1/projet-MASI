package com.iit.masi.service;

import com.iit.masi.command.AddShapeCommand;
import com.iit.masi.command.CommandManager;
import com.iit.masi.command.RemoveShapeCommand;
import com.iit.masi.model.Shape;
import com.iit.masi.observer.DrawingObserver;
import com.iit.masi.observer.Observable;
import com.iit.masi.strategy.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern - Gestionnaire de dessin
 * Observer Pattern — ConcretSubject : implémente Observable, gère les observers
 */
public class DrawingManager implements Observable {
    private static DrawingManager instance;
    private final List<Shape> shapes;
    private final List<DrawingObserver> observers;
    private final CommandManager commandManager;
    private Logger logger;

    private DrawingManager() {
        this.shapes = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.commandManager = new CommandManager();
    }

    /**
     * Obtient l'instance unique du DrawingManager (Singleton)
     */
    public static synchronized DrawingManager getInstance() {
        if (instance == null) {
            instance = new DrawingManager();
        }
        return instance;
    }

    /**
     * Définit le logger
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Ajoute un observateur — correspond à addObs() du cours
     */
    @Override
    public void addObs(DrawingObserver observer) {
        observers.add(observer);
    }

    /**
     * Supprime un observateur — correspond à removeObs() du cours
     */
    @Override
    public void removeObs(DrawingObserver observer) {
        observers.remove(observer);
    }

    // Méthodes conservées pour compatibilité avec MainController
    public void addObserver(DrawingObserver observer)    { addObs(observer); }
    public void removeObserver(DrawingObserver observer) { removeObs(observer); }

    /**
     * Notifie tous les observateurs — correspond à notifyAllObs() du cours
     */
    @Override
    public void notifyAllObs() {
        for (DrawingObserver observer : observers) {
            observer.update();
        }
    }

    // Alias interne pour compatibilité du reste du code
    private void notifyObservers() {
        notifyAllObs();
    }

    /**
     * Ajoute une forme avec commande (pour undo/redo)
     */
    public void addShape(Shape shape) {
        AddShapeCommand command = new AddShapeCommand(this, shape);
        commandManager.executeCommand(command);
        if (logger != null) {
            logger.log("Ajout d'une forme: " + shape.getType());
        }
        notifyObservers();
    }

    /**
     * Ajoute une forme directement (utilisé par les commandes)
     */
    public void addShapeDirectly(Shape shape) {
        shapes.add(shape);
        notifyObservers();
    }

    /**
     * Supprime une forme avec commande (pour undo/redo)
     */
    public void removeShape(Shape shape) {
        RemoveShapeCommand command = new RemoveShapeCommand(this, shape);
        commandManager.executeCommand(command);
        if (logger != null) {
            logger.log("Suppression d'une forme: " + shape.getType());
        }
        notifyObservers();
    }

    /**
     * Supprime une forme directement (utilisé par les commandes)
     */
    public void removeShapeDirectly(Shape shape) {
        shapes.remove(shape);
        notifyObservers();
    }

    /**
     * Annule la dernière action
     */
    public void undo() {
        if (commandManager.canUndo()) {
            commandManager.undo();
            if (logger != null) {
                logger.log("Annulation de la dernière action");
            }
            notifyObservers();
        }
    }

    /**
     * Restaure la dernière action annulée
     */
    public void redo() {
        if (commandManager.canRedo()) {
            commandManager.redo();
            if (logger != null) {
                logger.log("Restauration de l'action annulée");
            }
            notifyObservers();
        }
    }

    /**
     * Efface toutes les formes
     */
    public void clear() {
        shapes.clear();
        commandManager.clear();
        if (logger != null) {
            logger.log("Effacement de toutes les formes");
        }
        notifyObservers();
    }

    /**
     * Charge des formes
     */
    public void loadShapes(List<Shape> newShapes) {
        shapes.clear();
        shapes.addAll(newShapes);
        commandManager.clear();
        if (logger != null) {
            logger.log("Chargement de " + newShapes.size() + " formes");
        }
        notifyObservers();
    }

    /**
     * Obtient toutes les formes
     */
    public List<Shape> getShapes() {
        return new ArrayList<>(shapes);
    }

    /**
     * Trouve une forme à une position donnée
     */
    public Shape findShapeAt(double x, double y) {
        // Parcourir les formes en ordre inverse (les dernières dessinées en premier)
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.contains(x, y)) {
                return shape;
            }
        }
        return null;
    }

    public boolean canUndo() {
        return commandManager.canUndo();
    }

    public boolean canRedo() {
        return commandManager.canRedo();
    }
}
