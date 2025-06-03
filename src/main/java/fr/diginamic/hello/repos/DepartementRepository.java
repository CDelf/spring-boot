package fr.diginamic.hello.repos;

import fr.diginamic.hello.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Departement findByNom(String nom);

    Departement findByCode(String code);

}
