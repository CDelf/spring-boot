package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.exceptions.FunctionalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ville", description = "Opérations liées aux villes")
public interface IVilleControllerDoc {
    // GET BASIQUES
    @Operation(summary = "Récupère toutes les villes", description = "Retourne la liste complète des villes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
    @GetMapping("/villes")
    List<VilleDto> getVilles();


    @Operation(summary = "Rechercher une ville avec son id", description = "Retourne la ville dont l'id entré en paramètre correspond, si elle existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })

    @GetMapping("/id/{id}")
    ResponseEntity<?> getVilleById(@PathVariable int id) throws FunctionalException;

    @Operation(summary = "Recherche une ville avec son nom", description = "Retourne la ville dont le nom entré en paramètre correspond, si elle existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })

    @GetMapping("/nom/{nom}")
    ResponseEntity<?> getVilleByNom(@PathVariable String nom) throws FunctionalException;

    @Operation(summary = "Recherche de ville avec un préfixe", description = "Retourne la liste des villes dont le nom commence par le préfixe de type String entré en paramètre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
    @GetMapping("/prefix/{prefix}")
    ResponseEntity<?> findByPrefix(@PathVariable String prefix) throws FunctionalException;


    // GET POPULATION
    @Operation(summary = "Récupère une liste de villes selon un nombre d'habitants minimum", description = "Retourne la liste des villes dont le nombre d'habitants est supérieur au minimum entré en paramètre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
    @GetMapping("/population/min")
    ResponseEntity<?> findByMin(@RequestParam int min) throws FunctionalException;

    @Operation(summary = "Récupère une liste de villes selon un nombre d'habitants minimum et maximum", description = "Retourne la liste des villes dont le nombre d'habitants est supérieur au minimum et inférieur au maximum entrés en paramètre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
    @GetMapping("/population/range")
    ResponseEntity<?> findByRange(@RequestParam int min, @RequestParam int max) throws FunctionalException;

    //  GET DEPARTMENT
    @Operation(summary = "Récupère une liste de villes d'un département selon un nombre d'habitants minimum", description = "Retourne la liste des villes du département indiqué, dont le nombre d'habitants est supérieur au minimum entré en paramètre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
    @GetMapping("/departement/code/{code}/population/min")
    ResponseEntity<?> findByDptMin(@PathVariable String code, @RequestParam int min) throws FunctionalException;

    @Operation(summary = "Récupère une liste de villes d'un département selon un nombre d'habitants minimum et maximum", description = "Retourne la liste des villes du département indiqué, dont le nombre d'habitants est supérieur au minimum et inférieur au maximum entrés en paramètre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
    @GetMapping("/departement/code/{code}/population/range")
    ResponseEntity<?> findByDptRange(@PathVariable String code,
                                     @RequestParam int min,
                                     @RequestParam int max) throws FunctionalException;

    @Operation(summary = "Récupère les N villes d'un département les plus peuplées", description = "Retourne la liste de N villes du département indiqué par ordre descendant du nombre d'habitants.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
    @GetMapping("/departement/{dptId}/top/{n}")
    ResponseEntity<?> findTopNVilles(@PathVariable int dptId, @PathVariable int n) throws FunctionalException;

    // GET ALL PAGINATION
    @Operation(summary = "Récupère la liste complète de ville paginée.", description = "Retourne la liste des villes sur la page indiquée (par défaut 0) de taille indiquée (par défaut 10) en paramètre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste retournée avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune ville trouvée")
    })
     @GetMapping("/page")
     ResponseEntity<?> getPaginatedVilles(
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Créer une ville", description = "Création d'une ville dont les attributs sont entrés (format JSON) dans le corps de la requête.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Création impossible (règle métier non respectées précisée dans le message d'erreur")
    })
    @PostMapping
    ResponseEntity<?> insertVille(@Valid @RequestBody VilleDto dto) throws FunctionalException;

    @Operation(summary = "Modifier une ville", description = "Modification d'une ville dont les attributs sont entrés (format JSON) dans le corps de la requête.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville modifiée avec succès"),
            @ApiResponse(responseCode = "400", description = "Modification impossible (règle métier non respectées précisée dans le message d'erreur")
    })
    @PutMapping("/{id}")
    ResponseEntity<?> updateVille(@PathVariable int id, @Valid @RequestBody VilleDto dto) throws FunctionalException;

    @Operation(summary = "Supprimer une ville avec son id", description = "Suppression de la ville correspondant à l'ID précisé en paramètre, si elle existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Ville introuvable")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteVille(@PathVariable int id) throws FunctionalException;
}
