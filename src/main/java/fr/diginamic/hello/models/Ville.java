package fr.diginamic.hello.models;

import jakarta.validation.constraints.*;

public class Ville {


    @Positive(message = "L'id doit être strictement positif.")
    private int id;

    @NotBlank(message = "Le nom ne doit pas être vide.")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères.")
    private String nom;

    @Min(value = 1, message = "Le nombre d'habitants doit être au moins égal à 1.")
    private int nbHabitants;

    public Ville() {
    }

    public Ville(int id, String nom, int nbHabitants) {
        this.id = id;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
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
}
