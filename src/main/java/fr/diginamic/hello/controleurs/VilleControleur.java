package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private Validator validator;

    @Autowired
    private VilleDao villeDao;

    @Autowired
    private VilleService villeService;


    @GetMapping
    public List<Ville> getVilles() {
        return villeDao.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) {
        Ville villeATrouver = villeDao.findById(id);
        if (villeATrouver != null) {
            return ResponseEntity.ok(villeATrouver);
        } else {
            return ResponseEntity.status(404).body("ville introuvable");
        }
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getVilleByNom(@PathVariable String nom) {
        Ville villeATrouver = villeDao.findByNom(nom);
        if (villeATrouver != null) {
            return ResponseEntity.ok(villeATrouver);
        } else {
            return ResponseEntity.status(404).body("Ville introuvable");
        }
    }

    @GetMapping("/departement/{id}/{n}")
    public ResponseEntity<?> getNTopVillesDepartement(@PathVariable int id, @PathVariable int n) {
        List<Ville> topVilles = villeDao.findTopNVillesByDepartement(id, n);
        if(!topVilles.isEmpty()) {
            return ResponseEntity.ok(topVilles);
        } else {
            return ResponseEntity.badRequest().body("La requête ne retourne aucun résultat, vérifiez les paramètres");
        }
    }

    @GetMapping("departement/{id}/{min}/{max}")
    public ResponseEntity<?> getByDepartementAndPopRange(@PathVariable int id, @PathVariable int min, @PathVariable int max) {
        List<Ville> villesCorrespondantes = villeDao.findByDepartementAndPopulationRange(id, min, max);
            if(!villesCorrespondantes.isEmpty()) {
                return ResponseEntity.ok(villesCorrespondantes);
            } else {
                return ResponseEntity.badRequest().body("La requête ne retourne aucun résultat, vérifiez les paramètres");
            }
        }

    @PostMapping
    public ResponseEntity<?> insertVille(@Valid @RequestBody Ville ville, BindingResult result) {
        // Vérification des contraintes
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "\n");

            return ResponseEntity.badRequest().body(errors);
        }

        List<Ville> villes = villeService.insertVille(ville);
        // Réponse : message confirmation + liste villes
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Ville ajoutée avec succès");
        response.put("villes", villes);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVille(@PathVariable int id, @RequestBody Ville ville) {
        // Vérification des contraintes
        Errors result = validator.validateObject(ville);

        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "\n");

            return ResponseEntity.badRequest().body(errors);
        }

        Ville villeAModifier = villeDao.findById(id);
        if (villeAModifier != null) {
            List<Ville> villes = villeService.modifierVille(id, ville);
            // Réponse : message confirmation + liste villes
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Ville modifiée avec succès");
            response.put("villes", villes);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Ville introuvable");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVille(@PathVariable int id) {

        Ville villeASupprimer = villeDao.findById(id);
        if (villeASupprimer != null) {
            List<Ville> villes = villeService.supprimerVille(id);

            // Réponse : message confirmation + liste villes
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Ville supprimée avec succès");
            response.put("villes", villes);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(404).body("Ville introuvable");
        }
    }
}
