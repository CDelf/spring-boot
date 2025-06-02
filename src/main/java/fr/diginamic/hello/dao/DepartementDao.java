package fr.diginamic.hello.dao;

import fr.diginamic.hello.models.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;

    public List<Departement> findAll() {
        return em.createQuery("FROM Departement d ORDER BY d.id", Departement.class).getResultList();
    }

    public Departement findById(int id) {
        return em.find(Departement.class, id);
    }

    public Departement findByNom(String nom) {
        return em.createQuery("SELECT d FROM Departement d WHERE LOWER(d.nom) = LOWER(:nom)", Departement.class)
                .setParameter("nom", nom)
                .getResultStream().findFirst().orElse(null);
    }

    @Transactional
    public void saveDpt(Departement dpt) {
        em.persist(dpt);
    }

    @Transactional
    public void updateDpt(int id, Departement nvDpt) {
        Departement dptAModifier = em.find(Departement.class, id);
        if (dptAModifier != null) {
            dptAModifier.setNom(nvDpt.getNom());
            dptAModifier.setNumero(nvDpt.getNumero());
        }
    }

    @Transactional
    public void deleteDpt(int id) {
        em.remove(em.find(Departement.class, id));
    }
}
