package DAOTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.PersonDAO;
import Model.Person;
import Request.RegisterRequest;
import Services.ServeClear;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    private DatabaseConnect db;
    private Person bestPerson;
    private PersonDAO pDao;
    private ServeRegister register;
    private ServeClear clear;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseConnect();
        clear = new ServeClear();
        clear.clearDatabase();

        bestPerson = new Person("nate50", "nateowen", "Nate",
                "Owen", "M", "dad50", "mom50", "wife50");

        pDao = new PersonDAO(db.getConnection());
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.fetch(bestPerson.getPersonID());

        assertNotNull(compareTest);

        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);

        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.fetch(bestPerson.getPersonID());

        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        assertNull(pDao.fetch(bestPerson.getPersonID()));
    }

    @Test
    public void findArrayPass() throws DataAccessException, FileNotFoundException {
        register = new ServeRegister();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));

        ArrayList<Person> persons = pDao.findByUser("nateowen");

        assertNotNull(persons);
    }

    @Test
    public void findArrayFail() throws DataAccessException, FileNotFoundException {
        register = new ServeRegister();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));

        ArrayList<Person> persons = pDao.findByUser("nathanowen");

        assertNotEquals(31, persons.size());
    }

    @Test
    public void clearPersonPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person person = pDao.fetch(bestPerson.getPersonID());

        assertNotNull(person);

        pDao.clearPerson();
        Person find = pDao.fetch(bestPerson.getPersonID());

        assertNull(find);
    }

    @Test
    public void clearUsernamePass() throws DataAccessException, FileNotFoundException {
        register = new ServeRegister();
        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));

        pDao.clearAssociatedUsername("nateowen");
        ArrayList<Person> persons = pDao.findByUser("nateowen");

        assertEquals(0, persons.size());
    }
}
