package com.iit.masi.strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stratégie de journalisation dans un fichier texte
 */
public class FileLogStrategy implements LogStrategy {
    private static final String LOG_FILE = "drawing_app.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void log(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.println("[" + timestamp + "] " + message);
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier log: " + e.getMessage());
        }
    }

    @Override
    public String getStrategyName() {
        return "Fichier";
    }
}
