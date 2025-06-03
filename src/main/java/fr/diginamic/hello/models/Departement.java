package fr.diginamic.hello.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Departement {

    @Id
    private int id;

    private String nom;

    @NotBlank(message = "Le numéro de département est obligatoire")
    @Column(nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "departement")
    @JsonManagedReference
    private Set<Ville> villes = new HashSet<>();

    public Departement() {
    }

    public Departement(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public Departement(String nom, String code, Set<Ville> villes) {
        this.nom = nom;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String numero) {
        this.code = numero;
    }

    public Set<Ville> getVilles() {
        return villes;
    }

    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
    }
}
