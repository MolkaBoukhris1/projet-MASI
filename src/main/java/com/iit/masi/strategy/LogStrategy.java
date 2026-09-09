package com.iit.masi.strategy;

/**
 * Strategy Pattern - Interface pour les stratégies de journalisation
 * Ce pattern définit une famille d'algorithmes, encapsule chacun d'eux
 * et les rend interchangeables
 */
public interface LogStrategy {
    /**
     * Journalise un message
     * @param message Message à journaliser
     */
    void log(String message);

    /**
     * Obtient le nom de la stratégie
     */
    String getStrategyName();
}
