package fr.diginamic.hello.controleurs;


import com.itextpdf.text.DocumentException;
import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.dtos.IDepartementMapper;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repos.DepartementRepository;
import fr.diginamic.hello.services.IDepartementService;
import fr.diginamic.hello.services.IVilleService;
import fr.diginamic.hello.utils.PdfUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departements")
public class DepartementControleur implements IDepartementControleurDoc {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private IDepartementService IDepartementService;

    @Autowired
    private IDepartementMapper IDepartementMapper;

    @Autowired
    private IVilleService villeService;

    @GetMapping
    @Override
    public List<DepartementDto> getDepartements() {
        return departementRepository.findAll().stream()
                .map(IDepartementMapper::toDto)
                .toList();
    }

    @GetMapping("/id/{id}")
    @Override
    public ResponseEntity<?> getDepartementById(@PathVariable int id) throws FunctionalException {
        Departement dpt = IDepartementService.getById(id);
        return ResponseEntity.ok(IDepartementMapper.toDto(dpt));
    }

    @GetMapping("/nom/{nom}")
    @Override
    public ResponseEntity<?> getDepartementByNom(@PathVariable String nom) throws FunctionalException {
        Departement dpt = IDepartementService.getByNom(nom);
        return ResponseEntity.ok(IDepartementMapper.toDto(dpt));
    }

    @GetMapping("/code/{code}")
    @Override
    public ResponseEntity<?> getDepartementByCode(@PathVariable String code) throws FunctionalException {
        Departement dpt = IDepartementService.getByCode(code);
        return ResponseEntity.ok(IDepartementMapper.toDto(dpt));
    }

    // Generer PDF
    @GetMapping("code/{code}/pdf")
    public void exportPdf(@PathVariable String code, HttpServletResponse response) throws IOException, DocumentException, FunctionalException {
        Departement dpt = IDepartementService.getByCode(code);
        List<Ville> villes = villeService.getByDepartementCode(code);
        PdfUtil.exportDptPdf(response, dpt, villes);
    }

    @PostMapping
    @Override
    public ResponseEntity<?> insertDepartement(@Valid @RequestBody DepartementDto dto) throws FunctionalException {
        Departement dpt = IDepartementMapper.toBean(dto);
        List<DepartementDto> dtos = IDepartementService.insertDpt(dpt).stream()
                .map(IDepartementMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Département ajouté avec succès",
                "départements", dtos
        ));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> updateDepartement(@PathVariable int id, @Valid @RequestBody DepartementDto dto) throws FunctionalException {
        Departement updatedDpt = IDepartementMapper.toBean(dto);
        updatedDpt.setId(id);
        List<DepartementDto> dtos = IDepartementService.updateDpt(id, updatedDpt).stream()
                .map(IDepartementMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Département modifié avec succès",
                "départements", dtos
        ));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) throws FunctionalException {
        List<Departement> updatedDpts = IDepartementService.deleteDpt(id);
        List<DepartementDto> dtos = updatedDpts.stream().map(IDepartementMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Département supprimé avec succès",
                "départements", dtos
        ));
    }
}
