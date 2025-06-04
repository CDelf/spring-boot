package fr.diginamic.hello.repos;

import fr.diginamic.hello.models.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    Page<Ville> findAll(Pageable pageable);

    Optional<Ville> findByNom(String nom);

    // Liste des villes d'un département ayant un nom précisé (doublon)
    List<Ville> findByDepartementIdAndNomIgnoreCase(int id, String nom);

    // Villes dont le nom commence par une chaîne donnée
    List<Ville> findByNomStartingWithIgnoreCase(String prefix);

    // Villes avec population > min, triées décroissantes
    List<Ville> findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(int min);

    // Villes avec population entre min et max
    List<Ville> findByNbHabitantsBetweenOrderByNbHabitantsDesc(int min, int max);

    // Villes d'un département
    List<Ville> findByDepartementCode(String code);

    // Villes d'un département, population > min
    List<Ville> findByDepartementCodeAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(String code, int min);

    // Villes d'un département, population entre min et max
    List<Ville> findByDepartementCodeAndNbHabitantsBetweenOrderByNbHabitantsDesc(String code, int min, int max);

    // Toutes les villes d’un département, triées desc
    List<Ville> findByDepartementIdOrderByNbHabitantsDesc(int departementId);

}
