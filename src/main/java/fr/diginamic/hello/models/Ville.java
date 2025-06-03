package fr.diginamic.hello.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Ville {

    @Id
    private int id;

    @NotBlank(message = "Le nom ne doit pas être vide.")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères.")
    private String nom;

    @Column(name="nb_habitants")
    @Min(value = 10, message = "Le nombre d'habitants ne peut pas être inférieur à 10.")
    private int nbHabitants;

    @NotNull(message = "Le département est obligatoire")
    @ManyToOne
    @JoinColumn(name="id_dept", nullable = false)
    @JsonBackReference
    private Departement departement;

    public Ville() {
    }

    public Ville(String nom, int nbHabitants, Departement departement) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
