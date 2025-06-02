package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;
    @Autowired
    private DepartementDao departementDao;

    // TODO : vérifications à ajouter
    public List<Ville> insertVille(Ville ville) {
        Departement dpt = ville.getDepartement();
        if (dpt == null || departementDao.findById(dpt.getId()) == null) {
            throw new IllegalArgumentException("Département invalide ou inexistant.");
        }
        villeDao.saveVille(ville);
        return villeDao.findAll();
    }

    public List<Ville> modifierVille(int id, Ville villeModifiee) {
          villeDao.updateVille(id, villeModifiee);
          return villeDao.findAll();
    }

    public List<Ville> supprimerVille(int id) {
          villeDao.deleteVille(id);
          return villeDao.findAll();
    }
}
