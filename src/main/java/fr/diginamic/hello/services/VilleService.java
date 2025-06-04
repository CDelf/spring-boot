package fr.diginamic.hello.services;

import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Ville;
import fr.diginamic.hello.repos.DepartementRepository;
import fr.diginamic.hello.repos.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService implements IVilleService {

    @Autowired
    private VilleRepository villeRepo;
    @Autowired
    private DepartementRepository departementRepository;

    @Override
    public Ville getById(int id) throws FunctionalException {
        return villeRepo.findById(id)
                .orElseThrow(() -> new FunctionalException("Ville introuvable"));
    }

    @Override
    public Ville getByNom(String nom) throws FunctionalException {
        return villeRepo.findByNom(nom)
                .orElseThrow(() -> new FunctionalException("Ville introuvable"));
    }

    @Override
    public List<Ville> getByPrefix(String prefix) throws FunctionalException {
        List<Ville> villes = villeRepo.findByNomStartingWithIgnoreCase(prefix);
        if(villes.isEmpty()) {
            throw new FunctionalException("Aucune ville dont le nom commence par " + prefix + " n’a été trouvée");
        }
        return villes;
    }

    @Override
    public List<Ville> getByNbHabitantsMin(int min) throws FunctionalException {
        List<Ville> villes = villeRepo.findByNbHabitantsGreaterThanOrderByNbHabitantsDesc(min);
        if(villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population supérieure à " + min);
        }
        return villes;
    }

    @Override
    public List<Ville> getByNbHabitantsRange(int min, int max) throws FunctionalException {
        List<Ville> villes = villeRepo.findByNbHabitantsBetweenOrderByNbHabitantsDesc(min, max);
        if(villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population comprise entre " + min + " et " + max);
        }
        return villes;
    }

    @Override
    public List<Ville> getByDepartementCode(String code) throws FunctionalException {
        List<Ville> villes = villeRepo.findByDepartementCode(code);
        if(villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n'a été trouvée pour ce code département " + code);
        }
        return villes;
    }

    @Override
    public List<Ville> getByDepartementAndNbHabitantsMin(String code, int min) throws FunctionalException {
        List<Ville> villes = villeRepo.findByDepartementCodeAndNbHabitantsGreaterThanOrderByNbHabitantsDesc(code, min);
        if(villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population supérieure à "+ min + " dans le département " + code);
        }
        return villes;
    }

    @Override
    public List<Ville> getByDepartementAndNbHabitantsRange(String code, int min, int max) throws FunctionalException{
        List<Ville> villes = villeRepo.findByDepartementCodeAndNbHabitantsBetweenOrderByNbHabitantsDesc(code, min, max);
        if(villes.isEmpty()) {
            throw new FunctionalException("Aucune ville n’a une population supérieure à " + min + " dans le département " + code);
        }
        return villes;
    }
    // Retour un nombre n de résultats pour le TOP ville
    @Override
    public List<Ville> getTopNVilles(int dptId, int n) throws FunctionalException {
        departementRepository.findById(dptId).orElseThrow(() -> new FunctionalException("Département introuvable"));
        return villeRepo.findByDepartementIdOrderByNbHabitantsDesc(dptId)
                .stream()
                .limit(n)
                .toList();
    }

    // Gestion des retours de listes après les save et update
    @Override
    public List<Ville> save(Ville ville) throws FunctionalException {
        validateVille(ville);
        villeRepo.save(ville);
        return villeRepo.findAll();
    }

    @Override
    public List<Ville> update(int id, Ville villeModifiee) throws FunctionalException {
        validateVille(villeModifiee);
        Ville ville = villeRepo.findById(id).orElseThrow(() -> new FunctionalException("Ville inexistante"));
        ville.setNom(villeModifiee.getNom());
        ville.setNbHabitants(villeModifiee.getNbHabitants());
        ville.setDepartement(villeModifiee.getDepartement());
        villeRepo.save(ville);
        return villeRepo.findAll();
    }

    @Override
    public List<Ville> delete(int id) throws FunctionalException {
        villeRepo.findById(id).orElseThrow(() -> new FunctionalException("Ville inexistante"));
        return villeRepo.findAll();
    }

        private void validateVille(Ville ville) throws FunctionalException {
        if (ville.getNom() == null || ville.getNom().length() < 2) {
            throw new FunctionalException("Le nom de la ville doit contenir au moins 2 lettres.");
        }
        if (ville.getNbHabitants() < 10) {
            throw new FunctionalException("La ville doit avoir au moins 10 habitants.");
        }
        if (ville.getDepartement() == null || ville.getDepartement().getCode() == null || ville.getDepartement().getCode().length() != 2) {
            throw new FunctionalException("Le code du département doit contenir exactement 2 caractères.");
        }
        // unicité du nom par département
        List<Ville> existing = villeRepo.findByDepartementIdAndNomIgnoreCase(ville.getDepartement().getId(), ville.getNom());
        if (!existing.isEmpty()) {
            throw new FunctionalException("Une ville avec ce nom existe déjà dans ce département.");
        }
    }
}
