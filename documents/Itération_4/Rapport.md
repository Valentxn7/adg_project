# Rapport d'Itération 4

- [GitHub](https://github.com/Valentxn7/adg_project)
- [Trello](https://trello.com/b/qoNw8Geq/sae-301-adgproject)
## Informations générales

- **Nom du projet** : ADG Project (Automatic Diagram Generator)
- **Durée de l'itération** : 8 heures
- **Membres** : HEUERTZ KNORST KORBAN GROS FUCHS
- **Objectif principal** : L'objectif principal est globalement de rendre le code plus lisible et de corriger les bugs restants suite au gros merge qui a eu lieu.

---
## Fonctionnalités implémentées

### Fonctionnalité 1 : Exportation PNG
- **Responsable** : Heuertz Zacharie
- **Description** :
  - Ajout de la possibilité d'exporter le diagramme en PNG.

- **Avancement** : ?? A tester

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


**Ryan** :
J'ai commencé à résoudre un problème de conception dans le programme : les coordonnées des classes étaient stockées dans le modèle, alors qu'elles devraient l'être directement dans l'objet Classe. Cette mauvaise répartition des responsabilités causait des problèmes, notamment lors de l'utilisation du clic droit. J'ai donc entrepris un refactoring pour corriger et améliorer cet aspect. Cependant, le même problème persiste avec les flèches, mais je n'aurai pas le temps de le traiter dans cette itération.

**Valentin** :

**Zacharie**:

