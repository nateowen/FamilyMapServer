package DAOTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.UserDAO;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private DatabaseConnect db;
    private User bestUser;
    private UserDAO uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new DatabaseConnect();
        bestUser = new User("nateowen", "password", "nathanrheadowen",
                "Nate", "Owen", "M", "1234567");

        Connection conn = db.getConnection();

        uDao = new UserDAO(conn);

        uDao.clearUser();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.fetch(bestUser.getUsername(), bestUser.getPassword());

        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);

        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    public void fetchPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.fetch(bestUser.getUsername(), bestUser.getPassword());

        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void fetchFail() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> uDao.fetch(bestUser.getUsername(), bestUser.getPassword()));
        //assertNull(uDao.fetch(bestUser.getUsername(), bestUser.getPassword()));
    }

    @Test
    public void loginPass() throws DataAccessException {
        uDao.insert(bestUser);
        String authToken = uDao.login("nateowen", "password");

        assertNotNull(authToken);
    }

    @Test
    public void loginFail() throws DataAccessException {
        uDao.insert(bestUser);

        assertThrows(DataAccessException.class, () -> uDao.login("nathanowen", "password"));
    }

    @Test
    public void clearPass() throws DataAccessException {
        uDao.insert(bestUser);
        User user = uDao.fetch(bestUser.getUsername(), bestUser.getPassword());
        assertNotNull(user);

        uDao.clearUser();
        assertThrows(DataAccessException.class, () -> uDao.fetch(bestUser.getUsername(), bestUser.getPassword()));
    }
}
