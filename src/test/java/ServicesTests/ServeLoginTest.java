package ServicesTests;

import DAO.AuthTokenDAO;
import DAO.DataAccessException;
import DAO.DatabaseConnect;
import DAO.UserDAO;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Services.ServeClear;
import Services.ServeLoad;
import Services.ServeLogin;
import Services.ServeRegister;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ServeLoginTest {
    private DatabaseConnect db;
    private UserDAO uDAO;
    private ServeLogin service;
    private ServeRegister register;
    private LoginRequest request;
    private LoginResult result;

    @BeforeEach
    void setUp() throws DataAccessException, FileNotFoundException {
        db = new DatabaseConnect();
        ServeClear clear = new ServeClear();
        service = new ServeLogin();
        register = new ServeRegister();
        clear.clearDatabase();

        register.passToDAO(new RegisterRequest("nateowen", "password", "nate@gmail.com",
                "Nate", "Owen", "m"));
    }

    @Test
    @DisplayName("PASS")
    void passToDAO_PASS() throws DataAccessException {
        request = new LoginRequest("nateowen", "password");
        result = service.passToDAO(request);
        AuthTokenDAO atDAO = new AuthTokenDAO(db.getConnection());
        String username = atDAO.findToken(register.getAuthtoken());
        db.closeConnection(true);

        assertEquals("nateowen", result.getUsername());
        assertNotNull(username);
    }

    @Test
    @DisplayName("FAIL")
    void passToDAO_FAIL() throws DataAccessException {
        request = new LoginRequest("nathanowen", "Password");
        result = service.passToDAO(request);

        assertEquals("Error: No user found.", result.getMessage());
        assertThrows(NullPointerException.class, () -> uDAO.fetch("nathanowen", "Password"));
    }
}