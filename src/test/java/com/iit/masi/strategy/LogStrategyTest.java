package com.iit.masi.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour les stratégies de journalisation
 */
class LogStrategyTest {

    @Test
    void testConsoleLogStrategy() {
        LogStrategy strategy = new ConsoleLogStrategy();
        assertEquals("Console", strategy.getStrategyName());
        
        // Test que la méthode log ne lance pas d'exception
        assertDoesNotThrow(() -> strategy.log("Test message"));
    }

    @Test
    void testFileLogStrategy() {
        LogStrategy strategy = new FileLogStrategy();
        assertEquals("Fichier", strategy.getStrategyName());
        
        // Test que la méthode log ne lance pas d'exception
        assertDoesNotThrow(() -> strategy.log("Test message"));
    }

    @Test
    void testDatabaseLogStrategy() {
        LogStrategy strategy = new DatabaseLogStrategy();
        assertEquals("Base de données", strategy.getStrategyName());
        
        // Test que la méthode log ne lance pas d'exception
        assertDoesNotThrow(() -> strategy.log("Test message"));
    }

    @Test
    void testLoggerStrategyChange() {
        Logger logger = new Logger(new ConsoleLogStrategy());
        assertEquals("Console", logger.getStrategy().getStrategyName());
        
        logger.setStrategy(new FileLogStrategy());
        assertEquals("Fichier", logger.getStrategy().getStrategyName());
        
        logger.setStrategy(new DatabaseLogStrategy());
        assertEquals("Base de données", logger.getStrategy().getStrategyName());
    }

    @Test
    void testLoggerWithNullStrategy() {
        Logger logger = new Logger(null);
        
        // Ne devrait pas lancer d'exception
        assertDoesNotThrow(() -> logger.log("Test message"));
    }
}
