# Rapport d'Itération 1

- [GitHub](https://github.com/Valentxn7/adg_project)
- [Trello](https://trello.com/b/qoNw8Geq/sae-301-adgproject)
## Informations générales

- **Nom du projet** : ADG Project (Automatic Diagram Generator)
- **Durée de l'itération** : 8 heures
- **Membres** : HEUERTZ KNORST KORBAN GROS FUCHS
- **Objectif principal** : [Décrire brièvement l'objectif principal de l'itération]

--
## Fonctionnalités implémentées

### Fonctionnalité 1 : Drag and Drop (Debug et amélioration)
- **Responsable** : Ryan Korban
- **Description** :
  - Finalisation du drag & drop :
    - Ajout de la possibilité de glisser-déposer plusieurs fichiers .class.
    - Correction des bugs liés à l’arborescence sur macOS.
    - Résolution du problème empêchant le drag & drop des fichiers .class qui ont un package.
  - Affichage des diagrammes :
    - Intégration de l’affichage des diagrammes directement dans la console. (Pour l'instant en attendant que ça soit graphique)
  - Conversion des chemins absolus :
    - Transformation d’un chemin absolu en objet Class<?> pour permettre l’analyse via l’outil Analyser.
- **Avancement** : ✅ Complétée

### Fonctionnalité 6 : Explorateur de dossier et de fichiers
- **Responsable** : Valentin
- **Description** :
  - Cette fonctionnalité permet d'ouvrir deux explorateurs différents afin d'ouvrir et de traiter les données sélectionnées.
  - Un dossier peut être entièrement lut pour être affiché en Treemap.
- **Avancement** : :white_check_mark: Complétée
- **Exemple d'utilisation** :
  Ouvrir l'application et cliquer sur le bouton Ouvrir



### Fonctionnalité 7 : Création d'un dossier d'application ADG
- **Responsable** : Valentin
- **Description** :
  - Un dossier d'application permettant de stocker les projets créer sur l'application ADG et les fichers data en json pour les autres fonctionnalité.
  - Ce dossier se situe dans le dossier de l'Utilisateur
- **Avancement** : :white_check_mark: Complétée



### Fonctionnalité 8 : Créer un nouveau projet
- **Responsable** : Valentin
- **Description** :
  - Création d'un nouveau projet en .adg dès qu'un utilisateur clique sur "Nouveau" dans le dossier d'application afin de sauvegarder son avancement.
- **Avancement** : :white_check_mark: Complétée



### Fonctionnalité 9 : Créer une TreeView des dossiers récemment ouvert
- **Responsable** : Valentin
- **Description** :
  - Une treeView permettant à l'utilisateur de revenir à ses anciens projets ouverts, stocké dans data.json et limité à 10 emplacements de retour.
- **Avancement** : :white_check_mark: Complétée


## Conclusion

- Résumé : [Résumer brièvement ce qui a été accompli dans l'itération]
- Plan pour l'itération suivante :
    - [Lister les fonctionnalités ou améliorations prévues]