package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.exceptions.FunctionalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Département", description = "Opérations liées aux départements")
public interface IDepartementControleurDoc {
    @Operation(summary = "Récupère tous les départements", description = "Retourne la liste complète des départements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucun département trouvé")
    })
    @GetMapping
    List<DepartementDto> getDepartements();

    @Operation(summary = "Récupère un département avec son Id", description = "Retourne le département correspondant à l'Id entré en paramètre, s'il existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département retourné avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucun département trouvé")
    })
    @GetMapping("/id/{id}")
    ResponseEntity<?> getDepartementById(@PathVariable int id) throws FunctionalException;

    @Operation(summary = "Récupère un département avec son nom", description = "Retourne le département correspondant au nom entré en paramètre, s'il existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département retourné avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucun département trouvé")
    })
    @GetMapping("/nom/{nom}")
    ResponseEntity<?> getDepartementByNom(@PathVariable String nom) throws FunctionalException;

    @Operation(summary = "Récupère un département avec son code", description = "Retourne le département correspondant au code entré en paramètre, s'il existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département retourné avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucun département trouvé")
    })
    @GetMapping("/code/{code}")
    ResponseEntity<?> getDepartementByCode(@PathVariable String code) throws FunctionalException;

    @Operation(summary = "Créer un département", description = "Création d'un département dont les attributs sont entrés (format JSON) dans le corps de la requête.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Création impossible (règle métier non respectées précisée dans le message d'erreur")
    })
    @PostMapping
    ResponseEntity<?> insertDepartement(@Valid @RequestBody DepartementDto dto) throws FunctionalException;

    @Operation(summary = "Modifier un département", description = "Modification d'un département dont les attributs sont entrés (format JSON) dans le corps de la requête.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Modification impossible (règle métier non respectées précisée dans le message d'erreur")
    })
    @PutMapping("/{id}")
    ResponseEntity<?> updateDepartement(@PathVariable int id, @Valid @RequestBody DepartementDto dto) throws FunctionalException;

    @Operation(summary = "Supprimer un département avec son ID", description = "Suppression du département correspondant à l'id entré en paramètre, s'il existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Département supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Département introuvable")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteDepartement(@PathVariable int id) throws FunctionalException;
}
