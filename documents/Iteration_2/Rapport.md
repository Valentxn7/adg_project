# Rapport d'Itération 1

- [GitHub](https://github.com/Valentxn7/adg_project)
- [Trello](https://trello.com/b/qoNw8Geq/sae-301-adgproject)
## Informations générales

- **Nom du projet** : ADG Project (Automatic Diagram Generator)
- **Durée de l'itération** : 8 heures
- **Membres** : HEUERTZ KNORST KORBAN GROS FUCHS
- **Objectif principal** : L'objectif principal de l'itération était de développer et stabiliser les fonctionnalités essentielles de l'application ADG, notamment le drag & drop, la gestion des projets, et l'exploration des fichiers, afin de poser les bases pour une utilisation intuitive et efficace de l'outil.

--
## Fonctionnalités implémentées

### Fonctionnalité 1 : Drag and Drop (Debug et amélioration)
- **Responsable** : Ryan Korban
- **Description** :
  - Finalisation du drag & drop :
    - Ajout de la possibilité de glisser-déposer plusieurs fichiers .class. (FUCHS)
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
- **Avancement** : :✅ Complétée
- **Exemple d'utilisation** :
  Ouvrir l'application et cliquer sur le bouton Ouvrir



### Fonctionnalité 7 : Création d'un dossier d'application ADG
- **Responsable** : Valentin
- **Description** :
  - Un dossier d'application permettant de stocker les projets créer sur l'application ADG et les fichers data en json pour les autres fonctionnalité.
  - Ce dossier se situe dans le dossier de l'Utilisateur
- **Avancement** : ✅ Complétée



### Fonctionnalité 8 : Créer un nouveau projet
- **Responsable** : Valentin
- **Description** :
  - Création d'un nouveau projet en .adg dès qu'un utilisateur clique sur "Nouveau" dans le dossier d'application afin de sauvegarder son avancement.
- **Avancement** : ✅ Complétée



### Fonctionnalité 9 : Créer une TreeView des dossiers récemment ouvert
- **Responsable** : Valentin
- **Description** :
  - Une treeView permettant à l'utilisateur de revenir à ses anciens projets ouverts, stocké dans data.json et limité à 10 emplacements de retour.
- **Avancement** : ✅ Complétée



### Fonctionnalité 9 :Réalisation des flèches (branche flèche)
- **Responsable** :  Thomas Fuchs et Geoffrey Gros
- **Description** :
  - Conception et intégration des éléments visuels de la flèche
  - Vérification des collisions avec les classes
  - Flèches dynamiques
  - Implémentation des flèches dans le modèle
- **Avancement** : ✅ Complétée



## Conclusion

- Résumé : L'itération 1 a permis d'accomplir plusieurs avancées significatives dans le projet ADG. Les fonctionnalités essentielles, comme le Drag and Drop, l’explorateur de fichiers, la création de projets et la gestion des flèches, ont été finalisées.
- Plan pour l'itération suivante :
    - Faire fonctionner les flèches sur le main : Intégrer les flèches dynamiques à la branche principale et assurer leur bon fonctionnement.
    - Refactoriser le code : Simplifier et améliorer la lisibilité du code pour réduire la complexité, faciliter la maintenance et éviter les erreurs.
    - Affichage graphique des classes après le drag & drop : Passer de l’affichage en console à une visualisation graphique intuitive pour l’utilisateur.

## Éléments dont nous sommes fiers :

**Ryan** :

Le drag & drop a été un gros défi pour moi, notamment à cause de la gestion des packages qui nécessitait un nommage différent pour le chemin absolu et le nom des classes. J’ai testé plusieurs approches pour résoudre ce problème, mais une erreur persistait.
Cette erreur était particulièrement compliquée à gérer parce qu’elle n’était pas levée comme une exception classique, c'etait une Error et j’attrapais uniquement les exceptions dans mon catch, ce qui m’a ralenti pendant un moment vu que je me suis retrouvé bloqué.
Finalement, j’ai mis en place une boucle dans un try-catch qui essaie toutes les combinaisons possibles et j'attrape désormais les errors aussi. Grâce à cette solution, on peut désormais glisser-déposer n’importe quel fichier .class, peu importe le nombre de packages.
Cependant, il y a un cas où cela ne fonctionne pas : lorsque le fichier .class est dans le mauvais package. Dans ce cas, c’est normal que ça échoue, et je l’indique clairement en lançant une exception."

