package ServicesTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.EventDAO;
import Model.Event;
import Request.RegisterRequest;
import Result.EventResult;
import Services.ServeClear;
import Services.ServeEvent;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServeEventTest {
    private DatabaseConnect db;
    private ServeEvent service;
    private ServeClear clear;
    private ServeRegister register;
    private EventResult result;
    private EventDAO eDAO;

    @BeforeEach
    void setUp() throws FileNotFoundException, DataAccessException {
        db = new DatabaseConnect();
        clear = new ServeClear();
        register = new ServeRegister();
        service = new ServeEvent();
        result = new EventResult();

        clear.clearDatabase();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        String searchToken = register.getAuthtoken();
        result = service.passToDAO(searchToken);
        eDAO = new EventDAO(db.getConnection());
        ArrayList<Event> events = eDAO.findByUser("nateowen");
        db.closeConnection(true);

        assertEquals(true, result.isSuccess());
        assertNotNull(events);
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        String wrongToken = UUID.randomUUID().toString();
        result = service.passToDAO(wrongToken);

        assertEquals("Error: Invalid authToken", result.getMessage());
        assertThrows(NullPointerException.class, () -> eDAO.findEvent(wrongToken));
    }
}