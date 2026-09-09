package com.iit.masi.observer;

/**
 * Observer Pattern — interface Observable (selon diagramme UML du cours).
 *
 * UML cours :
 *   << interface >> Observable
 *     + addObs()
 *     + removeObs()
 *     + notifyAllObs()
 *
 * DrawingManager implémente cette interface (ConcretSubject dans le cours).
 */
public interface Observable {

    /** Ajoute un observateur */
    void addObs(DrawingObserver observer);

    /** Supprime un observateur */
    void removeObs(DrawingObserver observer);

    /** Notifie tous les observateurs */
    void notifyAllObs();
}
