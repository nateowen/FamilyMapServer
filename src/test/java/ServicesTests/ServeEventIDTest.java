package ServicesTests;

import DAO.*;
import Model.Event;
import Request.RegisterRequest;
import Result.EventIDResult;
import Services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServeEventIDTest {
    private DatabaseConnect db;
    private ServeEventID service;
    private ServeClear clear;
    private ServeRegister register;
    private EventIDResult result;
    private EventDAO eDAO;
    private UserDAO uDAO;
    private Event event;

    @BeforeEach
    void setUp() throws FileNotFoundException, DataAccessException {
        db = new DatabaseConnect();
        service = new ServeEventID();
        clear = new ServeClear();
        register = new ServeRegister();
        result = new EventIDResult();

        clear.clearDatabase();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        Random random = new Random();
        eDAO = new EventDAO(db.getConnection());
        ArrayList<Event> eventList = eDAO.findByUser("nateowen");
        db.closeConnection(true);

        Event singleEvent = eventList.get(random.nextInt(eventList.size()));
        String eventID = singleEvent.getEventID();

        result = service.passToDAO(eventID, register.getAuthtoken());
        assertEquals(eventID, result.getEventID());
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        result = service.passToDAO(UUID.randomUUID().toString(), register.getAuthtoken());
        assertNotEquals("nateowen", result.getAssociatedUsername());
    }
}