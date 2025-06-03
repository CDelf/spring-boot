package fr.diginamic.hello.services;

import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.repos.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository dptRepository;

    public Departement getById(int id) throws FunctionalException {
        return dptRepository.findById(id)
                .orElseThrow(() -> new FunctionalException("Département introuvable"));
    }

    public Departement getByNom(String nom) throws FunctionalException {
        return dptRepository.findByNom(nom)
                .orElseThrow(() -> new FunctionalException("Département introuvable"));
    }

    public Departement getByCode(String code) throws FunctionalException {
        return dptRepository.findByCode(code)
                .orElseThrow(() -> new FunctionalException("Département introuvable"));
    }

    public List<Departement> insertDpt(Departement dpt) throws FunctionalException {
        validateDepartement(dpt);
        dptRepository.save(dpt);
        return dptRepository.findAll();
    }

    public List<Departement> updateDpt(int id, Departement dpt) throws FunctionalException {
        Departement dptAModifier = dptRepository.findById(id).orElseThrow(() -> new FunctionalException("Département introuvable"));
        validateDepartement(dptAModifier);
        dptAModifier.setNom(dpt.getNom());
        dptAModifier.setCode(dpt.getCode());
        dptRepository.save(dptAModifier);

        return dptRepository.findAll();
    }

    public List<Departement> deleteDpt(int id) throws FunctionalException {
        dptRepository.findById(id).orElseThrow(() -> new FunctionalException("Département introuvable"));
        dptRepository.deleteById(id);
        return dptRepository.findAll();
    }

    private void validateDepartement(Departement dpt) throws FunctionalException {
        if (dpt.getNom() == null || dpt.getNom().length() < 3) {
            throw new FunctionalException("Le nom du département doit contenir au moins 3 lettres.");
        }

        List<Departement> dpts = dptRepository.findAllByNom(dpt.getNom());
        for (Departement dep : dpts) {
            if (dpt.getId() == 0 || dep.getId() != dpt.getId()) {
                throw new FunctionalException("Un département avec ce nom existe déjà.");
            }
        }
    }
}
