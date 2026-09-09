package com.iit.masi.command;

import java.util.Stack;

/**
 * Command Pattern — Invoker (selon diagramme UML du cours).
 *
 * UML cours :
 *   Invoker  →  + setCommand()
 *
 * Gère l'historique des commandes et permet undo/redo.
 */
public class CommandManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();
    private Command currentCommand; // setCommand() stocke la commande courante

    /**
     * setCommand() — correspond exactement au diagramme UML Invoker du cours.
     * Définit la commande courante à exécuter.
     */
    public void setCommand(Command command) {
        this.currentCommand = command;
    }

    /**
     * Exécute une commande et l'ajoute à l'historique
     */
    public void executeCommand(Command command) {
        setCommand(command);
        currentCommand.execute();
        undoStack.push(currentCommand);
        redoStack.clear();
    }

    /**
     * Annule la dernière commande
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    /**
     * Restaure la dernière commande annulée
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    /**
     * Vérifie si l'annulation est possible
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * Vérifie si la restauration est possible
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * Efface tout l'historique
     */
    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
