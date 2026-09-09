package com.iit.masi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application de dessin
 * 
 * Cette application utilise plusieurs design patterns:
 * - Factory Pattern: pour créer les formes géométriques
 * - Command Pattern: pour gérer les actions (undo/redo)
 * - Strategy Pattern: pour les stratégies de journalisation
 * - Singleton Pattern: pour les gestionnaires (DrawingManager, DatabaseManager)
 * - Observer Pattern: pour notifier les changements du dessin
 * - Repository Pattern: pour la persistance des données
 */
public class DrawingApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/iit/masi/view/main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Application de Dessin - MASI Mini Projet");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Fermer la connexion à la base de données lors de la fermeture
        com.iit.masi.repository.DatabaseManager.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
