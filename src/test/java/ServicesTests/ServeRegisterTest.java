package ServicesTests;

import DAO.*;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import Services.ServeClear;
import Services.ServeRegister;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServeRegisterTest {
    private DatabaseConnect db;
    private UserDAO uDAO;
    private PersonDAO pDAO;
    private ServeRegister service;
    private ServeClear clear;
    private RegisterRequest request;
    private RegisterResult result;

    @BeforeEach
    void setUp() throws DataAccessException, FileNotFoundException {
        db = new DatabaseConnect();
        clear = new ServeClear();
        clear.clearDatabase();

        service = new ServeRegister();
        request = new RegisterRequest("nateowen", "password", "nateowen@gmail.com",
                "Nate", "Owen", "m");
        result = new RegisterResult();
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        result = service.passToDAO(request);

        pDAO = new PersonDAO(db.getConnection());
        ArrayList<Person> persons = pDAO.findByUser("nateowen");
        db.closeConnection(true);

        assertEquals("nateowen", result.getUsername());
        assertNotNull(persons);
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        uDAO = new UserDAO(db.getConnection());
        String personID = UUID.randomUUID().toString();
        uDAO.insert(new User("nateowen", "password", "nateowen@gmail.com",
                "Nate", "Owen", "m", personID));
        db.closeConnection(true);

        result = service.passToDAO(request);
        pDAO = new PersonDAO(db.getConnection());
        ArrayList<Person> persons = pDAO.findByUser("nateowen");
        db.closeConnection(true);

        assertEquals("Error: Missing value or username already taken.", result.getMessage());
        assertNotEquals(31, persons.size());
    }

}