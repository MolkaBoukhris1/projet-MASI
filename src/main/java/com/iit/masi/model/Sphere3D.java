package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

/**
 * Sphère 3D avec dégradé radial et ombre portée
 */
public class Sphere3D extends Shape3D {

    public Sphere3D(double startX, double startY, double endX, double endY,
                    Color color, double strokeWidth, double depth) {
        super(startX, startY, endX, endY, color, strokeWidth, depth);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double cx = startX;
        double cy = startY;
        double radius = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        if (radius < 5) radius = 5;

        // ── Ombre portée elliptique sous la sphère ─────────────────
        gc.setFill(Color.rgb(0, 0, 0, 0.22));
        gc.fillOval(cx - radius * 0.85, cy + radius * 0.75,
                    radius * 1.7, radius * 0.35);

        // ── Corps de la sphère avec dégradé radial ─────────────────
        // Le point lumineux est décalé vers le haut-gauche (lumière en haut)
        double focusX = cx - radius * 0.3;
        double focusY = cy - radius * 0.35;

        RadialGradient gradient = new RadialGradient(
            0, 0,                          // focus angle/distance
            (focusX - (cx - radius)) / (radius * 2), // focusX normalisé [0..1]
            (focusY - (cy - radius)) / (radius * 2), // focusY normalisé [0..1]
            0.55,                          // radius normalisé
            true,
            CycleMethod.NO_CYCLE,
            new Stop(0.0, Color.WHITE.interpolate(color, 0.25)),   // reflet blanc
            new Stop(0.45, color),                                  // couleur principale
            new Stop(1.0, color.darker().darker())                  // bord sombre
        );

        gc.setFill(gradient);
        gc.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);

        // ── Contour ─────────────────────────────────────────────────
        gc.setStroke(color.darker().darker());
        gc.setLineWidth(strokeWidth * 0.8);
        gc.strokeOval(cx - radius, cy - radius, radius * 2, radius * 2);

        // ── Reflet secondaire (petit cercle blanc translucide) ──────
        double hlR = radius * 0.18;
        gc.setFill(Color.rgb(255, 255, 255, 0.55));
        gc.fillOval(focusX - hlR, focusY - hlR, hlR * 2, hlR * 2);
    }

    @Override
    public boolean contains(double x, double y) {
        double radius = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        double distance = Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
        return distance <= radius;
    }

    @Override
    public String getType() { return "Sphere3D"; }
}
