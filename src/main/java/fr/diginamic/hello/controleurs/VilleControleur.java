package fr.diginamic.hello.controleurs;

import com.itextpdf.text.DocumentException;
import fr.diginamic.hello.dtos.IVilleMapper;
import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repos.VilleRepository;
import fr.diginamic.hello.services.IVilleService;
import fr.diginamic.hello.utils.CsvUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/villes")
public class VilleControleur implements IVilleControllerDoc {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private IVilleService villeService;

    @Autowired
    private IVilleMapper IVilleMapper;

    // GET BASIQUES
    @GetMapping
    @Override
    public List<VilleDto> getVilles() {
        return villeRepository.findAll().stream()
                .map(IVilleMapper::toDto)
                .toList();
    }

    @GetMapping("/id/{id}")
    @Override
    public ResponseEntity<?> getVilleById(@PathVariable int id) throws FunctionalException {
        Ville ville = villeService.getById(id);
        return ResponseEntity.ok(IVilleMapper.toDto(ville));
    }

    @GetMapping("/nom/{nom}")
    @Override
    public ResponseEntity<?> getVilleByNom(@PathVariable String nom) throws FunctionalException {
        Ville ville = villeService.getByNom(nom);
        return ResponseEntity.ok(IVilleMapper.toDto(ville));
    }

    @GetMapping("/prefix/{prefix}")
    @Override
    public ResponseEntity<?> findByPrefix(@PathVariable String prefix) throws FunctionalException {
        List<VilleDto> dtoList = villeService.getByPrefix(prefix)
                .stream().map(IVilleMapper::toDto).toList();
        return ResponseEntity.ok(List.of(dtoList, "Villes"));
    }

    // GET POPULATION
    @GetMapping("/population/min")
    @Override
    public ResponseEntity<?> findByMin(@RequestParam int min) throws FunctionalException {
        List<VilleDto> dtoList = villeService.getByNbHabitantsMin(min)
                .stream().map(IVilleMapper::toDto).toList();
        return ResponseEntity.ok(List.of(dtoList, "Villes"));
    }

    @GetMapping("/population/range")
    @Override
    public ResponseEntity<?> findByRange(@RequestParam int min, @RequestParam int max) throws FunctionalException {
        List<Ville> villes = villeService.getByNbHabitantsRange(min, max);
        List<VilleDto> dtoList = villes.stream()
                .map(IVilleMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    //  GET DEPARTMENT
    @GetMapping("/departement/code/{code}/population/min")
    @Override
    public ResponseEntity<?> findByDptMin(@PathVariable String code, @RequestParam int min) throws FunctionalException {
        List<Ville> villes = villeService.getByDepartementAndNbHabitantsMin(code, min);
        List<VilleDto> dtoList = villes.stream()
                .map(IVilleMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/departement/code/{code}/population/range")
    @Override
    public ResponseEntity<?> findByDptRange(@PathVariable String code,
                                            @RequestParam int min,
                                            @RequestParam int max) throws FunctionalException {
        List<Ville> villes = villeService.getByDepartementAndNbHabitantsRange(code, min, max);
        List<VilleDto> dtoList = villes.stream()
                .map(IVilleMapper::toDto)
                .toList();

        if (!dtoList.isEmpty()) {
            return ResponseEntity.ok(dtoList);
        } else {
            return ResponseEntity.status(404).body("Aucune ville trouvée avec ces critères");
        }
    }

    @GetMapping("/departement/{dptId}/top/{n}")
    @Override
    public ResponseEntity<?> findTopNVilles(@PathVariable int dptId, @PathVariable int n) throws FunctionalException {
        List<VilleDto> dtos = villeService.getTopNVilles(dptId, n).stream()
                .map(IVilleMapper::toDto)
                .toList();
        return ResponseEntity.ok(Map.of("top", n, "villes", dtos));
    }

   // GET ALL PAGINATION
   @GetMapping("/page")
   @Override
   public ResponseEntity<?> getPaginatedVilles(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Ville> resultPage = villeRepository.findAll(pageable);

        if (resultPage.hasContent()) {
            Page<VilleDto> dtoPage = resultPage.map(IVilleMapper::toDto);
            return ResponseEntity.ok(dtoPage);
        } else {
            return ResponseEntity.status(404).body("Aucune ville trouvée à cette page");
        }
    }

    // GENERATION CSV
    @GetMapping("/{min}/csv")
    public void exportCsv(@PathVariable int min, HttpServletResponse response) throws IOException, DocumentException, FunctionalException {
        List<Ville> villes = villeService.getByNbHabitantsMin(min);
        CsvUtil.exportVillesCsv(response, villes);
    }

    @PostMapping
    @Override
    public ResponseEntity<?> insertVille(@Valid @RequestBody VilleDto dto) throws FunctionalException {
        Ville ville = IVilleMapper.toBean(dto);
        List<VilleDto> dtoList = villeService.save(ville).stream().map(IVilleMapper::toDto).toList();
        return ResponseEntity.ok(Map.of("message", "Ville ajoutée avec succès", "villes", dtoList));
    }


    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> updateVille(@PathVariable int id, @Valid @RequestBody VilleDto dto) throws FunctionalException {
        Ville updatedVille = IVilleMapper.toBean(dto);
        updatedVille.setId(id);
        List<Ville> updatedList = villeService.update(id, updatedVille);

        List<VilleDto> dtoList = updatedList.stream().map(IVilleMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Ville modifiée avec succès",
                "villes", dtoList
        ));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteVille(@PathVariable int id) throws FunctionalException {
        List<Ville> updatedList = villeService.delete(id);

        List<VilleDto> dtoList = updatedList.stream().map(IVilleMapper::toDto).toList();
        return ResponseEntity.ok(Map.of(
                "message", "Ville supprimée avec succès",
                "villes", dtoList
        ));
    }
}