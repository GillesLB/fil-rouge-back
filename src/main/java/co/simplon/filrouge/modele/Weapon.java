package co.simplon.filrouge.modele;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter //crée automatiquement les getter / setter dans les méthodes
@Setter

@NoArgsConstructor //va générer un constructeur sans paramètre

@AllArgsConstructor //crée un constructeur avec 1 param dans chaque champ de la classe

@Entity //déclare la classe comme un entity bean (donc une classe POJO persistante)

/* permet de définir le nom de la table / du catalogue /
*  du schéma pour le mapping de l'entity bean */
@Table(name = "weapon")

@EntityListeners(AuditingEntityListener.class) //permet d'auditer une entité
public class Weapon implements Serializable {

 @Id // marque le champ comme une clé primaire unique

 @GeneratedValue(strategy = GenerationType.AUTO)  // génération auto et attribution (par objectDB) à l'objet
 private Long id;

 @NotBlank // String non null et de longueur > 0
 private String type;

 @NotBlank
 private String modele;

 @CreationTimestamp // fixe automatiquement un temps pour l'objet à sa création (date du jour)

 @Temporal(TemporalType.TIMESTAMP) // ne conserve que la date

 @Column(name = "create_date") // la requête SQL concernera la colonne "create_date"
 private Date createDate;

 /* en cas de modification, changement de la date
 *  d'origine de l'objet par la date actuelle */
 @UpdateTimestamp
 @Temporal(TemporalType.TIMESTAMP)
 @Column(name = "update_date")
 private Date updateDate;

 	/* @ManyToMany : l'entité esclave doit préciser un champ retour par une annotation
 	*  fetch : permet de surcharger le type de récupération pour une requête particulière
 	*  cascade : obligatoire quand nous appliquons la relation entre les objets, 
 	*  la cascade attribue des opérations de transferts faites sur ses objets d'enfant liés
 	*  mappedBy : référence le champ qui porte la relation (côté maître) */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "weapon")

    @JsonBackReference // évite la récurcivite dans le JSON
    private Set<PoliceCase> policeCase = new HashSet<> ();

}
