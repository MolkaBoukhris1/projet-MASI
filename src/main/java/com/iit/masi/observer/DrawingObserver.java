package com.iit.masi.observer;

/**
 * Observer Pattern — interface Observer (selon diagramme UML du cours).
 *
 * UML cours :
 *   << interface >> Observer  →  + update()
 *
 * MainController est le ConcretObserver : il implémente update().
 */
public interface DrawingObserver {

    /**
     * Appelé par DrawingManager (ConcretSubject) quand l'état change.
     * Correspond à update() dans le diagramme UML du cours.
     */
    void update();
}
