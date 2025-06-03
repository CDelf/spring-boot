package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repos.VilleRepository;
import fr.diginamic.hello.services.ResponseEntityService;
import fr.diginamic.hello.services.VilleService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private VilleRepository villeRepository;

    @Autowired
    private VilleService villeService;


    @GetMapping
    public List<Ville> getVilles() {
        return villeRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) {
        Ville villeATrouver = villeRepository.findById(id).orElse(null);
        return ResponseEntityService.returnResponse(villeATrouver, "Ville");
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getVilleByNom(@PathVariable String nom) {
        Ville villeATrouver = villeRepository.findByNom(nom);
        return ResponseEntityService.returnResponse(villeATrouver, "Ville");
    }

    // GET: Extra queries
    @GetMapping("/prefix/{prefix}")
    public ResponseEntity<?> findByPrefix(@PathVariable String prefix) {
        List<Ville> villes = villeRepository.findByNomStartingWithIgnoreCase(prefix);
        return ResponseEntityService.returnResponse(villes, "Villes");
    }

    @GetMapping("/population/min/{min}")
    public ResponseEntity<?> findByMin(@PathVariable int min) {
        List<Ville> villes = villeRepository.findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(min);
        return ResponseEntityService.returnResponse(villes, "Villes");
    }

    @GetMapping("/population/range")
    public ResponseEntity<?> findByRange(@RequestParam int min, @RequestParam int max) {
        List<Ville> villes = villeRepository.findByNbHabitantsBetweenOrderByNbHabitantsDesc(min, max);
        return ResponseEntityService.returnResponse(villes, "Villes");
    }

    @GetMapping("/departement/{dptId}/population/min/{min}")
    public ResponseEntity<?> findByDptMin(@PathVariable int dptId, @PathVariable int min) {
        List<Ville> villes = villeRepository.findByDepartementIdAndMinPopulation(dptId, min);
        return ResponseEntityService.returnResponse(villes, "Villes");
    }

    @GetMapping("/departement/{dptId}/population/range")
    public ResponseEntity<?> findByDptRange(@PathVariable int dptId,
                                      @RequestParam int min,
                                      @RequestParam int max) {
        List<Ville> villes = villeRepository.findByDepartementIdAndPopulationRange(dptId, min, max);
        return ResponseEntityService.returnResponse(villes, "Villes");
    }

    @GetMapping("/departement/{dptId}/top/{n}")
    public ResponseEntity<?> findTopNVilles(@PathVariable int dptId, @PathVariable int n) {
        List<Ville> villes = villeService.findTopNVilles(dptId, n);
        return ResponseEntityService.returnResponse(villes, "Villes");
    }

    @GetMapping("/page")
    public ResponseEntity<?> getPaginatedVilles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Ville> resultPage = villeRepository.findAll(pageable);

        if (resultPage.hasContent()) {
            return ResponseEntity.ok(resultPage);
        } else {
            return ResponseEntity.status(404).body("Aucune ville trouvée à cette page");
        }
    }

    // --------------------------

    @PostMapping
    public ResponseEntity<?> insertVille(@Valid @RequestBody Ville ville, BindingResult result) {
        // Vérification des contraintes
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "\n");

            return ResponseEntity.badRequest().body(errors);
        }

        List<Ville> villes = villeService.save(ville);
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

        Ville villeAModifier = villeRepository.findById(id).orElse(null);
        if (villeAModifier != null) {
            List<Ville> villes = villeService.update(id, ville);
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

        Ville villeASupprimer = villeRepository.findById(id).orElse(null);
        if (villeASupprimer != null) {
            List<Ville> villes = villeService.delete(id);

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
