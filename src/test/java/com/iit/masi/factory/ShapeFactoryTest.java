package com.iit.masi.factory;

import com.iit.masi.model.*;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour ShapeFactory
 */
class ShapeFactoryTest {

    @Test
    void testCreateRectangle() {
        Shape shape = ShapeFactory.createShape(
            ShapeFactory.ShapeType.RECTANGLE,
            10, 10, 100, 100,
            Color.RED, 2.0
        );
        
        assertNotNull(shape);
        assertTrue(shape instanceof Rectangle);
        assertEquals("Rectangle", shape.getType());
        assertEquals(10, shape.getStartX());
        assertEquals(10, shape.getStartY());
        assertEquals(100, shape.getEndX());
        assertEquals(100, shape.getEndY());
        assertEquals(Color.RED, shape.getColor());
        assertEquals(2.0, shape.getStrokeWidth());
    }

    @Test
    void testCreateCircle() {
        Shape shape = ShapeFactory.createShape(
            ShapeFactory.ShapeType.CIRCLE,
            50, 50, 100, 100,
            Color.BLUE, 3.0
        );
        
        assertNotNull(shape);
        assertTrue(shape instanceof Circle);
        assertEquals("Circle", shape.getType());
    }

    @Test
    void testCreateEllipse() {
        Shape shape = ShapeFactory.createShape(
            ShapeFactory.ShapeType.ELLIPSE,
            20, 20, 120, 80,
            Color.GREEN, 1.5
        );
        
        assertNotNull(shape);
        assertTrue(shape instanceof Ellipse);
        assertEquals("Ellipse", shape.getType());
    }

    @Test
    void testCreateLine() {
        Shape shape = ShapeFactory.createShape(
            ShapeFactory.ShapeType.LINE,
            0, 0, 200, 200,
            Color.BLACK, 4.0
        );
        
        assertNotNull(shape);
        assertTrue(shape instanceof Line);
        assertEquals("Line", shape.getType());
    }

    @Test
    void testCreateShapeFromString() {
        Shape shape = ShapeFactory.createShape(
            "RECTANGLE",
            10, 10, 100, 100,
            Color.RED, 2.0
        );
        
        assertNotNull(shape);
        assertTrue(shape instanceof Rectangle);
    }
}
