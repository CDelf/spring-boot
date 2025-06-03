package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.dtos.VilleMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/villes")
public class VilleControleur {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private VilleService villeService;

    @Autowired
    private VilleMapper villeMapper;

    // GET BASIQUES
    @GetMapping
    public List<VilleDto> getVilles() {
        return villeRepository.findAll().stream()
                .map(villeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) {
        return villeRepository.findById(id)
                .map(villeMapper::toDto)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).body("Ville introuvable"));
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getVilleByNom(@PathVariable String nom) {
        Ville ville = villeRepository.findByNom(nom);
        return ville != null ? ResponseEntity.ok(villeMapper.toDto(ville))
                : ResponseEntity.status(404).body("Ville introuvable");
    }

    @GetMapping("/prefix/{prefix}")
    public ResponseEntity<?> findByPrefix(@PathVariable String prefix) {
        List<VilleDto> dtos = villeRepository.findByNomStartingWithIgnoreCase(prefix)
                .stream().map(villeMapper::toDto).toList();
        return ResponseEntityService.returnResponse(dtos, "Villes");
    }

    // GET POPULATION
    @GetMapping("/population/min/{min}")
    public ResponseEntity<?> findByMin(@PathVariable int min) {
        List<VilleDto> dtos = villeRepository.findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(min)
                .stream().map(villeMapper::toDto).toList();
        return ResponseEntityService.returnResponse(dtos, "Villes");
    }

    @GetMapping("/population/range")
    public ResponseEntity<?> findByRange(@RequestParam int min, @RequestParam int max) {
        List<Ville> villes = villeRepository.findByNbHabitantsBetweenOrderByNbHabitantsDesc(min, max);
        List<VilleDto> dtoList = villes.stream()
                .map(villeMapper::toDto)
                .toList();

        if (!dtoList.isEmpty()) {
            return ResponseEntity.ok(dtoList);
        } else {
            return ResponseEntity.status(404).body("Aucune ville trouvée avec ces critères");
        }
    }

    //  GET DEPARTMENT
    @GetMapping("/departement/{dptId}/population/min/{min}")
    public ResponseEntity<?> findByDptMin(@PathVariable int dptId, @PathVariable int min) {
        List<Ville> villes = villeRepository.findByDepartementIdAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(dptId, min);
        List<VilleDto> dtoList = villes.stream()
                .map(villeMapper::toDto)
                .toList();

        if (!dtoList.isEmpty()) {
            return ResponseEntity.ok(dtoList);
        } else {
            return ResponseEntity.status(404).body("Aucune ville trouvée avec ces critères");
        }
    }

    @GetMapping("/departement/{dptId}/population/range")
    public ResponseEntity<?> findByDptRange(@PathVariable int dptId,
                                            @RequestParam int min,
                                            @RequestParam int max) {
        List<Ville> villes = villeRepository.findByDepartementIdAndNbHabitantsBetweenOrderByNbHabitantsDesc(dptId, min, max);
        List<VilleDto> dtoList = villes.stream()
                .map(villeMapper::toDto)
                .toList();

        if (!dtoList.isEmpty()) {
            return ResponseEntity.ok(dtoList);
        } else {
            return ResponseEntity.status(404).body("Aucune ville trouvée avec ces critères");
        }
    }

    @GetMapping("/departement/{dptId}/top/{n}")
    public ResponseEntity<?> findTopNVilles(@PathVariable int dptId, @PathVariable int n) {
        List<VilleDto> dtos = villeService.findTopNVilles(dptId, n).stream()
                .map(villeMapper::toDto).toList();
        return ResponseEntityService.returnResponse(dtos, "Villes");
    }

   // GET ALL PAGINATION
    @GetMapping("/page")
    public ResponseEntity<?> getPaginatedVilles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Ville> resultPage = villeRepository.findAll(pageable);

        if (resultPage.hasContent()) {
            Page<VilleDto> dtoPage = resultPage.map(villeMapper::toDto);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.status(404).body("Aucune ville trouvée à cette page");
        }
    }


    @PostMapping
    public ResponseEntity<?> insertVille(@Valid @RequestBody VilleDto dto) {
        Ville ville = villeMapper.toBean(dto);
        villeRepository.save(ville);
        return ResponseEntity.ok(Map.of("message", "Ville ajoutée avec succès", "ville", villeMapper.toDto(ville)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVille(@PathVariable int id, @Valid @RequestBody VilleDto dto) {
        Ville ville = villeRepository.findById(id).orElse(null);
        if (ville != null) {
            Ville updated = villeMapper.toBean(dto);
            updated.setId(id);
            villeRepository.save(updated);
            return ResponseEntity.ok(Map.of("message", "Ville modifiée avec succès", "ville", villeMapper.toDto(updated)));
        }
        return ResponseEntity.status(404).body("Ville introuvable");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVille(@PathVariable int id) {
        if (villeRepository.existsById(id)) {
            villeRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("message", "Ville supprimée avec succès"));
        }
        return ResponseEntity.status(404).body("Ville introuvable");
    }
}