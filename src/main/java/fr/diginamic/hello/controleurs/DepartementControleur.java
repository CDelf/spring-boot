package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.services.DepartementService;
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
    private DepartementDao departementDao;

    @Autowired
    private DepartementService departementService;

    @GetMapping
    public List<Departement> getDepartements() {
        return departementDao.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDepartementById(@PathVariable int id) {
        Departement dptATrouver = departementDao.findById(id);
        if (dptATrouver != null) {
            return ResponseEntity.ok(dptATrouver);
        } else {
            return ResponseEntity.status(404).body("département introuvable");
        }
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getDepartementByNom(@PathVariable String nom) {
        Departement dptATrouver = departementDao.findByNom(nom);
        if (dptATrouver != null) {
            return ResponseEntity.ok(dptATrouver);
        } else {
            return ResponseEntity.status(404).body("département introuvable");
        }
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
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Département ajouté avec succès");
        response.put("departements", dpts);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @Valid @RequestBody Departement dpt, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "\n");
            return ResponseEntity.badRequest().body(erreurs);
        }

        Departement dptAModifier = departementDao.findById(id);
        if (dptAModifier != null) {
            List<Departement> dpts = departementService.updateDpt(id, dpt);
            // Réponse : message de confirmation + retourne liste des départements en base
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Département modifié avec succès");
            response.put("departements", dpts);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("Département introuvable");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) {
        Departement dptASupprimer = departementDao.findById(id);
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
