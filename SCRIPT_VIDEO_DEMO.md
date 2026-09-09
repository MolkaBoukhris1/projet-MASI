# Script pour la Vidéo Démonstrative

## Durée Estimée : 5-7 minutes

---

## Introduction (30 secondes)

**[Écran titre avec le nom du projet]**

> "Bonjour, je vous présente l'application de dessin développée dans le cadre du mini-projet MASI. Cette application JavaFX permet de dessiner des formes géométriques avec une architecture basée sur des design patterns."

**Points à mentionner :**
- Nom du projet
- Technologies utilisées (JavaFX, SQLite, Maven)
- Objectif principal

---

## Partie 1 : Présentation de l'Interface (1 minute)

**[Montrer l'interface complète]**

> "L'interface est divisée en trois parties principales :"

1. **Barre d'outils supérieure**
   - Sélection de formes (Rectangle, Cercle, Ellipse, Ligne)
   - Choix de couleur
   - Ajustement de l'épaisseur
   - Boutons d'action (Annuler, Refaire, Effacer)
   - Boutons de persistance (Sauvegarder, Charger, Supprimer)
   - Menu de journalisation

2. **Zone de dessin**
   - Canvas blanc pour dessiner

3. **Barre de statut**
   - Messages d'information

---

## Partie 2 : Démonstration des Fonctionnalités de Base (2 minutes)

### 2.1 Dessiner des Formes

**[Dessiner un rectangle]**
> "Commençons par dessiner un rectangle. Je sélectionne la forme Rectangle, je choisis une couleur bleue, et je dessine en cliquant et en glissant sur le canvas."

**[Dessiner un cercle]**
> "Maintenant un cercle rouge. Je change la couleur et je dessine."

**[Dessiner une ellipse]**
> "Une ellipse verte avec une épaisseur plus importante."

**[Dessiner une ligne]**
> "Et enfin une ligne noire."

### 2.2 Annuler et Refaire

**[Cliquer sur Annuler]**
> "Si je fais une erreur, je peux annuler la dernière action avec le bouton Annuler."

**[Cliquer sur Refaire]**
> "Et la restaurer avec le bouton Refaire."

---

## Partie 3 : Sauvegarde et Chargement (1 minute 30)

### 3.1 Sauvegarder un Dessin

**[Cliquer sur Sauvegarder]**
> "Je peux sauvegarder mon dessin dans la base de données. Je clique sur Sauvegarder, j'entre un nom, par exemple 'Dessin Démo', et je valide."

**[Montrer le message de confirmation]**
> "Un message confirme que le dessin a été sauvegardé avec succès."

### 3.2 Effacer et Charger

**[Cliquer sur Effacer tout]**
> "Je vais maintenant effacer le canvas pour montrer la fonction de chargement."

**[Confirmer l'effacement]**

**[Cliquer sur Charger]**
> "Je clique sur Charger, je sélectionne 'Dessin Démo' dans la liste, et voilà, mon dessin est restauré."

### 3.3 Supprimer un Dessin

**[Créer un nouveau dessin simple et le sauvegarder]**
> "Je crée un dessin simple que je vais supprimer."

**[Cliquer sur Supprimer]**
> "Je clique sur Supprimer, je sélectionne le dessin, je confirme, et il est supprimé de la base de données."

---

## Partie 4 : Journalisation (1 minute)

### 4.1 Stratégie Console

**[Montrer la console]**
> "L'application journalise toutes les actions. Par défaut, les logs s'affichent dans la console."

**[Effectuer quelques actions et montrer les logs]**

### 4.2 Stratégie Fichier

**[Changer pour 'Fichier']**
> "Je peux changer la stratégie de journalisation. Je sélectionne 'Fichier'."

**[Effectuer quelques actions]**

**[Ouvrir le fichier drawing_app.log]**
> "Les logs sont maintenant écrits dans le fichier drawing_app.log."

### 4.3 Stratégie Base de Données

**[Changer pour 'Base de données']**
> "Et je peux aussi journaliser dans la base de données pour une analyse ultérieure."

---

## Partie 5 : Architecture et Design Patterns (1 minute 30)

**[Montrer le code ou un diagramme]**

> "Cette application utilise six design patterns principaux :"

### 5.1 Factory Pattern
**[Montrer ShapeFactory.java]**
> "Le Factory Pattern pour créer les formes de manière flexible."

### 5.2 Command Pattern
**[Montrer CommandManager.java]**
> "Le Command Pattern pour gérer l'historique et l'annulation."

### 5.3 Strategy Pattern
**[Montrer les classes LogStrategy]**
> "Le Strategy Pattern pour les différentes stratégies de journalisation."

### 5.4 Singleton Pattern
**[Montrer DrawingManager.java]**
> "Le Singleton Pattern pour garantir une instance unique des gestionnaires."

### 5.5 Observer Pattern
**[Montrer DrawingObserver]**
> "L'Observer Pattern pour synchroniser l'interface avec le modèle."

### 5.6 Repository Pattern
**[Montrer DatabaseManager.java]**
> "Et le Repository Pattern pour abstraire la persistance des données."

---

## Partie 6 : Structure du Projet (30 secondes)

**[Montrer l'arborescence du projet]**

> "Le projet est organisé en packages clairs :"
- `model` : Les formes géométriques
- `factory` : La fabrique de formes
- `command` : Les commandes et leur gestionnaire
- `strategy` : Les stratégies de journalisation
- `observer` : L'interface d'observateur
- `service` : Le gestionnaire de dessin
- `repository` : La persistance des données
- `controller` : Le contrôleur JavaFX

---

## Conclusion (30 secondes)

**[Retour à l'interface]**

> "En résumé, cette application démontre :"
- ✅ Une architecture modulaire et extensible
- ✅ L'utilisation de design patterns reconnus
- ✅ Une séparation claire des responsabilités
- ✅ Une interface utilisateur intuitive
- ✅ Une persistance robuste des données

> "Merci de votre attention. Le code source complet et la documentation sont disponibles dans le dépôt du projet."

**[Écran de fin avec les informations du projet]**

---

## Conseils pour l'Enregistrement

### Préparation
1. Fermez toutes les applications inutiles
2. Nettoyez votre bureau
3. Préparez les dessins à l'avance si nécessaire
4. Testez l'enregistrement audio

### Pendant l'Enregistrement
1. Parlez clairement et pas trop vite
2. Faites des pauses entre les sections
3. Montrez les actions lentement
4. Zoomez sur les parties importantes du code

### Logiciels Recommandés
- **OBS Studio** (gratuit) : Pour l'enregistrement d'écran
- **Audacity** (gratuit) : Pour l'enregistrement audio séparé
- **DaVinci Resolve** (gratuit) : Pour le montage vidéo

### Format de Sortie
- **Résolution** : 1920x1080 (Full HD)
- **Format** : MP4 (H.264)
- **Framerate** : 30 fps
- **Audio** : AAC, 128 kbps

---

## Checklist Avant l'Enregistrement

- [ ] L'application compile et s'exécute sans erreur
- [ ] La base de données est vide ou contient des données de test
- [ ] Le fichier de log est vide ou supprimé
- [ ] L'interface est en plein écran
- [ ] Le micro fonctionne correctement
- [ ] Le script est lu et répété
- [ ] Les exemples de dessin sont préparés
- [ ] L'enregistreur d'écran est configuré

---

## Points Importants à Montrer

✅ **Fonctionnalités**
- Dessin de toutes les formes
- Personnalisation (couleur, épaisseur)
- Undo/Redo
- Sauvegarde/Chargement
- Suppression
- Journalisation

✅ **Code**
- Au moins 2-3 design patterns avec leur code
- Structure du projet
- Fichiers de configuration (pom.xml)

✅ **Documentation**
- README.md
- RAPPORT_DESIGN_PATTERNS.md
- Diagrammes UML (si possible)

---

**Bonne chance pour votre vidéo ! 🎬**
