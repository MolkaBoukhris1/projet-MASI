package com.iit.masi.controller;

import com.iit.masi.decorator.LabelDecorator;
import com.iit.masi.decorator.BorderDecorator;
import com.iit.masi.factory.ShapeFactory;
import com.iit.masi.model.Shape;
import com.iit.masi.observer.DrawingObserver;
import com.iit.masi.repository.DatabaseManager;
import com.iit.masi.service.DrawingManager;
import com.iit.masi.strategy.*;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur principal de l'application
 */
public class MainController implements DrawingObserver {

    @FXML private Canvas canvas;
    @FXML private ToggleGroup shapeGroup;
    @FXML private RadioButton rectangleBtn;
    @FXML private RadioButton circleBtn;
    @FXML private RadioButton ellipseBtn;
    @FXML private RadioButton lineBtn;
    @FXML private RadioButton cube3DBtn;
    @FXML private RadioButton sphere3DBtn;
    @FXML private RadioButton cylinder3DBtn;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider strokeWidthSlider;
    @FXML private Button undoBtn;
    @FXML private Button redoBtn;
    @FXML private Button clearBtn;
    @FXML private Button saveBtn;
    @FXML private Button loadBtn;
    @FXML private Button deleteBtn;
    @FXML private ComboBox<String> logStrategyCombo;
    @FXML private Label statusLabel;
    @FXML private CheckBox showLabelsCheck;

    private DrawingManager drawingManager;
    private Logger logger;
    private GraphicsContext gc;
    
    private double startX, startY;
    private boolean isDrawing = false;
    private boolean showLabels = false;
    private ShapeFactory.ShapeType currentShapeType = ShapeFactory.ShapeType.RECTANGLE;
    private Shape selectedShape = null; // Forme sélectionnée

