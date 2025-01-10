# Rapport d'Itération 5

- [GitHub](https://github.com/Valentxn7/adg_project)
- [Trello](https://trello.com/b/qoNw8Geq/sae-301-adgproject)
## Informations générales

- **Nom du projet** : ADG Project (Automatic Diagram Generator)
- **Durée de l'itération** : 8 heures
- **Membres** : HEUERTZ KNORST KORBAN GROS FUCHS
- **Objectif principal** : Finaliser le projet en ajoutant les dernières fonctionnalités et en corrigeant les derniers bugs.

---
## Fonctionnalités implémentées

### Fonctionnalité 1 : Gestion de l'état d'affichage des Classes
- **Responsable** : Ryan Korban
- **Description** :
  - Ajout de la possibilité de cacher/afficher les attributs.
  - Ajout de la possibilité de cacher/afficher les méthodes.
  - Ajout de la possibilité de cacher/afficher toutes les dépendances.
  - Ajout de la possibilité de cacher/afficher les dépendence d'une classe précise.
  - Ajout de la possibilité de cacher/afficher les attributs d'une classe précise.
  - Ajout de la possibilité de cacher/afficher les méthodes d'une classe précise.
- **Avancement** : ✅ Complétée


### Fonctionnalité 2 : Export des diagrammes en squelette Java et bouton quitter
- **Responsable** : Thomas Fuchs
- **Description** :
  -ajout de la possibilité d'exporter le squelette Java du diagramme
  -ajout d'un bouton quitter 
- **Avancement** : ✅ Complétée

### Fonctionnalité 3 : Refactoring de la partie graphique des flèches
- **Responsable** : Gros Geoffrey
- **Description** :
  - Ajout de la Classe VuePointe avec PointePleine et PointeCreuse
  - Refonte du code de VueFleche VuePointe
- **Avancement** : ✅ Complétée
- 
### Fonctionnalité 4 : Correction des bugs liée à l'affichage de la classe
- **Responsable** : Gros Geoffrey
- **Description** :
  - Modification de la classe Classe afin d'améliorer le texte de l'affichage
  - Modification de la classe VueClasse afin d'améliorer le texte de l'affichage
- **Avancement** : ✅ Complétée
- 
### Fonctionnalité 5 : Renforcement de l'écriture pour UML et ADG
- **Responsable** : HEUERTZ Zacharie
- **Description** :
  - Modification de la classe Load et Model pour renforcer l'écriture et le chargement des projets
- **Avancement** : ✅ Complétée

### Fonctionnalité 6 : Mises à jour Analyser
- **Responsable** : HEUERTZ Zacharie
- **Description** :
- Modification de la classe Analyser afin de récupérer les informations :
  - Des noms des parametres (uniquement si .class compilés avec -parameters)
  - Le type de la classe (interface ou classe)
- **Avancement** : ✅ Complétée
- 
### Fonctionnalité 7 : Ajouts des icones et logo
- **Responsable** : HEUERTZ Zacharie
- **Description** :
- Ajout des icones pour les parametres d'affichage (afficher / cacher)
- **Avancement** : ✅ Complétée

### Fonctionnalité 8 : Amélioration de du code du projet (conception)
- **Responsable** : Ryan Korban
- **Description** :
  - Ajustement de la conception du projet car certaines partie de respectent pas la logique MVC
    - Creer un objet fleche au lieu de tout stoker dans la vue fleche
  - (Refactoring non fini  par manque de temps (pas push))
    - Ajout d'arraylist de fleche dans la Classe Classe et mise en place d'un MVC valide pour VueClasse et VueFleche pour que ces vues s'occupent uniquement de l'affichage et non de la gestion des données et meme chose pour le model
    
- **Avancement** : ✅ Complétée

## Conclusion

Malgré de nombreuses fonctionnalités implémentées et un projet qui semble réussi, le groupe ressent un sentiment d'imcomplet dans le projet. Le code n'est pas aussi propre que l'on aurait voulu, et les problèmes de communication et de gestion du temps ont été un frein à l'avancement du projet. Cependant, le groupe est fier de ce qu'il a accompli et est plutôt satisfait du résultat final.

  
## Éléments dont nous sommes fiers :

**Ryan** :
Refactoring du code pour permettre le clic droit sur les classes, corrigeant ainsi partiellement une conception initiale inadéquate.

**Valentin** :

**Zacharie**:
Les fonctionnalités de l'itération 5 sont en bonne voie, les dernières fonctionnalités sont en cours de finalisation. Les derniers bugs sont en cours de correction.

**Geoffrey** : L'affichage de la classe fonctionne enfin parfaitement. Les Types sont claires et les flèches sont bien placées.
