package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.repos.DepartementRepository;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.ResponseEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

    @Autowired
    private DepartementRepository dptRepository;

    @Autowired
    private DepartementService departementService;

    @GetMapping
    public List<Departement> getDepartements() {
        return dptRepository.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDepartementById(@PathVariable int id) {
        Departement dptATrouver = dptRepository.findById(id).orElse(null);
        return ResponseEntityService.returnResponse(dptATrouver, "Département");
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getDepartementByNom(@PathVariable String nom) {
        Departement dptATrouver = dptRepository.findByNom(nom);
        return ResponseEntityService.returnResponse(dptATrouver, "Département");
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) {
        Departement dptATrouver = dptRepository.findByCode(code);
        return ResponseEntityService.returnResponse(dptATrouver, "Département");
    }

    @PostMapping
    public ResponseEntity<?> insertDepartement(@Valid @RequestBody Departement dpt, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "\n");
            return ResponseEntity.badRequest().body(erreurs);
        }

        List<Departement> dpts = departementService.insertDpt(dpt);
        // Réponse : message de confirmation + retourne liste des départements en base
        return ResponseEntity.ok(Map.of(
                "message", "Département ajouté avec succès",
                "departements", dpts));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @Valid @RequestBody Departement dpt, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "\n");
            return ResponseEntity.badRequest().body(erreurs);
        }

        Departement dptAModifier = dptRepository.findById(id).orElse(null);
        if (dptAModifier != null) {
            List<Departement> dpts = departementService.updateDpt(id, dpt);
            // Réponse : message de confirmation + retourne liste des départements en base
            return ResponseEntity.ok(Map.of(
                    "message", "Département modifié avec succès",
                    "departements", dpts));
        } else {
            return ResponseEntity.status(404).body("Département introuvable");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) {
        Departement dptASupprimer = dptRepository.findById(id).orElse(null);
        if (dptASupprimer != null) {
            List<Departement> dpts = departementService.deleteDpt(id);
            // Réponse : message de confirmation + retourne liste des départements en base
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Département supprimé avec succès");
            response.put("departements", dpts);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Département introuvable");
        }
    }
}
