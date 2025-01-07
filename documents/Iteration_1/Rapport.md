# Rapport d'Itération 1

- [GitHub](https://github.com/Valentxn7/adg_project)
- [Trello](https://trello.com/b/qoNw8Geq/sae-301-adgproject)
## Informations générales

- **Nom du projet** : ADG Project (Automatic Diagram Generator)
- **Durée de l'itération** : 8 heures
- **Membres** : HEUERTZ KNORST KORBAN GROS FUCHS
- **Objectif principal** : L'objectif principal de l'itération était de développer les fonctionnalités de base de l'application ADG, notamment la conception de l'interface graphique, la gestion du drag and drop, et la sauvegarde des données sous le format .adg, afin de poser les fondations pour les itérations suivantes qui se concentreront sur l'affichage des classes et l'amélioration de l'interactivité.
---
## Fonctionnalités implémentées

### Fonctionnalité 1 : Base de l’interface graphique
- **Responsable** : Valentin Knorst
- **Description** :
    - Conception complète de la maquette de l'interface graphique, création de cette dernière :
    sa page d’accueil, ses inputs windows et sa transition vers son état d’affichage diagramme avec un état vide de celui- ci.

- **Avancement** : ✅ Complétée


### Fonctionnalité 2 : Sauvegarde en .adg
- **Responsable** : Zacharie Heuertz
- **Description** :
    - Analyser un fichier (.java) et retranscrire les informations dans la classe “Classe” ainsi que retranscrire les données de la dite classe dans un fichier (.adg) sous forme (en premier lieu) de script PlantUml. (branche “analyser”)
- **Avancement** : ✅ Complétée

### Fonctionnalité 3 : Drag and Drop
- **Responsable** : Thomas Fuchs, Ryan Korban
- **Description** : Drag and Drop
    - Drag and drop un fichiers .class dans un StackPane dans la partie droite de l’interface de la branche ConceptionPartieGraphique. Stockage du chemin absolue du fichier dans une ArrayList<String>. (Thomas Fuchs)
    - Lors de l'opération, le chemin absolu des fichiers est affiché dans la console et les coordonnées exactes de l'endroit où les fichiers ont été déposés sont sauvegardées pour une utilisation ultérieure. (Ryan Korban)
- **Avancement** : ✅ Complétée

### Fonctionnalité 4 : Affichage d’une classe
- **Responsable** : Geoffrey Gros
- **Description** :
    - Affiche une classe dans la partie prévue du logiciel. Lors de l’affichage, les différents niveaux de protection sont affichés par un code couleur afin de les différencier plus simplement.
- **Avancement** : ⛔ Non Complétée (itération suivante)

- **Plan pour l'itération suivante :**
  - Drag & Drop : Ajout du glisser-déposer multiple pour fichiers .class, correction des bugs macOS et des fichiers avec packages
  - Explorateur de Dossiers et Fichiers
  - Nouveau Projets : Création d’un nouveau projet en .adg
  - Flèches Dynamiques 


### Element dont nous sommes fiers


**Ryan Korban** :

Afficher dans la console les coordonnées exactes de l’endroit où les fichiers sont déposés a été intéressant. Les coordonnées récupérées par défaut ne correspondent pas à la StackPane où le fichier est réellement glissé-déposé. J’ai dû effectuer des calculs pour convertir ces coordonnées en celles correspondant à la StackPane. Cela m’a permis de mieux comprendre comment gérer les événements graphiques en fonction du contexte exact.