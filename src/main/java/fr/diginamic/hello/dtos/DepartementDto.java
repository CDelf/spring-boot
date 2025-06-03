package fr.diginamic.hello.dtos;

public class DepartementDto {

    private String code;
    private String nom;
    private int nbHabitants;

    public DepartementDto() {
    }

    public DepartementDto(String code, String nom, int nbHabitants) {
        this.code = code;
        this.nom = nom;
        this.nbHabitants = nbHabitants;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
