# AlgoTN's website

## Motivations et utilisations
Site fait par le club Algo en 2023 principalement par Alexandre Duchesne et repris en 2025 par le nouveau bureau. Il permet d'accueillir des compétitions d'algorithmie et une base de donnée de problèmes rencontrés par les membres du club. Il a notamment servi pour une compétition pour les 30 ans d'Alisee en 2023 et un évènement du TN Event 2025. Il est utilisé en complément de l'outil [pepper](https://github.com/TN-Club-Algo/pepper) qui permet de vérifier qu'un programme est une solution valide d'un problème.

## Manuel d'utilisation
Actuellement hébergé à l'adresse https://algo.telecomnancy.net/, vous pouvez utiliser ce site en local dans un conteneur Docker utilisant les images publiées à chaque push de la branche main des projets. 
Pour faire ceci :
Assurer-vous d'avoir [Docker](https://docs.docker.com/engine/install/) d'installé sur votre machine.
Dans un terminal à la racine du projet cloné une fois connecté avec les bons accès tuto [ici](https://www.andrewhoog.com/post/authorizing-github-container-registry/), effectuez 
``` docker compose up ```
 
Puis le site sera disponible sur le port 8080 à l'adresse localhost:8080.
Pour ajouter des fichiers externes à votre site (problèmes, images) il faudra les déposer dans un dossié situé à l'adresse /etc/algotn de votre machine. 

Pour utiliser le site en direct (sans les images docker actualisées à chaque push) : 
Dans un terminal à la racine du projet cloné, effectuez :

```./gradlew build ```