package fr.diginamic.hello.repos;

import fr.diginamic.hello.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Optional<Departement> findByNom(String nom);

    List<Departement> findAllByNom(String nom);

    Optional<Departement> findByCode(String code);

}
