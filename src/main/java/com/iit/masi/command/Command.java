package com.iit.masi.command;

/**
 * Command Pattern - Interface pour toutes les commandes
 * Ce pattern encapsule une requête comme un objet, permettant ainsi
 * de paramétrer les clients avec différentes requêtes, de mettre en file
 * d'attente ou de journaliser les requêtes, et de supporter l'annulation
 */
public interface Command {
    /**
     * Exécute la commande
     */
    void execute();

    /**
     * Annule la commande
     */
    void undo();

    /**
     * Obtient la description de la commande
     */
    String getDescription();
}
