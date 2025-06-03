package fr.diginamic.hello.controleurs;


import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.dtos.DepartementMapper;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.repos.DepartementRepository;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.ResponseEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departements")
public class DepartementControleur {

    @Autowired
    private DepartementRepository dptRepository;

    @Autowired
    private DepartementService departementService;

    @Autowired
    private DepartementMapper departementMapper;

    @GetMapping
    public List<DepartementDto> getDepartements() {
        return dptRepository.findAll().stream()
                .map(departementMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDepartementById(@PathVariable int id) {
        Departement dpt = dptRepository.findById(id).orElse(null);
        return ResponseEntityService.returnResponse(
                dpt != null ? departementMapper.toDto(dpt) : null, "Département");
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<?> getDepartementByNom(@PathVariable String nom) {
        Departement dpt = dptRepository.findByNom(nom);
        return ResponseEntityService.returnResponse(
                dpt != null ? departementMapper.toDto(dpt) : null, "Département");
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) {
        Departement dpt = dptRepository.findByCode(code);
        return ResponseEntityService.returnResponse(
                dpt != null ? departementMapper.toDto(dpt) : null, "Département");
    }

    @PostMapping
    public ResponseEntity<?> insertDepartement(@Valid @RequestBody DepartementDto dto, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .collect(Collectors.joining("\n"));
            return ResponseEntity.badRequest().body(erreurs);
        }

        Departement dpt = departementMapper.toBean(dto);
        List<DepartementDto> dpts = departementService.insertDpt(dpt).stream()
                .map(departementMapper::toDto)
                .toList();

        return ResponseEntity.ok(Map.of(
                "message", "Département ajouté avec succès",
                "departements", dpts));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @Valid @RequestBody DepartementDto dto, BindingResult result) {
        if (result.hasErrors()) {
            String erreurs = result.getFieldErrors().stream()
                    .map(e -> e.getField() + " : " + e.getDefaultMessage())
                    .collect(Collectors.joining("\n"));
            return ResponseEntity.badRequest().body(erreurs);
        }

        Departement existing = dptRepository.findById(id).orElse(null);
        if (existing != null) {
            Departement updatedEntity = departementMapper.toBean(dto);
            List<DepartementDto> dpts = departementService.updateDpt(id, updatedEntity).stream()
                    .map(departementMapper::toDto)
                    .toList();

            return ResponseEntity.ok(Map.of(
                    "message", "Département modifié avec succès",
                    "departements", dpts));
        } else {
            return ResponseEntity.status(404).body("Département introuvable");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) {
        Departement dpt = dptRepository.findById(id).orElse(null);
        if (dpt != null) {
            List<DepartementDto> dpts = departementService.deleteDpt(id).stream()
                    .map(departementMapper::toDto)
                    .toList();

            return ResponseEntity.ok(Map.of(
                    "message", "Département supprimé avec succès",
                    "departements", dpts));
        } else {
            return ResponseEntity.status(404).body("Département introuvable");
        }
    }
}
