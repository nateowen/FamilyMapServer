package ServicesTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.UserDAO;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Services.ServeClear;
import Services.ServeLoad;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import passoff.ServerTest;

import javax.xml.crypto.Data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServeLoadTest {
    private DatabaseConnect db;
    private ServeLoad service;
    private LoadRequest request;
    private LoadResult result;
    private ServeClear clear;
    private UserDAO uDAO;
    private User user;
    private User[] userObject;
    private Event event;
    private Event[] eventObject;
    private Person person;
    private Person[] personObject;

    @BeforeEach
    void setUp() throws DataAccessException {
//        clear = new ServeClear();
//        clear.clearDatabase();

        String personID = UUID.randomUUID().toString();

        // Create Event, User, and Person objects and load those in to request.
        user = new User("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m", personID);
        userObject = new User[1];
        userObject[0] = user;

        event = new Event(UUID.randomUUID().toString(), "nateowen", personID, (float)55.100,
                (float)100.55, "Utah", "Salt Lake City", "death", 2075);
        eventObject = new Event[1];
        eventObject[0] = event;

        person = new Person(personID, "nateowen", "Nate", "Owen",
                "m", UUID.randomUUID().toString(), UUID.randomUUID().toString(), null);
        personObject = new Person[1];
        personObject[0] = person;

        service = new ServeLoad();
        db = new DatabaseConnect();
        request = new LoadRequest(userObject, personObject, eventObject);
        result = new LoadResult();
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        result = service.passToDAO(request);

        uDAO = new UserDAO(db.getConnection());
        User findUser = uDAO.fetch("nateowen", "password");
        db.closeConnection(true);

        assertEquals(true, result.isSuccess());
        assertNotNull(findUser);
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        user = new User("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m", null);
        userObject = new User[1];
        userObject[0] = user;

        event = new Event(UUID.randomUUID().toString(), "nateowen", null, (float)55.100,
                (float)100.55, "Utah", "Salt Lake City", "death", 2075);
        eventObject = new Event[1];
        eventObject[0] = event;

        person = new Person(null, "nateowen", "Nate", "Owen",
                "m", UUID.randomUUID().toString(), UUID.randomUUID().toString(), null);
        personObject = new Person[1];
        personObject[0] = person;

        request = new LoadRequest(userObject, personObject, eventObject);
        result = service.passToDAO(request);

        assertEquals(false, result.isSuccess());
    }
}