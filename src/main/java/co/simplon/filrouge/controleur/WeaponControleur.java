package co.simplon.filrouge.controleur;

import co.simplon.filrouge.modele.Weapon;
import co.simplon.filrouge.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//fusion de @Controller et @ResponseBody
//@Controller est là pour recevoir, transformer, et retourner les données au client,
//pas pour du traitement métier, ni pour agir avec la BDD. 
//renvoie l'objet et ses données ecrites en HTTP en JSON ou XML.
@RestController

//n'autorise les requêtes que d'une origine spécifique 
//et permet de se connecter au localhost
@CrossOrigin

//crée la base de l'URL
@RequestMapping("/api")
public class WeaponControleur {

	// permet de faire l’injection de dépendances entre les beans de l’application,
	// il suffit juste d’annoter un constructeur ou une méthode avec cette dernière.
  @Autowired
  WeaponRepository weaponRepository;

  // pour voir toutes les armes
  @GetMapping("/weapon")
  public List<Weapon> getAllWeapon() {
      return weaponRepository.findAll();
  }

  // Creer une nouvelle Weapon
  @PostMapping("/weapon")
  // @Valid : l'annotation d'un objet permet au framework une validation pour traiter l'objet annoté
  // @RequestBody (and @ResponseBody) : utilisées pour lier la requête HTTP / réponse du body 
  // avec un objet de domaine des paramètre de méthode ou du type de retour
  // cette annotation utilise des convertisseurs de Message HTTP pour adapter la demande / réponse HTTP aux objets de domaine.
  public Weapon createWeapon(@Valid @RequestBody Weapon weapon) {
      return weaponRepository.save ( weapon );
  }

  // Consulter une seule Weapon
  @GetMapping("/weapon/{id}")
  // @PathVariable : annotation which indicates that a method parameter should be bound to a URI template variable.
  // Supported for RequestMapping annotated handler methods in Servlet environments
    public ResponseEntity<Weapon> getWeaponById(@PathVariable(value = "id") Long weaponId) {
        Weapon weapon = weaponRepository.findOne (weaponId);
        if(weapon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(weapon);
    }

    // Mise à jour d'une Weapon
    @PutMapping("/weapon/{id}")
    public ResponseEntity<Weapon> updateWeapon(@PathVariable(value = "id") Long weaponId,
                                                  @Valid @RequestBody Weapon weaponDetail) {
        Weapon weapon = weaponRepository.findOne(weaponId);
        if(weapon == null) {
            return ResponseEntity.notFound().build();
        }
        weapon.setModele (weaponDetail.getModele());
        weapon.setType (weaponDetail.getType());

        Weapon updateWeapon = weaponRepository.save(weapon);
        return ResponseEntity.ok(updateWeapon);
    }


    // Efface une Weapon
    @DeleteMapping("/weapon/{id}")
    public ResponseEntity<Weapon> deleteWeapon(@PathVariable(value = "id") Long weaponId) {
        Weapon weapon = weaponRepository.findOne(weaponId);
        if(weapon == null) {
            return ResponseEntity.notFound().build();
        }

        weaponRepository.delete(weapon);
        return ResponseEntity.ok().build();
    }
}