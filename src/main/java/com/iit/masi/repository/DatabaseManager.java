package com.iit.masi.repository;

import com.iit.masi.model.Shape;
import javafx.scene.paint.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern - Gestionnaire de base de données
 * Repository Pattern - Gère la persistance des données
 * Une seule instance de cette classe existe dans l'application
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private static final String DB_URL = "jdbc:sqlite:drawing_app.db";
    private Connection connection;

    private DatabaseManager() {
        initializeDatabase();
    }

    /**
     * Obtient l'instance unique du DatabaseManager (Singleton)
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Initialise la base de données et crée les tables
     */
    private void initializeDatabase() {
        try {
            System.out.println("DEBUG: Initialisation de la base de donnees...");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("DEBUG: Connexion etablie");
            // Activer les clés étrangères pour SQLite
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
                System.out.println("DEBUG: Cles etrangeres activees");
            }
            createTables();
            System.out.println("DEBUG: Tables creees");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la base de données: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crée les tables nécessaires
     */
    private void createTables() throws SQLException {
        String createDrawingsTable = """
            CREATE TABLE IF NOT EXISTS drawings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                created_at TEXT NOT NULL,
                updated_at TEXT NOT NULL
            )
        """;

        String createShapesTable = """
            CREATE TABLE IF NOT EXISTS shapes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                drawing_id INTEGER NOT NULL,
                shape_data TEXT NOT NULL,
                FOREIGN KEY (drawing_id) REFERENCES drawings(id) ON DELETE CASCADE
            )
        """;

        String createLogsTable = """
            CREATE TABLE IF NOT EXISTS logs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                timestamp TEXT NOT NULL,
                message TEXT NOT NULL
            )
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createDrawingsTable);
            stmt.execute(createShapesTable);
            stmt.execute(createLogsTable);
        }
    }

    /**
     * Sauvegarde un dessin dans la base de données
     */
    public int saveDrawing(String name, List<Shape> shapes) throws SQLException {
        String timestamp = java.time.LocalDateTime.now().toString();
        
        String insertDrawing = "INSERT INTO drawings (name, created_at, updated_at) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertDrawing)) {
            pstmt.setString(1, name);
            pstmt.setString(2, timestamp);
            pstmt.setString(3, timestamp);
            pstmt.executeUpdate();

            // Récupérer l'ID avec last_insert_rowid() au lieu de getGeneratedKeys()
            String getIdQuery = "SELECT last_insert_rowid() as id";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(getIdQuery)) {
                if (rs.next()) {
                    int drawingId = rs.getInt("id");
                    System.out.println("DEBUG: Dessin insere avec ID: " + drawingId);
                    saveShapes(drawingId, shapes);
                    return drawingId;
                }
            }
        }
        return -1;
    }

    /**
     * Sauvegarde les formes d'un dessin
     */
    private void saveShapes(int drawingId, List<Shape> shapes) throws SQLException {
        System.out.println("DEBUG: Sauvegarde de " + shapes.size() + " formes pour le dessin ID " + drawingId);
        String insertShape = "INSERT INTO shapes (drawing_id, shape_data) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertShape)) {
            for (int i = 0; i < shapes.size(); i++) {
                Shape shape = shapes.get(i);
                try {
                    // Créer un objet JSON avec toutes les propriétés
                    // Utiliser Locale.US pour garantir le format avec point décimal
                    String shapeJson = String.format(java.util.Locale.US,
                        "{\"type\":\"%s\",\"startX\":%f,\"startY\":%f,\"endX\":%f,\"endY\":%f,\"strokeWidth\":%f,\"color\":\"%s\"}",
                        shape.getType(),
                        shape.getStartX(),
                        shape.getStartY(),
                        shape.getEndX(),
                        shape.getEndY(),
                        shape.getStrokeWidth(),
                        colorToHex(shape.getColor())
                    );
                    System.out.println("DEBUG: Forme " + (i+1) + " JSON: " + shapeJson);
                    pstmt.setInt(1, drawingId);
                    pstmt.setString(2, shapeJson);
                    int result = pstmt.executeUpdate();
                    System.out.println("DEBUG: Forme " + (i+1) + " inseree, resultat: " + result);
                } catch (Exception e) {
                    System.err.println("ERREUR lors de la sauvegarde de la forme " + (i+1) + ": " + e.getMessage());
                    e.printStackTrace();
                    throw new SQLException("Erreur lors de la sauvegarde de la forme " + (i+1), e);
                }
            }
        }
        System.out.println("DEBUG: Toutes les formes ont ete sauvegardees");
    }
    
    /**
     * Convertit une couleur en format hexadécimal
     */
    private String colorToHex(javafx.scene.paint.Color color) {
        return String.format("#%02X%02X%02X",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }

    /**
     * Charge un dessin depuis la base de données
     */
    public List<Shape> loadDrawing(int drawingId) throws SQLException {
        List<Shape> shapes = new ArrayList<>();
        String query = "SELECT shape_data FROM shapes WHERE drawing_id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, drawingId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String shapeJson = rs.getString("shape_data");
                Shape shape = parseShapeFromJson(shapeJson);
                if (shape != null) {
                    shapes.add(shape);
                }
            }
        }
        return shapes;
    }
    
    /**
     * Parse une forme depuis JSON
     */
    private Shape parseShapeFromJson(String json) {
        try {
            // Parser le JSON manuellement
            String type = extractJsonValue(json, "type");
            double startX = Double.parseDouble(extractJsonValue(json, "startX"));
            double startY = Double.parseDouble(extractJsonValue(json, "startY"));
            double endX = Double.parseDouble(extractJsonValue(json, "endX"));
            double endY = Double.parseDouble(extractJsonValue(json, "endY"));
            double strokeWidth = Double.parseDouble(extractJsonValue(json, "strokeWidth"));
            String colorHex = extractJsonValue(json, "color");
            
            javafx.scene.paint.Color color = javafx.scene.paint.Color.web(colorHex);
            
            return com.iit.masi.factory.ShapeFactory.createShape(
                type, startX, startY, endX, endY, color, strokeWidth
            );
        } catch (Exception e) {
            System.err.println("Erreur lors du parsing de la forme: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Extrait une valeur d'un JSON simple
     */
    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return "";
        
        startIndex += searchKey.length();
        
        // Ignorer les espaces
        while (startIndex < json.length() && json.charAt(startIndex) == ' ') {
            startIndex++;
        }
        
        // Si c'est une chaîne (commence par ")
        if (json.charAt(startIndex) == '"') {
            startIndex++;
            int endIndex = json.indexOf('"', startIndex);
            return json.substring(startIndex, endIndex);
        } else {
            // Si c'est un nombre
            int endIndex = startIndex;
            while (endIndex < json.length() && 
                   (Character.isDigit(json.charAt(endIndex)) || 
                    json.charAt(endIndex) == '.' || 
                    json.charAt(endIndex) == '-')) {
                endIndex++;
            }
            return json.substring(startIndex, endIndex);
        }
    }

    /**
     * Obtient la liste de tous les dessins
     */
    public List<DrawingInfo> getAllDrawings() throws SQLException {
        List<DrawingInfo> drawings = new ArrayList<>();
        String query = "SELECT id, name, created_at, updated_at FROM drawings ORDER BY updated_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                DrawingInfo info = new DrawingInfo(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("created_at"),
                    rs.getString("updated_at")
                );
                drawings.add(info);
            }
        }
        return drawings;
    }

    /**
     * Insère un log dans la base de données
     */
    public void insertLog(String timestamp, String message) throws SQLException {
        String insertLog = "INSERT INTO logs (timestamp, message) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertLog)) {
            pstmt.setString(1, timestamp);
            pstmt.setString(2, message);
            pstmt.executeUpdate();
        }
    }

    /**
     * Supprime un dessin
     */
    public void deleteDrawing(int drawingId) throws SQLException {
        String delete = "DELETE FROM drawings WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(delete)) {
            pstmt.setInt(1, drawingId);
            pstmt.executeUpdate();
        }
    }

    /**
     * Ferme la connexion à la base de données
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }
    }

    /**
     * Classe interne pour stocker les informations d'un dessin
     */
    public static class DrawingInfo {
        private final int id;
        private final String name;
        private final String createdAt;
        private final String updatedAt;

        public DrawingInfo(int id, String name, String createdAt, String updatedAt) {
            this.id = id;
            this.name = name;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCreatedAt() { return createdAt; }
        public String getUpdatedAt() { return updatedAt; }

        @Override
        public String toString() {
            return name + " (ID: " + id + ")";
        }
    }
}
