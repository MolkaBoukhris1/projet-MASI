package com.iit.masi.command;

import com.iit.masi.model.Rectangle;
import com.iit.masi.model.Shape;
import com.iit.masi.service.DrawingManager;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour CommandManager
 */
class CommandManagerTest {

    private CommandManager commandManager;
    private DrawingManager drawingManager;

    @BeforeEach
    void setUp() {
        commandManager = new CommandManager();
        drawingManager = DrawingManager.getInstance();
        drawingManager.clear();
    }

    @Test
    void testExecuteCommand() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2.0);
        Command command = new AddShapeCommand(drawingManager, shape);
        
        commandManager.executeCommand(command);
        
        assertTrue(drawingManager.getShapes().contains(shape));
        assertTrue(commandManager.canUndo());
        assertFalse(commandManager.canRedo());
    }

    @Test
    void testUndo() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2.0);
        Command command = new AddShapeCommand(drawingManager, shape);
        
        commandManager.executeCommand(command);
        assertTrue(drawingManager.getShapes().contains(shape));
        
        commandManager.undo();
        assertFalse(drawingManager.getShapes().contains(shape));
        assertFalse(commandManager.canUndo());
        assertTrue(commandManager.canRedo());
    }

    @Test
    void testRedo() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2.0);
        Command command = new AddShapeCommand(drawingManager, shape);
        
        commandManager.executeCommand(command);
        commandManager.undo();
        commandManager.redo();
        
        assertTrue(drawingManager.getShapes().contains(shape));
        assertTrue(commandManager.canUndo());
        assertFalse(commandManager.canRedo());
    }

    @Test
    void testClear() {
        Shape shape = new Rectangle(10, 10, 100, 100, Color.RED, 2.0);
        Command command = new AddShapeCommand(drawingManager, shape);
        
        commandManager.executeCommand(command);
        commandManager.clear();
        
        assertFalse(commandManager.canUndo());
        assertFalse(commandManager.canRedo());
    }

    @Test
    void testMultipleUndoRedo() {
        Shape shape1 = new Rectangle(10, 10, 100, 100, Color.RED, 2.0);
        Shape shape2 = new Rectangle(20, 20, 120, 120, Color.BLUE, 3.0);
        
        commandManager.executeCommand(new AddShapeCommand(drawingManager, shape1));
        commandManager.executeCommand(new AddShapeCommand(drawingManager, shape2));
        
        assertEquals(2, drawingManager.getShapes().size());
        
        commandManager.undo();
        assertEquals(1, drawingManager.getShapes().size());
        
        commandManager.undo();
        assertEquals(0, drawingManager.getShapes().size());
        
        commandManager.redo();
        assertEquals(1, drawingManager.getShapes().size());
        
        commandManager.redo();
        assertEquals(2, drawingManager.getShapes().size());
    }
}
