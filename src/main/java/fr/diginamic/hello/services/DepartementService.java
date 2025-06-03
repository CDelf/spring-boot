package fr.diginamic.hello.services;

import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.repos.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository dptRepository;

    public List<Departement> insertDpt(Departement dpt) {
        if (dptRepository.findByNom(dpt.getNom()) != null) {
            throw new IllegalArgumentException("Un département avec ce nom existe déjà.");
        }
        dptRepository.save(dpt);
        return dptRepository.findAll();
    }
    public List<Departement> updateDpt(int id, Departement dpt) {
        Departement dptAModifier = dptRepository.findById(id).orElse(null);
        if(dptAModifier != null) {
            dptAModifier.setNom(dpt.getNom());
            dptAModifier.setCode(dpt.getCode());
            dptRepository.save(dptAModifier);
        }
        return dptRepository.findAll();
    }

    public List<Departement> deleteDpt(int id) {
        dptRepository.deleteById(id);
        return dptRepository.findAll();
    }
}
