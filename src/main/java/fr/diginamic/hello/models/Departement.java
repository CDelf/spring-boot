package fr.diginamic.hello.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Le nom de département est obligatoire")
    @Column(nullable = false, unique = true)
    private String nom;

    @NotBlank(message = "Le numéro de département est obligatoire")
    @Column(nullable = false, unique = true)
    private String numero;

    @OneToMany(mappedBy = "departement")
    @JsonManagedReference
    private Set<Ville> villes = new HashSet<>();

    public Departement() {
    }

    public Departement(String nom, String numero) {
        this.nom = nom;
        this.numero = numero;
    }

    public Departement(String nom, String numero, Set<Ville> villes) {
        this.nom = nom;
        this.numero = numero;
        this.villes = villes;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Set<Ville> getVilles() {
        return villes;
    }

    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
    }
}
