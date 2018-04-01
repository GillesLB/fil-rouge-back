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

//crée automatiquement les getter / setter dans les méthodes
@Getter
@Setter

//génère des classes (lié à Lombok)
@Builder

//va générer un constructeur sans paramètre
@NoArgsConstructor

//crée un constructeur avec 1 param dans chaque champ de la classe
@AllArgsConstructor

//déclare la classe comme un entity bean (donc une classe POJO persistante)
@Entity

//permet de définir le nom de la table / du catalogue / du schéma pour le mapping de l'entity bean
@Table(name = "weapon")

//permet d'auditer une entité
@EntityListeners(AuditingEntityListener.class)
public class Weapon implements Serializable {

 // marque le champ comme une clé primaire unique
 @Id
 // génération auto et attribution (par objectDB) à l'objet
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Long id;

 // String non null et de longueur > 0
 @NotBlank
 private String type;

 @NotBlank
 private String modele;

 // fixe automatiquement un temps pour l'objet à sa création (date du jour)
 @CreationTimestamp
 // ne conserve que la date
 @Temporal(TemporalType.TIMESTAMP)
 // la requête SQL concernera la colonne "create_date"
 @Column(name = "create_date")
 private Date createDate;

 // en cas de modification, changement de la date d'origine de l'objet par la date actuelle
 @UpdateTimestamp
 @Temporal(TemporalType.TIMESTAMP)
 @Column(name = "update_date")
 private Date updateDate;

 	// @ManyToMany : l'entité esclave doit préciser un champ retour par une annotation
 	// fetch : permet de surcharger le type de récupération pour une requête particulière
 	// cascade : obligatoire quand nous appliquons la relation entre les objets, 
 	// la cascade attribue des opérations de transferts faites sur ses objets d'enfant liés
 	// mappedBy : référence le champ qui porte la relation (côté maître)
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "weapon")
    @JsonBackReference // evite la récurcivite dans le JSON
    private Set<PoliceCase> policeCase = new HashSet<> ();

}
