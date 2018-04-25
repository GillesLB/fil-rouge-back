# **Projet Fil Rouge** #


<p>
  <img src=".\src\assets\images\titre_fil_rouge.png"/>
</p>
Conçu et réalisé par l'équipe GGSF (Gérôme Gilles Stéphan Frédéric)

----------

*Partie Back*

**Pré-requis :**

	JDK : 1.8.0_61  
	Apache Maven : 3.5.2  
	SQL server : 5.6  

**Mode opératoire :**

1 - Installer JDK dans un IDE (par exemple, Eclipse) : aller sur http://www.oracle.com/technetwork/java, cliquer sur J SE et suivre les instructions  
2 - Installer Apache Maven : ouvrir Eclipse, puis aller sur Help > Install New Software  
Ensuite, name = **m2e** et location = **http://download.eclipse.org/technology/m2e/releases** et suivez les instructions  
3 - Mettre à jour les dépendances de Maven (faire un clic droit sur le projet, puis Maven > Update Project)  
4 - Installer Spring Boot : aller sur Help > Eclipse Marketplace et saisir sts (Spring Tool Service) et suivez les instructions  
5 - Vérifier si des mises à jour sont disponibles pour les autres composants  
6 - Importer le projet "Fil rouge" sous l'IDE (file > Open project et choisir fil-rouge-back)  
7 - Modifier le fichier "application.properties", afin de :

* saisir le localhost (ici, 3306)  
* entrer les identifiants de connection à la base de données  

Pour démarrer l'application, il suffira de lancer, sur la classe Main, un "Run As", puis de choisir "Spring boot app".  
Il est possible de se connecter, dans un navigateur, sur http://localhost:8080, pour vérifier les données 
(en format JSON), les connexions, ...  
Par exemple, le lien http://localhost:8080/api/weapon affichera le contenu de la table weapon.

----------

Personnellement, j'ai particulièrement travaillé sur la partie UML, design (mokup, CSS) et "arme" du projet  


**Créer la partie back de l'application :**  

- préparer les diagrammes UML : use case, diagramme des classes, diagramme d'activité, diagramme de base ...  
    ¤ USE case du projet :  
<p>
  <img src=".\src\assets\images\use_case.jpg"/>
</p>  
	    ¤ Diagramme d'activité :  
<p>
  <img src=".\src\assets\images\diagramme_activite.jpg"/>
</p>  
	    ¤ Diagramme des classes :  
<p>
  <img src=".\src\assets\images\LesExperts.jpg"/>
</p>  

- faire quelques maquettes d'écrans  

	    ¤ mokup du projet :  
<p>
  <img src=".\src\assets\images\fil_rouge_mokup.png"/>
</p>  

- créer les bases de données (et les remplir) :  

	    ¤ diagramme de bases :  
<p>
  <img src=".\src\assets\images\schema_BDD.png"/>  

</p>  

	¤ schema.sql : possède le schéma pour créer les différentes tables de la base  

	¤ data.sql : contient un jeu de données pour remplir les tables  



- créer les classes java, en lien avec Spring et Hiberbate  

=> modèle : cette classe permet de créer et nommer une table, ainsi que ses champs et leurs caractéristiques (nom, non null, clé primaire, ...)  

    ~ Weapon.java : permet de fixer le nom de la table et des colonnes, ainsi que les  
      caractéristiques spécifiques

=> DAO : interface, qui va permettre ici de supprimer le lien qu'un objet peut avoir entre 2 tables (mais sans toucher à l'objet même)  

    ~ WeaponLinkDAO : interface qui va supprimer le lien existant entre une arme et  
      une affaire

=> contrôleur : s'occupe de récupérer les données et de les renvoyer, après traitement, au client. Il crée aunsi une URI et agit au niveau du CRUD  

    ~ WeaponController : crée une URI et contient les méthodes pour créer, modifier ou  
      effacer une arme

=> repository : interface permettant d'activer les méthodes du CRUD pour une table donnée  
  
    ~ WeaponRepository : interface qui active, pour la table weapon, de créer (C),    
     lire (R), mettre à jour (U) ou effacer (D) une arme.


=> JDBC : instruction SQL déjà compilée (plus sécurisée), pour ici supprimer le lien qu'un objet peut posseder entre 2 tables  

    ~ JdbcWeaponDAO : permet de supprimer la table de jointure entre arme et affaire,  
      pour une arme (id) précise  

=> service : interface pour effacer le lien entre une arme et une affaire  

    ~ WeaponLinkService : implémente le JdbcWeaponDAO (où elle sera @Override) pour pouvoir effacer  
      un lien entre 2 tables

----------

On va trouver différents types de requête SQL :  

- création de la base :  
     ```CREATE DATABASE IF NOT EXISTS `police_app` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; 
USE `police_app`;```

- création des tables (ici : weapon) :  
	```CREATE TABLE IF NOT EXISTS `weapon` (`id` bigint(20) NOT NULL AUTO_INCREMENT,`create_date` datetime DEFAULT NULL, `modele` varchar(255) NOT NULL, `type` varchar(255) NOT NULL, `update_date` datetime DEFAULT NULL, PRIMARY KEY (`id`)  
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;```  
    
 - récupérer la marque, la couleur et la plaque des voitures liées à une affaire dans la table police_case_vehicle :  
 	```SELECT name, marque, color, license_plate FROM police_app.police_case INNER JOIN police_app.vehicule ON police_case.id = vehicule.id;```  
	
- insertion de données (test) dans "weapon" :  
	```INSERT IGNORE INTO `weapon` (`id`, `create_date`, `type`, `modele`, `update_date`) VALUES (1,  "2017-09-25 08:37:34", 'Essai', 'Ca marche', "2017-12-22 05:44:55"), (2, "2017-09-25 08:37:34", 'Paille', 'Jaune et bleue', "2017-12-22 05:44:55"), ...```

- mise à jour d'un modèle d'arme (ici, pour l'id 2) dans la table weapon :  
    ```UPDATE police_app.weapon SET modele = "Bombe à eau" WHERE id = 2;```  

- effacer des liens entre des tables ou des données :  
    ```DELETE FROM `police_case_weapon` WHERE police_case_id = ? AND weapon_id = ?;```  

----------

