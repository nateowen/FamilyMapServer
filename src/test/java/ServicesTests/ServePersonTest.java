package ServicesTests;

import DAO.*;
import Model.Person;
import Request.RegisterRequest;
import Result.PersonsResult;
import Services.ServeClear;
import Services.ServePerson;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServePersonTest {
    private DatabaseConnect db;
    private PersonDAO pDAO;
    private ServePerson service;
    private ServeClear clear;
    private ServeRegister register;
    private PersonsResult result;

    @BeforeEach
    void setUp() throws DataAccessException, FileNotFoundException {
        db = new DatabaseConnect();
        clear = new ServeClear();
        register = new ServeRegister();
        service = new ServePerson();
        result = new PersonsResult();

        clear.clearDatabase();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        String searchToken = register.getAuthtoken();
        result = service.passToDAO(searchToken);
        pDAO = new PersonDAO(db.getConnection());
        ArrayList<Person> persons = pDAO.findByUser("nateowen");
        db.closeConnection(true);

        assertEquals(true, result.isSuccess());
        assertNotNull(persons);
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        String wrongToken = UUID.randomUUID().toString();
        result = service.passToDAO(wrongToken);

        assertEquals("Error: Invalid authToken", result.getMessage());
        assertThrows(NullPointerException.class, () -> pDAO.fetch(wrongToken));
    }
}