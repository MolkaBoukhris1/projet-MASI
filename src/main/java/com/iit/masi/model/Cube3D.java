package com.iit.masi.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

/**
 * Cube 3D avec effet ombre, remplissage et dégradé isométrique
 */
public class Cube3D extends Shape3D {

    public Cube3D(double startX, double startY, double endX, double endY,
                  Color color, double strokeWidth, double depth) {
        super(startX, startY, endX, endY, color, strokeWidth, depth);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double w = Math.abs(endX - startX);
        double h = Math.abs(endY - startY);
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double d = Math.max(depth, 20); // offset isométrique

        // ── Ombre portée ──────────────────────────────────────────
        gc.setFill(Color.rgb(0, 0, 0, 0.25));
        // On dessine une forme décalée vers le bas-droite
        double[] shadowX = { x + d + 6, x + w + d + 6, x + w + 6, x + 6 };
        double[] shadowY = { y - d + 6, y - d + 6, y + h + 6, y + h + 6 };
        gc.fillPolygon(shadowX, shadowY, 4);

        // ── Face GAUCHE (la plus sombre) ──────────────────────────
        Color darkFace = color.darker().darker();
        gc.setFill(darkFace);
        double[] leftX = { x,     x + d, x + d, x };
        double[] leftY = { y + h, y + h - d, y - d, y };
        // correctif : face gauche isométrique
        double[] lx = { x,         x,         x + d,     x + d };
        double[] ly = { y,         y + h,     y + h - d, y - d };
        gc.fillPolygon(lx, ly, 4);
        gc.setStroke(color.darker().darker().darker());
        gc.setLineWidth(strokeWidth * 0.6);
        gc.strokePolygon(lx, ly, 4);

        // ── Face DESSUS (la plus claire) ──────────────────────────
        Color lightFace = color.brighter().brighter();
        gc.setFill(lightFace);
        double[] tx = { x,     x + w,     x + w + d, x + d };
        double[] ty = { y,     y,         y - d,     y - d };
        gc.fillPolygon(tx, ty, 4);
        gc.setStroke(color.darker());
        gc.setLineWidth(strokeWidth * 0.6);
        gc.strokePolygon(tx, ty, 4);

        // ── Face AVANT avec dégradé ───────────────────────────────
        LinearGradient gradient = new LinearGradient(
            0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
            new Stop(0.0, color.brighter()),
            new Stop(1.0, color.darker())
        );
        gc.setFill(gradient);
        gc.fillRect(x, y, w, h);

        // Bordure face avant
        gc.setStroke(color.darker().darker());
        gc.setLineWidth(strokeWidth);
        gc.strokeRect(x, y, w, h);

        // ── Highlight (reflet lumineux coin supérieur gauche) ─────
        gc.setFill(Color.rgb(255, 255, 255, 0.18));
        gc.fillRect(x + 2, y + 2, w * 0.35, h * 0.25);
    }

    @Override
    public boolean contains(double x, double y) {
        double minX = Math.min(startX, endX);
        double maxX = Math.max(startX, endX) + depth;
        double minY = Math.min(startY, endY) - depth;
        double maxY = Math.max(startY, endY);
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    @Override
    public String getType() { return "Cube3D"; }
}
