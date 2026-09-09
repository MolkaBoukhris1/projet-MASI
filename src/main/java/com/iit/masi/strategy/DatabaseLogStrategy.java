package com.iit.masi.strategy;

import com.iit.masi.repository.DatabaseManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stratégie de journalisation dans une base de données
 */
public class DatabaseLogStrategy implements LogStrategy {
    private final DatabaseManager databaseManager;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DatabaseLogStrategy() {
        this.databaseManager = DatabaseManager.getInstance();
    }

    @Override
    public void log(String message) {
        try {
            String timestamp = LocalDateTime.now().format(formatter);
            databaseManager.insertLog(timestamp, message);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'écriture dans la base de données: " + e.getMessage());
        }
    }

    @Override
    public String getStrategyName() {
        return "Base de données";
    }
}
