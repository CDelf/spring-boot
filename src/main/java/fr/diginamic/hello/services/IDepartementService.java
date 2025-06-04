package fr.diginamic.hello.services;

import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Departement;

import java.util.List;

public interface IDepartementService {


    Departement getById(int id) throws FunctionalException;

    Departement getByNom(String nom) throws FunctionalException;

    Departement getByCode(String code) throws FunctionalException;

    List<Departement> insertDpt(Departement dpt) throws FunctionalException;

    List<Departement> updateDpt(int id, Departement dpt) throws FunctionalException;

    List<Departement> deleteDpt(int id) throws FunctionalException;
}
