package fr.diginamic.hello.services;

import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Ville;

import java.util.List;

public interface IVilleService {
    Ville getById(int id) throws FunctionalException;

    Ville getByNom(String nom) throws FunctionalException;

    List<Ville> getByPrefix(String prefix) throws FunctionalException;

    List<Ville> getByNbHabitantsMin(int min) throws FunctionalException;

    List<Ville> getByNbHabitantsRange(int min, int max) throws FunctionalException;

    List<Ville> getByDepartementCode(String code) throws FunctionalException;

    List<Ville> getByDepartementAndNbHabitantsMin(String code, int min) throws FunctionalException;

    List<Ville> getByDepartementAndNbHabitantsRange(String code, int min, int max) throws FunctionalException;

    // Retour un nombre n de résultats pour le TOP ville
    List<Ville> getTopNVilles(int dptId, int n) throws FunctionalException;

    // Gestion des retours de listes après les save et update
    List<Ville> save(Ville ville) throws FunctionalException;

    List<Ville> update(int id, Ville villeModifiee) throws FunctionalException;

    List<Ville> delete(int id) throws FunctionalException;
}
