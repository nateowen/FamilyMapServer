package ServicesTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.UserDAO;
import Model.User;
import Request.RegisterRequest;
import Result.PersonIDResult;
import Result.PersonsResult;
import Services.ServeClear;
import Services.ServePerson;
import Services.ServePersonID;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServePersonIDTest {
    private DatabaseConnect db;
    private ServePersonID service;
    private ServeClear clear;
    private ServeRegister register;
    private PersonIDResult result;
    private UserDAO getUser;

    @BeforeEach
    void setUp() throws FileNotFoundException, DataAccessException {
        db = new DatabaseConnect();
        service = new ServePersonID();
        clear = new ServeClear();
        register = new ServeRegister();
        result = new PersonIDResult();

        clear.clearDatabase();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        User user;
        getUser = new UserDAO(db.getConnection());
        user = getUser.fetch("nateowen", "password");
        db.closeConnection(true);

        String personID = user.getPersonID();
        result = service.passToDAO(personID, register.getAuthtoken());

        assertEquals("nateowen", result.getAssociatedUsername());
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        result = service.passToDAO(UUID.randomUUID().toString(), register.getAuthtoken());

        assertEquals("Error: Invalid personID", result.getMessage());
    }
}