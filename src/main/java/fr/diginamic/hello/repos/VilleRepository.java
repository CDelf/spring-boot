package fr.diginamic.hello.repos;

import fr.diginamic.hello.models.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    Page<Ville> findAll(Pageable pageable);

    Ville findByNom(String nom);

    // Villes dont le nom commence par une chaîne donnée
    List<Ville> findByNomStartingWithIgnoreCase(String prefix);

    // Villes avec population > min, triées décroissantes
    List<Ville> findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(int min);

    // Villes avec population entre min et max
    List<Ville> findByNbHabitantsBetweenOrderByNbHabitantsDesc(int min, int max);

    // Villes d'un département, population > min
    List<Ville> findByDepartementIdAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(int id, int min);
//    @Query("SELECT v FROM Ville v WHERE v.departement.id = :dptId AND v.nbHabitants > :min ORDER BY v.nbHabitants DESC")
//    List<Ville> findByDepartementIdAndMinPopulation(@Param("dptId") int dptId, @Param("min") int min);

    // Villes d'un département, population entre min et max
    List<Ville> findByDepartementIdAndNbHabitantsBetweenOrderByNbHabitantsDesc(int id, int min, int max);
//    @Query("SELECT v FROM Ville v WHERE v.departement.id = :dptId AND v.nbHabitants BETWEEN :min AND :max ORDER BY v.nbHabitants DESC")
//    List<Ville> findByDepartementIdAndPopulationRange(@Param("dptId") int dptId, @Param("min") int min, @Param("max") int max);

    // Toutes les villes d’un département, triées desc (gestion du N dans le contrôleur)
    @Query("SELECT v FROM Ville v WHERE v.departement.id = :dptId ORDER BY v.nbHabitants DESC")
    List<Ville> findByDepartementIdOrderByNbHabitantsDesc(@Param("dptId") int dptId);
}
