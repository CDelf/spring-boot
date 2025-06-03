package fr.diginamic.hello.services;

import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repos.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepo;

    // Retour un nombre n de résultats pour le TOP ville
    public List<Ville> findTopNVilles(int dptId, int n) {
        return villeRepo.findByDepartementIdOrderByNbHabitantsDesc(dptId)
                .stream().limit(n).toList();
    }

    // Gestion des retours de listes après les save et update
    public List<Ville> save(Ville ville) {
        villeRepo.save(ville);
        return villeRepo.findAll();
    }

    public List<Ville> update(int id, Ville villeModifiee) {
        Optional<Ville> villeOpt = villeRepo.findById(id);
        if (villeOpt.isPresent()) {
            Ville ville = villeOpt.get();
            ville.setNom(villeModifiee.getNom());
            ville.setNbHabitants(villeModifiee.getNbHabitants());
            ville.setDepartement(villeModifiee.getDepartement());
            villeRepo.save(ville);
        }
        return villeRepo.findAll();
    }

    public List<Ville> delete(int id) {
        villeRepo.deleteById(id);
        return villeRepo.findAll();
    }
}
