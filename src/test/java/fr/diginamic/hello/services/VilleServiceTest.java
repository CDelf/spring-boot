package fr.diginamic.hello.services;

import fr.diginamic.hello.HelloApplicationTest;
import fr.diginamic.hello.exceptions.FunctionalException;
import fr.diginamic.hello.models.Departement;
import fr.diginamic.hello.models.Ville;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= HelloApplicationTest.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VilleServiceTest {

    @Autowired
    VilleService villeService;

    private Departement dpt;

    @BeforeAll
    public void init() {
        dpt = new Departement();
        dpt.setId(1);
        dpt.setCode("01");
        dpt.setNom("Ain");
    }


    @Test
    public void TestgetVilleById() throws FunctionalException {
        Ville ville = villeService.getById(502);
        assertNotNull(ville);
        assertEquals("Briançon", ville.getNom());
        assertEquals(12000, ville.getNbHabitants());
    }

    @Test
    void testGetByNonExistingId_throwsException() {
        assertThrows(FunctionalException.class, () -> villeService.getById(999));
    }

    @Test
    public void getVilleByNom() throws FunctionalException {
        Ville ville = villeService.getByNom("Oraison");
        assertNotNull(ville);
        assertEquals(406, ville.getId());
        assertEquals(3700, ville.getNbHabitants());
    }

    @Test
    void testGetByNonExistingName_throwsException() {
        assertThrows(FunctionalException.class, () -> villeService.getByNom("Lalaland"));
    }

    @Test
    public void saveVille() throws FunctionalException {

        villeService.save(new Ville("VilleTest", 10000, dpt));
        Ville ville = villeService.getByNom("VilleTest");

        assertNotNull(ville);
        assertEquals("Ain", ville.getDepartement().getNom());
        assertEquals(10000, ville.getNbHabitants());
    }

    @Test
    public void saveVilleSameDptAndName() {
        assertThrows(FunctionalException.class, () ->
                villeService.save(new Ville("VilleTest", 20000, dpt)));
    }

//    @Test
//    public void deleteVilleById() throws FunctionalException {
//        Ville ville = villeService.getByNom("VilleTest");
//        villeService.delete(ville.getId());
//
//        assertThrows(FunctionalException.class, () -> villeService.getById(ville.getId()));
//    }
}