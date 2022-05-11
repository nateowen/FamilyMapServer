package ServicesTests;

import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.PersonDAO;
import DAO.UserDAO;
import Model.User;
import Request.RegisterRequest;
import Result.FillResult;
import Services.ServeClear;
import Services.ServeFill;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ServeFillTest {
    private DatabaseConnect db;
    private UserDAO uDAO;
    private PersonDAO pDAO;
    private ServeFill service;
    private ServeRegister register;
    private ServeClear clear;
    private FillResult result;

    // Use register and then fill to refill that registered user's family tree.

    @BeforeEach
    void setUp() throws DataAccessException, FileNotFoundException {
        clear = new ServeClear();
        clear.clearDatabase();

        db = new DatabaseConnect();
        service = new ServeFill();
        register = new ServeRegister();
        result = new FillResult();

        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        result = service.passToDAO("nateowen", 4);

        uDAO = new UserDAO(db.getConnection());
        User findUser = uDAO.fetch("nateowen", "password");
        db.closeConnection(true);

        assertEquals(service.getMessage(), result.getMessage());
        assertNotNull(findUser);
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        result = service.passToDAO("nathanowen", 4);

        assertEquals(false, result.isSuccess());
        assertThrows(NullPointerException.class, () -> pDAO.findByUser("nathanowen"));
    }
}