package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.models.Ville;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

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
            return ResponseEntity.ok(villeATrouver);
        } else {
            return ResponseEntity.status(404).body("ville introuvable");
        }
    }

    @PostMapping
    public ResponseEntity<String> insertVille(@RequestBody Ville ville) {
        // Vérifie si l'id existe déjà
        boolean idExiste = villes.stream()
                .anyMatch(v -> v.getId() == ville.getId());
        if(idExiste) {
            return ResponseEntity.status(404).body("Cet id existe déjà");
        }
        // Vérifie si le nom existe déjà
        boolean nomExiste = villes.stream()
                        .anyMatch(v -> v.getNom().equalsIgnoreCase(ville.getNom()));
        if(nomExiste) {
            return ResponseEntity.status(404).body("Une ville de même nom existe déjà");
        }
        // Si aucun doublon, ajoute la ville
        villes.add(ville);
        return ResponseEntity.ok("Ville ajoutée avec succès");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateVille(@PathVariable int id, @RequestBody Ville ville) {
       for(Ville v : villes) {
           if(v.getId() == id) {
               v.setNom(ville.getNom());
               v.setNbHabitants(ville.getNbHabitants());
               return ResponseEntity.ok("Ville modifiée avec succès");
           }
       }
       return ResponseEntity.status(404).body("Ville introuvable");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {
        for(Ville v : villes) {
            if(v.getId() == id) {
                villes.remove(v);
                return ResponseEntity.ok("Ville supprimée");
            }
        }
        return ResponseEntity.status(404).body("Ville introuvable");
    }
}
