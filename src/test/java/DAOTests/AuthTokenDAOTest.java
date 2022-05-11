package DAOTests;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseConnect;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDAOTest {
    private DatabaseConnect db;
    private AuthTokenDAO aTDAO;
    private AuthToken token;
    String authtoken = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new DatabaseConnect();
        token = new AuthToken(authtoken, "nateowen");

        Connection connection = db.getConnection();

        aTDAO = new AuthTokenDAO(connection);

        aTDAO.clearAuthToken();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    void insertPass() throws DataAccessException {
        aTDAO.insertToken(authtoken, "nateowen");
        String checkToken = aTDAO.findToken(authtoken);

        assertNotNull(checkToken);
    }

    @Test
    void insertFail() throws DataAccessException {
        aTDAO.insertToken(authtoken, "nateowen");

        assertThrows(DataAccessException.class, () -> aTDAO.insertToken(authtoken, "nateowen"));
    }

    @Test
    void findPass() throws DataAccessException {
        aTDAO.insertToken(authtoken, "nateowen");
        String checkToken = aTDAO.findToken(authtoken);

        assertEquals("nateowen", checkToken);
    }

    @Test
    void findFail() throws DataAccessException {
        String checkToken = aTDAO.findToken(UUID.randomUUID().toString());

        assertNull(checkToken);
    }

    @Test
    void clearPass() throws DataAccessException {
        aTDAO.insertToken(authtoken, "nate");
        String token2 = UUID.randomUUID().toString();
        aTDAO.insertToken(token2, "owen");

        aTDAO.clearAuthToken();
        String findToken = aTDAO.findToken(authtoken);
        String findToken2 = aTDAO.findToken(token2);
        assertNull(findToken);
        assertNull(findToken2);
    }
}