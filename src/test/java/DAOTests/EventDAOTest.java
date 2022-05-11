package DAOTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.EventDAO;
import Model.Event;
import Model.Person;
import Request.RegisterRequest;
import Services.ServeClear;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDAOTest {
    private DatabaseConnect db;
    private Event bestEvent;
    private EventDAO eDao;
    private ServeRegister register;

    @BeforeEach
    public void setUp() throws DataAccessException, FileNotFoundException {
        db = new DatabaseConnect();
        ServeClear clear = new ServeClear();
        clear.clearDatabase();

        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        eDao = new EventDAO(db.getConnection());
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        eDao.addEvent(bestEvent);
        Event compareTest = eDao.findEvent(bestEvent.getEventID());

        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        eDao.addEvent(bestEvent);

        assertThrows(DataAccessException.class, () -> eDao.addEvent(bestEvent));
    }

    @Test
    public void findPass() throws DataAccessException {
        eDao.addEvent(bestEvent);
        Event compareTest = eDao.findEvent(bestEvent.getEventID());

        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull(eDao.findEvent(bestEvent.getEventID()));
    }

    @Test
    public void findUserPass() throws DataAccessException, FileNotFoundException {
        register = new ServeRegister();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));

        ArrayList<Event> events = eDao.findByUser("nateowen");

        assertNotNull(events);
    }

    @Test
    public void findUserFail() throws DataAccessException, FileNotFoundException {
        register = new ServeRegister();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));

        ArrayList<Event> events = eDao.findByUser("nathanowen");

        assertNotEquals(92, events.size());
    }

    @Test
    public void clearPass() throws DataAccessException {
        eDao.addEvent(bestEvent);
        Event event = eDao.findEvent(bestEvent.getEventID());
        assertNotNull(event);

        eDao.clearEvent();
        Event findEvent = eDao.findEvent(bestEvent.getEventID());
        assertNull(findEvent);
    }

    @Test
    public void clearUserPass() throws DataAccessException, FileNotFoundException {
        register = new ServeRegister();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));

        eDao.clearAssociatedUsername("nateowen");
        ArrayList<Event> events = eDao.findByUser("nateowen");

        assertEquals(0, events.size());
    }
}
