package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

/**
 * Cylindre 3D avec dégradé latéral, capuchon elliptique et ombre portée
 */
public class Cylinder3D extends Shape3D {

    public Cylinder3D(double startX, double startY, double endX, double endY,
                      Color color, double strokeWidth, double depth) {
        super(startX, startY, endX, endY, color, strokeWidth, depth);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double w  = Math.abs(endX - startX);
        double h  = Math.abs(endY - startY);
        double x  = Math.min(startX, endX);
        double y  = Math.min(startY, endY);
        double eh = Math.max(depth * 0.4, 12); // hauteur de l'ellipse des bouchons

        // ── Ombre portée elliptique ────────────────────────────────
        gc.setFill(Color.rgb(0, 0, 0, 0.22));
        gc.fillOval(x + w * 0.05, y + h + eh * 0.3, w * 0.9, eh * 0.7);

        // ── Corps avec dégradé horizontal (reflet sur la gauche) ───
        LinearGradient bodyGrad = new LinearGradient(
            0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
            new Stop(0.0,  Color.WHITE.interpolate(color, 0.30)),  // reflet lumineux gauche
            new Stop(0.35, color),                                  // couleur principale
            new Stop(0.75, color.darker()),                         // zone sombre
            new Stop(1.0,  color.darker().darker())                 // bord droit très sombre
        );
        gc.setFill(bodyGrad);
        // On utilise un rectangle pour le corps (clip manuel sur les côtés)
        gc.fillRect(x, y + eh / 2, w, h - eh / 2);

        // ── Bouchon INFÉRIEUR (ellipse) ────────────────────────────
        gc.setFill(color.darker());
        gc.fillOval(x, y + h - eh / 2, w, eh);
        gc.setStroke(color.darker().darker());
        gc.setLineWidth(strokeWidth * 0.7);
        gc.strokeOval(x, y + h - eh / 2, w, eh);

        // ── Bouchon SUPÉRIEUR (ellipse) - plus claire car face à la lumière
        gc.setFill(color.brighter().brighter());
        gc.fillOval(x, y, w, eh);
        gc.setStroke(color.darker());
        gc.setLineWidth(strokeWidth * 0.7);
        gc.strokeOval(x, y, w, eh);

        // ── Contours verticaux du corps ────────────────────────────
        gc.setStroke(color.darker().darker());
        gc.setLineWidth(strokeWidth);
        gc.strokeLine(x,     y + eh / 2, x,     y + h - eh / 2 + eh / 2);
        gc.strokeLine(x + w, y + eh / 2, x + w, y + h - eh / 2 + eh / 2);

        // ── Reflet lumineux sur le corps (bande verticale blanche) ─
        gc.setFill(Color.rgb(255, 255, 255, 0.20));
        gc.fillRect(x + w * 0.08, y + eh / 2, w * 0.18, h - eh / 2);
    }

    @Override
    public boolean contains(double x, double y) {
        double minX = Math.min(startX, endX);
        double maxX = Math.max(startX, endX);
        double minY = Math.min(startY, endY);
        double maxY = Math.max(startY, endY);
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    @Override
    public String getType() { return "Cylinder3D"; }
}
