package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.models.Ville;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private Validator validator;

    private List<Ville> villes = new ArrayList<>(List.of(
            new Ville(1, "Paris", 2148000),
            new Ville(2, "Lyon", 513000),
            new Ville(3, "Marseille", 861000),
            new Ville(4, "Toulouse", 493000),
            new Ville(5, "Nice", 342000)));

    @GetMapping
    public List<Ville> getVilles() {
        return villes;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) {
        Optional<Ville> villeATrouver = villes.stream()
                .filter(v -> v.getId() == id)
                .findAny();
        if(villeATrouver.isPresent()) {
            return ResponseEntity.ok(villeATrouver.get());
        } else {
            return ResponseEntity.status(404).body("ville introuvable");
        }
    }

    @PostMapping
    public ResponseEntity<String> insertVille(@Valid @RequestBody Ville ville, BindingResult result) {
        // Vérification des contraintes
        if (result.hasErrors()) {
            String errors = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2 + "\n");

            return ResponseEntity.badRequest().body(errors);
        }

        // Vérifie si l'id existe déjà
        boolean idExiste = villes.stream()
                .anyMatch(v -> v.getId() == ville.getId());
        if(idExiste) {
            return ResponseEntity.badRequest().body("Cet id existe déjà");
        }
        // Vérifie si le nom existe déjà
        boolean nomExiste = villes.stream()
                        .anyMatch(v -> v.getNom().equalsIgnoreCase(ville.getNom()));
        if(nomExiste) {
            return ResponseEntity.badRequest().body("Une ville de même nom existe déjà");
        }
        // Si aucun doublon ou erreur, ajoute la ville
        villes.add(ville);
        return ResponseEntity.ok("Ville ajoutée avec succès");
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

        // Mise à jour de la ville si elle existe
        Optional<Ville> villeAModifier = villes.stream()
                .filter(v -> v.getId() == id)
                .findAny();
        if (villeAModifier.isPresent()) {
            Ville v = villeAModifier.get();
            v.setNom(ville.getNom());
            v.setNbHabitants(ville.getNbHabitants());
            return ResponseEntity.ok("Ville modifiée avec succès");
        } else {
            return ResponseEntity.status(404).body("Ville introuvable");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        Optional<Ville> villeASupprimer = villes.stream()
                .filter(v -> v.getId() == id)
                .findAny();
        if(villeASupprimer.isPresent()) {
            villes.remove(villeASupprimer.get());
            return ResponseEntity.ok("Ville supprimée avec succès");
        } else {
            return ResponseEntity.status(404).body("Ville introuvable");
        }
    }
}
