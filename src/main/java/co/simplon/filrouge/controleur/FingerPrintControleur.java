package co.simplon.filrouge.controleur;


import co.simplon.filrouge.modele.FingerPrint;
import co.simplon.filrouge.repository.FingerPrintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// fusion de @Controller et @ResponseBody
// @Controller est là pour recevoir, transformer, et retourner les données au client,
// pas pour du traitement métier, ni pour agir avec la BDD. 
// renvoie l'objet et ses données ecrites en HTTP en JSON ou XML.
@RestController

// ...
@CrossOrigin

// crée la base de l'URL
@RequestMapping("/api")
public class FingerPrintControleur {
	
	// permet de faire l’injection de dépendances entre les beans de l’application,
	// il suffit juste d’annoter un constructeur ou une méthode avec cette dernière.
    @Autowired
    FingerPrintRepository fingerPrintRepository;

    // Consulter toutes les FingerPrint
    @GetMapping("/fingerprint")
    public List<FingerPrint> getAllFingerPrint() {
        return fingerPrintRepository.findAll();
    }

    // Creer une nouvelle FingerPrint
    @PostMapping("/fingerprint")
    
    // @Valid : annotation on an object is an indication to the validation framework to process the annotated object
    // @RequestBody (and @ResponseBody) : annotations are used to bind the HTTP request/response body 
    // with a domain object in method parameter or return type. 
    // Behind the scenes, these annotation uses HTTP Message converters to convert the body of HTTP request/response to domain objects.
    public FingerPrint createFingerPrint(@Valid @RequestBody FingerPrint fingerPrint) {
        return fingerPrintRepository.save ( fingerPrint );
    }

    // Consulter une seule FingerPrint
    @GetMapping("/fingerprint/{id}")
    
    // @PathVariable : annotation which indicates that a method parameter should be bound to a URI template variable.
    // Supported for RequestMapping annotated handler methods in Servlet environments
    public ResponseEntity<FingerPrint> getFingerPrintById(@PathVariable(value = "id") Long fingerPrintId) {
        FingerPrint fingerPrint = fingerPrintRepository.findOne (fingerPrintId);
        if(fingerPrint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(fingerPrint);
    }

    // Mise à jour d'une FingerPrint
    @PutMapping("/fingerprint/{id}")
    public ResponseEntity<FingerPrint> updateFingerPrint(@PathVariable(value = "id") Long fingerPrintId,
                                                  @Valid @RequestBody FingerPrint fingerPrintDetail) {
        FingerPrint fingerPrint = fingerPrintRepository.findOne(fingerPrintId);
        if(fingerPrint == null) {
            return ResponseEntity.notFound().build();
        }
        fingerPrint.setFingerprint (fingerPrintDetail.getFingerprint ());
        fingerPrint.setPeople(fingerPrintDetail.getPeople());

        FingerPrint updatedFingerPrint = fingerPrintRepository.save(fingerPrint);
        return ResponseEntity.ok(updatedFingerPrint);
    }

    // Efface une FingerPrint
    @DeleteMapping("/fingerprint/{id}")
    public ResponseEntity<FingerPrint> deleteFingerPrint(@PathVariable(value = "id") Long fingerPrintId) {
        FingerPrint fingerPrint = fingerPrintRepository.findOne(fingerPrintId);
        if(fingerPrint == null) {
            return ResponseEntity.notFound().build();
        }

        fingerPrintRepository.delete(fingerPrint);
        return ResponseEntity.ok().build();
    }
}