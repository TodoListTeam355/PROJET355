# Mon Application de Tâches

Ceci est un projet d'application Android simple, développée avec Kotlin et Jetpack Compose. L'application permet de gérer des listes de tâches personnelles classées par catégories.

## 📋 Fonctionnalités

*   **Vue des Catégories :** Affiche une grille des différentes catégories de tâches (Travail, Musique, Maison, etc.) avec le nombre de tâches pour chacune.
*   **Détail des Tâches :** En cliquant sur une catégorie, l'utilisateur accède à un écran de détail qui liste toutes les tâches associées.
*   **Organisation des Tâches :** Les tâches sont regroupées par statut : "En retard", "Aujourd'hui" et "Terminé".
*   **Gestion de l'État :** Il est possible de marquer une tâche comme terminée via une case à cocher.
*   **Interface Moderne :** L'interface utilisateur est construite avec Jetpack Compose et Material Design 3.

## 🛠️ Technologies et Bibliothèques

*   **Langage :** [Kotlin](https://kotlinlang.org/)
*   **Interface Utilisateur :** [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **Architecture :** Basée sur les Activities et les Composables
*   **Bibliothèques Principales :**
    *   Material 3 pour les composants d'interface.
    *   Jetpack Navigation (Compose) pour la navigation entre les écrans.
    *   ViewModel pour la gestion des états (non implémenté pour le moment).

## 🚀 Comment Lancer le Projet

1.  **Cloner le dépôt :**
    ```sh
    git clone <URL_DU_DEPOT_GITHUB>
    ```
2.  **Ouvrir dans Android Studio :**
    *   Lancez Android Studio.
    *   Sélectionnez "Open an existing project".
    *   Naviguez jusqu'au dossier du projet cloné et ouvrez-le.

3.  **Lancer l'application :**
    *   Laissez Gradle synchroniser le projet.
    *   Sélectionnez un émulateur ou connectez un appareil physique.
    *   Cliquez sur le bouton "Run" (▶️) pour compiler et lancer l'application.
