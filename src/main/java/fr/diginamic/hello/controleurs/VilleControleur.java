package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.dtos.VilleMapper;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repos.VilleRepository;
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
                .toList();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getVilleById(@PathVariable int id) throws FunctionalException {
        Ville ville = villeService.getById(id);
        return ResponseEntity.ok(villeMapper.toDto(ville));
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getVilleByNom(@PathVariable String nom) throws FunctionalException {
        Ville ville = villeService.getByNom(nom);
        return ResponseEntity.ok(villeMapper.toDto(ville));
    }

    @GetMapping("/prefix/{prefix}")
    public ResponseEntity<?> findByPrefix(@PathVariable String prefix) throws FunctionalException {
        List<VilleDto> dtoList = villeService.getByPrefix(prefix)
                .stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(List.of(dtoList, "Villes"));
    }

    // GET POPULATION
    @GetMapping("/population/min")
    public ResponseEntity<?> findByMin(@RequestParam int min) throws FunctionalException {
        List<VilleDto> dtoList = villeService.getByNbHabitantsMin(min)
                .stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(List.of(dtoList, "Villes"));
    }

    @GetMapping("/population/range")
    public ResponseEntity<?> findByRange(@RequestParam int min, @RequestParam int max) throws FunctionalException {
        List<Ville> villes = villeService.getByNbHabitantsRange(min, max);
        List<VilleDto> dtoList = villes.stream()
                .map(villeMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    //  GET DEPARTMENT
    @GetMapping("/departement/code/{code}/population/min")
    public ResponseEntity<?> findByDptMin(@PathVariable String code,@RequestParam int min) throws FunctionalException {
        List<Ville> villes = villeService.getByDepartementAndNbHabitantsMin(code, min);
        List<VilleDto> dtoList = villes.stream()
                .map(villeMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/departement/code/{code}/population/range")
    public ResponseEntity<?> findByDptRange(@PathVariable String code,
                                            @RequestParam int min,
                                            @RequestParam int max) throws FunctionalException {
        List<Ville> villes = villeService.getByDepartementAndNbHabitantsRange(code, min, max);
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
    public ResponseEntity<?> findTopNVilles(@PathVariable int dptId, @PathVariable int n) throws FunctionalException {
        List<VilleDto> dtos = villeService.getTopNVilles(dptId, n).stream()
                .map(villeMapper::toDto)
                .toList();
        return ResponseEntity.ok(Map.of("top", n, "villes", dtos));
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
    public ResponseEntity<?> insertVille(@Valid @RequestBody VilleDto dto) throws FunctionalException {
        Ville ville = villeMapper.toBean(dto);
        List<VilleDto> dtoList = villeService.save(ville).stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(Map.of("message", "Ville ajoutée avec succès", "villes", dtoList));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateVille(@PathVariable int id, @Valid @RequestBody VilleDto dto) throws FunctionalException {
        Ville updatedVille = villeMapper.toBean(dto);
        updatedVille.setId(id);
        List<Ville> updatedList = villeService.update(id, updatedVille);

        List<VilleDto> dtoList = updatedList.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Ville modifiée avec succès",
                "villes", dtoList
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVille(@PathVariable int id) throws FunctionalException {
        List<Ville> updatedList = villeService.delete(id);

        List<VilleDto> dtoList = updatedList.stream().map(villeMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Ville supprimée avec succès",
                "villes", dtoList
        ));
    }
}