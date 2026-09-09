package com.iit.masi.strategy;

/**
 * Contexte pour la stratégie de journalisation
 * Permet de changer dynamiquement la stratégie de journalisation
 */
public class Logger {
    private LogStrategy strategy;

    public Logger(LogStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Change la stratégie de journalisation
     */
    public void setStrategy(LogStrategy strategy) {
        this.strategy = strategy;
        log("Stratégie de journalisation changée vers: " + strategy.getStrategyName());
    }

    /**
     * Journalise un message avec la stratégie actuelle
     */
    public void log(String message) {
        if (strategy != null) {
            strategy.log(message);
        }
    }

    public LogStrategy getStrategy() {
        return strategy;
    }
}
