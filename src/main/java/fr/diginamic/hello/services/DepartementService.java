package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.models.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;

    public List<Departement> insertDpt(Departement dpt) {
        if (departementDao.findByNom(dpt.getNom()) != null) {
            throw new IllegalArgumentException("Un département avec ce nom existe déjà.");
        }
        departementDao.saveDpt(dpt);
        return departementDao.findAll();
    }

    public List<Departement> updateDpt(int id, Departement dpt) {
        if (departementDao.findById(id) == null) {
            throw new IllegalArgumentException("Le département n'a pas été trouvé.");
        }
        departementDao.updateDpt(id, dpt);
        return departementDao.findAll();
    }

    public List<Departement> deleteDpt(int id) {
        departementDao.deleteDpt(id);
        return departementDao.findAll();
    }
}