    @FXML
    public void initialize() {
        // Initialisation du gestionnaire de dessin
        drawingManager = DrawingManager.getInstance();
        drawingManager.addObserver(this);

        // Initialisation du logger avec stratégie console par défaut
        logger = new Logger(new ConsoleLogStrategy());
        drawingManager.setLogger(logger);

        // Initialisation du canvas
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Configuration des stratégies de log
        logStrategyCombo.getItems().addAll("Console", "Fichier", "Base de données");
        logStrategyCombo.setValue("Console");
        logStrategyCombo.setOnAction(e -> changeLogStrategy());

        // Configuration du color picker
        colorPicker.setValue(Color.BLACK);

        // Configuration du slider
        strokeWidthSlider.setValue(2.0);

        // Sélection par défaut
        rectangleBtn.setSelected(true);
        
        // Gestionnaire de touches pour supprimer la forme sélectionnée
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.DELETE || 
                event.getCode() == javafx.scene.input.KeyCode.BACK_SPACE) {
                onDeleteSelectedShape();
            }
        });

        // Log de démarrage
        logger.log("Application démarrée");

        updateButtons();
    }

    @FXML
    private void onCanvasMousePressed(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();
        
        // Vérifier si on clique sur une forme existante (clic droit ou Ctrl+clic)
        if (event.isSecondaryButtonDown() || event.isControlDown()) {
            selectedShape = drawingManager.findShapeAt(startX, startY);
            if (selectedShape != null) {
                logger.log("Forme sélectionnée: " + selectedShape.getType());
                updateStatus("Forme sélectionnée: " + selectedShape.getType() + " (Appuyez sur Suppr pour effacer)");
                redrawCanvas(); // BorderDecorator s'applique dans redrawCanvas
            } else {
                updateStatus("Aucune forme à cette position");
            }
            return;
        }
        
        isDrawing = true;
        logger.log("Début du dessin à (" + (int)startX + ", " + (int)startY + ")");
    }

    @FXML
    private void onCanvasMouseDragged(MouseEvent event) {
        if (isDrawing) {
            redrawCanvas();
            
            // Dessiner un aperçu de la forme en cours
            gc.setStroke(colorPicker.getValue());
            gc.setLineWidth(strokeWidthSlider.getValue());
            
            double endX = event.getX();
            double endY = event.getY();
            
            drawPreview(startX, startY, endX, endY);
        }
    }

    @FXML
    private void onCanvasMouseReleased(MouseEvent event) {
        if (isDrawing) {
            double endX = event.getX();
            double endY = event.getY();
            
            // Créer la forme avec la factory
            Shape shape = ShapeFactory.createShape(
                currentShapeType,
                startX, startY,
                endX, endY,
                colorPicker.getValue(),
                strokeWidthSlider.getValue()
            );
            
            // Ajouter la forme au gestionnaire COMMANDD
            drawingManager.addShape(shape);
            
            isDrawing = false;
            redrawCanvas();
            
            logger.log("Forme créée: " + shape.getType() + " à (" + (int)endX + ", " + (int)endY + ")");
            updateStatus("Forme " + shape.getType() + " ajoutée");
        }
    }

    @FXML
    private void onShapeSelected() {
        if (rectangleBtn.isSelected()) {
            currentShapeType = ShapeFactory.ShapeType.RECTANGLE;
            logger.log("Sélection: Rectangle");
        } else if (circleBtn.isSelected()) {
            currentShapeType = ShapeFactory.ShapeType.CIRCLE;
            logger.log("Sélection: Cercle");
        } else if (ellipseBtn.isSelected()) {
            currentShapeType = ShapeFactory.ShapeType.ELLIPSE;
            logger.log("Sélection: Ellipse");
        } else if (lineBtn.isSelected()) {
            currentShapeType = ShapeFactory.ShapeType.LINE;
            logger.log("Sélection: Ligne");
        } else if (cube3DBtn.isSelected()) {
            currentShapeType = ShapeFactory.ShapeType.CUBE3D;
            logger.log("Sélection: Cube 3D");
        } else if (sphere3DBtn.isSelected()) {
            currentShapeType = ShapeFactory.ShapeType.SPHERE3D;
            logger.log("Sélection: Sphère 3D");
        } else if (cylinder3DBtn.isSelected()) {
            currentShapeType = ShapeFactory.ShapeType.CYLINDER3D;
            logger.log("Sélection: Cylindre 3D");
        }
        updateStatus("Forme sélectionnée: " + currentShapeType);
    }

    @FXML
    private void onUndo() {
        drawingManager.undo();
        updateStatus("Action annulée");
    }

    @FXML
    private void onRedo() {
        drawingManager.redo();
        updateStatus("Action restaurée");
    }

    @FXML
    private void onClear() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Effacer le dessin");
        alert.setContentText("Voulez-vous vraiment effacer tout le dessin ?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            drawingManager.clear();
            redrawCanvas();
            updateStatus("Dessin effacé");
        }
    }

    @FXML
    private void onSave() {
        TextInputDialog dialog = new TextInputDialog("Mon dessin");
        dialog.setTitle("Sauvegarder le dessin");
        dialog.setHeaderText("Entrez un nom pour le dessin");
        dialog.setContentText("Nom:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try {
                DatabaseManager dbManager = DatabaseManager.getInstance();
                int drawingId = dbManager.saveDrawing(name, drawingManager.getShapes());
                logger.log("Dessin sauvegardé: " + name + " (ID: " + drawingId + ")");
                updateStatus("Dessin sauvegardé avec succès");
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Dessin sauvegardé avec l'ID: " + drawingId);
                alert.showAndWait();
            } catch (SQLException e) {
                showError("Erreur lors de la sauvegarde", e.getMessage());
            }
        });
    }

    @FXML
    private void onLoad() {
        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            List<DatabaseManager.DrawingInfo> drawings = dbManager.getAllDrawings();
            
            if (drawings.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Aucun dessin sauvegardé trouvé.");
                alert.showAndWait();
                return;
            }

            ChoiceDialog<DatabaseManager.DrawingInfo> dialog = new ChoiceDialog<>(drawings.get(0), drawings);
            dialog.setTitle("Charger un dessin");
            dialog.setHeaderText("Sélectionnez un dessin à charger");
            dialog.setContentText("Dessin:");

            Optional<DatabaseManager.DrawingInfo> result = dialog.showAndWait();
            result.ifPresent(drawingInfo -> {
                try {
                    List<Shape> shapes = dbManager.loadDrawing(drawingInfo.getId());
                    drawingManager.loadShapes(shapes);
                    logger.log("Dessin chargé: " + drawingInfo.getName() + " (" + shapes.size() + " formes)");
                    updateStatus("Dessin chargé: " + drawingInfo.getName());
                } catch (SQLException e) {
                    showError("Erreur lors du chargement", e.getMessage());
                }
            });
        } catch (SQLException e) {
            showError("Erreur lors de la récupération des dessins", e.getMessage());
        }
    }

    @FXML
    private void onDelete() {
        try {
            DatabaseManager dbManager = DatabaseManager.getInstance();
            List<DatabaseManager.DrawingInfo> drawings = dbManager.getAllDrawings();
            
            if (drawings.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Aucun dessin à supprimer.");
                alert.showAndWait();
                return;
            }

            ChoiceDialog<DatabaseManager.DrawingInfo> dialog = new ChoiceDialog<>(drawings.get(0), drawings);
            dialog.setTitle("Supprimer un dessin");
            dialog.setHeaderText("Sélectionnez un dessin à supprimer");
            dialog.setContentText("Dessin:");

            Optional<DatabaseManager.DrawingInfo> result = dialog.showAndWait();
            result.ifPresent(drawingInfo -> {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirmation");
                confirmAlert.setHeaderText("Supprimer le dessin");
                confirmAlert.setContentText("Voulez-vous vraiment supprimer \"" + drawingInfo.getName() + "\" ?");
                
                Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
                if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                    try {
                        dbManager.deleteDrawing(drawingInfo.getId());
                        logger.log("Dessin supprimé: " + drawingInfo.getName());
                        updateStatus("Dessin supprimé");
                    } catch (SQLException e) {
                        showError("Erreur lors de la suppression", e.getMessage());
                    }
                }
            });
        } catch (SQLException e) {
            showError("Erreur lors de la récupération des dessins", e.getMessage());
        }
    }

    private void changeLogStrategy() {
        String strategy = logStrategyCombo.getValue();
        LogStrategy logStrategy = switch (strategy) {
            case "Fichier" -> new FileLogStrategy();
            case "Base de données" -> new DatabaseLogStrategy();
            default -> new ConsoleLogStrategy();
        };
        
        logger.setStrategy(logStrategy);
        updateStatus("Stratégie de log changée: " + strategy);
    }

    private void drawPreview(double x1, double y1, double x2, double y2) {
        Shape previewShape = ShapeFactory.createShape(
            currentShapeType, x1, y1, x2, y2,
            colorPicker.getValue(),
            strokeWidthSlider.getValue()
        );
        previewShape.draw(gc);
    }

    private void redrawCanvas() {
        // Effacer le canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Redessiner toutes les formes — avec Decorator si labels activés
        for (Shape shape : drawingManager.getShapes()) {
            Shape toDraw = shape;
            if (showLabels) {
                // Decorator Pattern : on enveloppe la forme avec LabelDecorator
                toDraw = new LabelDecorator(shape);
            }
            toDraw.draw(gc);
        }
        
        // Mettre en surbrillance la forme sélectionnée avec BorderDecorator
        if (selectedShape != null && drawingManager.getShapes().contains(selectedShape)) {
            // Decorator Pattern : bordure de sélection
            new BorderDecorator(selectedShape, Color.RED, 2.0).draw(gc);
        }
    }

    /** Decorator Pattern : active/désactive l'affichage des noms via LabelDecorator */
    @FXML
    private void onToggleLabels() {
        showLabels = showLabelsCheck.isSelected();
        redrawCanvas();
        updateStatus(showLabels ? "Noms des formes activés" : "Noms des formes désactivés");
    }
    
    /**
     * Supprime la forme sélectionnée
     */
    @FXML
    private void onDeleteSelectedShape() {
        if (selectedShape != null) {
            drawingManager.removeShape(selectedShape);
            logger.log("Forme supprimée: " + selectedShape.getType());
            updateStatus("Forme supprimée");
            selectedShape = null;
            redrawCanvas();
        } else {
            updateStatus("Aucune forme sélectionnée");
        }
    }

    /**
     * Observer Pattern — update() appelé par DrawingManager quand le dessin change.
     * Correspond à update() dans le diagramme UML du cours.
     */
    @Override
    public void update() {
        redrawCanvas();
        updateButtons();
    }

    private void updateButtons() {
        undoBtn.setDisable(!drawingManager.canUndo());
        redoBtn.setDisable(!drawingManager.canRedo());
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        logger.log("ERREUR: " + title + " - " + message);
    }
}
