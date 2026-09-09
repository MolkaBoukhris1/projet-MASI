package com.iit.masi.strategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Stratégie de journalisation dans la console
 */
public class ConsoleLogStrategy implements LogStrategy {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] " + message);
    }

    @Override
    public String getStrategyName() {
        return "Console";
    }
}
