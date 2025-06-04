package fr.diginamic.hello.controleurs;


import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.dtos.DepartementMapper;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.repos.DepartementRepository;
import fr.diginamic.hello.services.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departements")
public class DepartementControleur implements IDepartementControleurDoc {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private DepartementService departementService;

    @Autowired
    private DepartementMapper departementMapper;

    @GetMapping
    @Override
    public List<DepartementDto> getDepartements() {
        return departementRepository.findAll().stream()
                .map(departementMapper::toDto)
                .toList();
    }

    @GetMapping("/id/{id}")
    @Override
    public ResponseEntity<?> getDepartementById(@PathVariable int id) throws FunctionalException {
        Departement dpt = departementService.getById(id);
        return ResponseEntity.ok(departementMapper.toDto(dpt));
    }

    @GetMapping("/nom/{nom}")
    @Override
    public ResponseEntity<?> getDepartementByNom(@PathVariable String nom) throws FunctionalException {
        Departement dpt = departementService.getByNom(nom);
        return ResponseEntity.ok(departementMapper.toDto(dpt));
    }

    @GetMapping("/code/{code}")
    @Override
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) throws FunctionalException {
        Departement dpt = departementService.getByCode(code);
        return ResponseEntity.ok(departementMapper.toDto(dpt));
    }

    @PostMapping
    @Override
    public ResponseEntity<?> insertDepartement(@Valid @RequestBody DepartementDto dto) throws FunctionalException {
        Departement dpt = departementMapper.toBean(dto);
        List<DepartementDto> dtos = departementService.insertDpt(dpt).stream()
                .map(departementMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Département ajouté avec succès",
                "départements", dtos
        ));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @Valid @RequestBody DepartementDto dto) throws FunctionalException {
        Departement updatedDpt = departementMapper.toBean(dto);
        updatedDpt.setId(id);
        List<DepartementDto> dtos = departementService.updateDpt(id, updatedDpt).stream()
                .map(departementMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Département modifié avec succès",
                "départements", dtos
        ));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) throws FunctionalException {
        List<Departement> updatedDpts = departementService.deleteDpt(id);
        List<DepartementDto> dtos = updatedDpts.stream().map(departementMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Département supprimé avec succès",
                "départements", dtos
        ));
    }
}
