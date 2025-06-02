package fr.diginamic.hello.dao;

import fr.diginamic.hello.models.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class VilleDao {

    @PersistenceContext
    private EntityManager em;

    // Récupère toutes les villes en base
    public List<Ville> findAll() {
        return em.createQuery("FROM Ville", Ville.class).getResultList();
    }

    // Récupérer une ville par son id
    public Ville findById(int id) {
        return em.find(Ville.class, id);
    }

    // Récupérer une ville par son nom
    public Ville findByNom(String nom) {
        return em.createQuery("FROM Ville v WHERE LOWER(v.nom) = LOWER(:nom)", Ville.class)
                .setParameter("nom", nom)
                .getResultStream().findFirst().orElse(null);
    }

    // Récupérer les n villes les plus peuplées d'un département
    public List<Ville> findTopNVillesByDepartement(int dptId, int n) {
        return em.createQuery(
                "FROM Ville v WHERE v.departement.id = :dptId ORDER BY v.nbHabitants DESC", Ville.class)
                .setParameter("dptId", dptId)
                .setMaxResults(n)
                .getResultList();
    }

    // Lister les villes ayant entre min et max habitants dans un département donné
    public List<Ville> findByDepartementAndPopulationRange(int dptId, int min, int max) {
        return em.createQuery(
                "FROM Ville v WHERE v.departement.id = :dptId AND v.nbHabitants BETWEEN :min AND :max", Ville.class)
                .setParameter("dptId", dptId)
                .setParameter("min", min)
                .setParameter("max", max)
                .getResultList();
    }

    // Insérer une liste de villes en base
    @Transactional
    public void saveVille(Ville ville) {
        em.persist(ville);
    }

    // Modifier une ville
    @Transactional
    public void updateVille(int id, Ville villeModifiee) {
        Ville villeInitiale = em.find(Ville.class, id);
        if(villeInitiale != null) {
            villeInitiale.setNom(villeModifiee.getNom());
            villeInitiale.setNbHabitants(villeModifiee.getNbHabitants());
        }
    }

    // Supprimer une ville
    @Transactional
    public void deleteVille(int id) {
        em.remove(em.find(Ville.class, id));
    }
}
