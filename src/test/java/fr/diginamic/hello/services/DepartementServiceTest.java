package fr.diginamic.hello.services;

import fr.diginamic.hello.HelloApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= HelloApplicationTest.class)
@ActiveProfiles("test")
class DepartementServiceTest {

    @Test
    void getById() {
    }

    @Test
    void getByNom() {
    }

    @Test
    void getByCode() {
    }

    @Test
    void insertDpt() {
    }

    @Test
    void updateDpt() {
    }

    @Test
    void deleteDpt() {
    }
}