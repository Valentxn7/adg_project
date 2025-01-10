# Rapport d'Itération 4

- [GitHub](https://github.com/Valentxn7/adg_project)
- [Trello](https://trello.com/b/qoNw8Geq/sae-301-adgproject)
## Informations générales

- **Nom du projet** : ADG Project (Automatic Diagram Generator)
- **Durée de l'itération** : 8 heures
- **Membres** : HEUERTZ KNORST KORBAN GROS FUCHS
- **Objectif principal** : Après le gros merge, je dois faire fonctionner les flèches, ajouter des éléments graphiques, assurer le bon fonctionnement de la sauvegarde du projet et refactoriser les erreurs de conception.

---
## Fonctionnalités implémentées

### Fonctionnalité 1 : Exportation
- **Responsable** : Heuertz Zacharie
- **Description** :
  - Ajout de la possibilité d'exporter le diagramme en PNG.
  - Ajout de la possibilité d'exporter le diagramme en UML (puml).

- **Avancement** : ✅ Complétée

### Fonctionnalité 2 : Click droit
- **Responsable** : Ryan Korban
- **Description** :
  - Possiblilité de faire un click droit sur un endroit vide pour avoir accès à un menu contextuel.
  - Possibilité de faire un click droit sur un élément pour avoir accès à un menu contextuel.
  - Correction de bugs liés au click droit (mauvaise conception des coordonnée).
  - Ajout primaire des action du menu contextuel (avec des print).
- **Avancement** : ✅ Complétée

### Fonctionnalité 3 : Refactoring Des coordonnées de la cLasse CLasse
- **Responsable** : Ryan Korban
- **Description** :
  - Refactoring des coordonnées pour une meilleure gestion des coordonnées. (les coordonnées des classes étaient stoker dans le modèle alors qu'elles devraient être stockées dans l'objet Classe).
- **Avancement** : ✅ Complétée

### Fonctionnalité 4 : Sauvegarde effective
- **Responsable** : Heuertz Zacharie
- **Description** :
  - Ajout de la possibilité d'enregistrer un projet dans un .adg.
  - Ajout de la possibilité de charger un projet depuis un .adg (permet de garder les positions des classes).
- **Avancement** : ✅ Complétée


### Fonctionnalité 5 : Déplacement des classes
- **Responsable** : Thomas Fuchs
- **Description** :
  - Ajout de la possibilité de déplacer les classes dans le diagramme.
  - Ajout de la gestion des limites du pane, qui empêche les classes de sortir de la partie droite.
- **Avancement** : ✅ Complétée

### Fonctionnalité 6 : Flèches d'attributs 
- **Responsable** : Geoffrey Gros
- **Description** :
  - Ajout de la possibilité de créer des flèches entre les classes pour représenter les attributs.
  - Ajout de la Javadoc dans le modèle.
  - correction de bugs liés aux flèches.
- **Avancement** : ✅ Complétée

### Fonctionnalité 7 : Amélioration du code MVC
- **Responsable** : Valentin Knorst
- **Description** :
  - Optimisation des fonctions et des traitements inutiles
- **Avancement** : ✅ Complétée

- ### Fonctionnalité 8 : Factorisation du code MVC
- **Responsable** : Valentin Knorst
- **Description** :
  - Factorisation des fonctions en fonction servant à plusieurs utilité et donc la réduction de la taille du modele
- **Avancement** : ✅ Complétée (mais se travail ce fait en continue)

## Conclusion

- Plan pour l'itération suivante :
  - Corriger les bugs de l'affichage de flèches
  - Correction de l'affichage des vues Classes
  - Finir les fonctionnalités du menuBar
  - Gérer l'aspect graphique des classes
  - Rendre cliquable et utilisable le TreeView
  - Continuer a corriger les coordonnées qui sont mal stoquées
  - Améliorer l'analyser pour récupérer les sous-types des classes
  - implémentation de la fonctionnalité squelette des classes 
  - Finir les fonctionnalités attendue sur la partie graphique 

## Éléments dont nous sommes fiers :

**Ryan** :
J'ai commencé à résoudre un problème de conception dans le programme : les coordonnées des classes étaient stockées dans le modèle, alors qu'elles devraient l'être directement dans l'objet Classe. Cette mauvaise répartition des responsabilités causait des problèmes, notamment lors de l'utilisation du clic droit. J'ai donc entrepris un refactoring pour corriger et améliorer cet aspect. Cependant, le même problème persiste avec les flèches, mais je n'aurai pas le temps de le traiter dans cette itération.

**Valentin** : 
Le modèle MVC prend une forme réellement appréciable et le code commence à bien se mélanger aux autres branches.
Ce travail bien que très chronophage nous assure l'ensemble de nos fonctionnalités grâce à notre communication.

**Zacharie**:
Après de grosses phases de fix dans le model, j'ai pu implémenter les controllers pour l'exportation et le système de sauvegarde. Malgré quelques difficultés de conception, le système est désormais fonctionnel et permet d'enregistrer le projet sans passer par une simple sérialisation mais par un vrai fichier de sauvegarde.

**Geoffrey** : comportement dynamique des flèches, indépendamment de la position des classes, les flèches s'adaptent.
