package ServicesTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.UserDAO;
import Model.User;
import Result.ClearResult;
import Services.ServeClear;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServeClearTest {
    private DatabaseConnect db;
    private UserDAO userDAO;
    private ServeClear service;
    private ClearResult result;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new DatabaseConnect();
        userDAO = new UserDAO(db.getConnection());
        userDAO.clearUser();
        userDAO.insert(new User("nateowen", "password", "nateowen@gmail.com",
                "Nate", "Owen", "m", "bvihjerwbgai2523"));
        db.closeConnection(true);

        service = new ServeClear();
        result = new ClearResult();
    }

    @Test
    @DisplayName("PASS #1")
    void clearDatabase_PASS() throws DataAccessException {
        result = service.clearDatabase();

        assertEquals("Clear succeeded.", result.getMessage());
        userDAO = new UserDAO(db.getConnection());
        assertThrows(DataAccessException.class, () -> userDAO.fetch("nateowen", "password"));
        db.closeConnection(true);
    }

    @Test
    @DisplayName("PASS #2")
    void clearDatabase_PASS2() throws DataAccessException {
        service.clearDatabase();

        userDAO = new UserDAO(db.getConnection());
        assertThrows(DataAccessException.class, () -> userDAO.fetch("nateowen", "password"));
        db.closeConnection(true);
    }
}